package jackyy.exchangers.handler;

import jackyy.exchangers.util.IExchanger;
import jackyy.exchangers.util.ExchangerMode;
import jackyy.exchangers.util.helper.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ExchangerHandler {

    public static final String[] rangeList = new String[] {
            "1x1", "3x3", "5x5", "7x7", "9x9",
            "11x11", "13x13", "15x15", "17x17",
            "19x19", "21x21", "23x23", "25x25"
    };

    public static void setDefaultTagCompound(ItemStack stack) {
        // Only set defaults for missing keys, never overwrite existing ones
        if (!NBTHelper.hasCompoundTag(stack, "blockstate")) {
            NBTHelper.setCompoundTag(stack, "blockstate", NbtUtils.writeBlockState(Blocks.AIR.defaultBlockState()));
        }
        if (!NBTHelper.hasInt(stack, "range")) {
            NBTHelper.setInt(stack, "range", 0);
        }
        if (!NBTHelper.hasInt(stack, "mode")) {
            NBTHelper.setInt(stack, "mode", ExchangerMode.PLANE.ordinal());
        }
        if (!NBTHelper.hasBoolean(stack, "directionalPlacement")) {
            NBTHelper.setBoolean(stack, "directionalPlacement", true);
        }
        if (!NBTHelper.hasBoolean(stack, "fuzzyPlacement")) {
            NBTHelper.setBoolean(stack, "fuzzyPlacement", false);
        }
        if (!NBTHelper.hasInt(stack, "fuzzyPlacementChance")) {
            NBTHelper.setInt(stack, "fuzzyPlacementChance", 100);
        }
        if (!NBTHelper.hasBoolean(stack, "forceDropItems")) {
            NBTHelper.setBoolean(stack, "forceDropItems", false);
        }
        if (!NBTHelper.hasBoolean(stack, "voidItems")) {
            NBTHelper.setBoolean(stack, "voidItems", false);
        }
        if (!NBTHelper.hasBoolean(stack, "selectiveReplacement")) {
            NBTHelper.setBoolean(stack, "selectiveReplacement", true);
        }
    }


    public static void ensureTagsExist(ItemStack stack) {
        // Legacy method - now just calls setDefaultTagCompound for compatibility
        setDefaultTagCompound(stack);
    }

    public static void selectBlock(ItemStack stack, Player player, Level world, BlockPos pos) {
        ensureTagsExist(stack); // Only ensure missing tags exist, preserve existing settings
        BlockState state = world.getBlockState(pos);
        float blockHardness = state.getDestroySpeed(world, pos);

        if (world.getBlockEntity(pos) != null) {
            player.sendSystemMessage(Component.literal("§cError: Invalid block! (Tile Entities)"));
            return;
        } else if (blockHardness < -0.1F) {
            player.sendSystemMessage(Component.literal("§cError: Invalid block! (Unbreakable Blocks)"));
            return;
        }

        NBTHelper.setCompoundTag(stack, "blockstate", NbtUtils.writeBlockState(state));
        player.sendSystemMessage(Component.literal("Selected block: " + state.getBlock().getName().getString()));
    }

    public static void placeBlock(ItemStack stack, Player player, Level world, BlockPos pos, Direction side, UseOnContext context) {
        // Ensure NBT exists on server side FIRST
        setDefaultTagCompound(stack);

        CompoundTag tag = NBTHelper.getTag(stack);
        BlockState state = NbtUtils.readBlockState(world.holderLookup(Registries.BLOCK), tag.getCompound("blockstate"));
        Block block = state.getBlock();

        int currentRange = getCurrentRange(stack);

        if (block == Blocks.AIR) {
            player.sendSystemMessage(Component.literal("§cNo block selected! Sneak right-click a block first."));
            return;
        }

        // Store the target block type (the block being clicked on) for selective replacement
        BlockState targetBlockState = world.getBlockState(pos);
        Block targetBlock = targetBlockState.getBlock();

        // If selective replacement is enabled and target is air, warn user and return
        if (getSelectiveReplacement(stack) && targetBlock == Blocks.AIR) {
            player.sendSystemMessage(Component.literal("§cCannot target air blocks for selective replacement!"));
            return;
        }

        // All ranges use area exchange now - range 0 = 1x1, range 1 = 3x3, etc.
        ExchangerMode mode = getCurrentMode(stack);
        exchangeArea(stack, player, world, pos, side, state, block, currentRange, mode, targetBlock);
    }

    private static void exchangeSingleBlock(ItemStack stack, Player player, Level world, BlockPos pos, BlockState newState, Block newBlock) {
        BlockState oldState = world.getBlockState(pos);
        Block oldblock = oldState.getBlock();
        float blockHardness = oldState.getDestroySpeed(world, pos);

        // Get placement state based on directional setting
        BlockState placementState = getPlacementState(stack, newState, pos, null);

        if ((newBlock == oldblock) && (placementState == oldState)) {
            return;
        } else if (oldblock == Blocks.AIR) {
            player.sendSystemMessage(Component.literal("§cError: Cannot exchange air blocks!"));
            return;
        } else if (world.getBlockEntity(pos) != null) {
            player.sendSystemMessage(Component.literal("§cError: Invalid block! (Tile Entities)"));
            return;
        } else if (blockHardness < -0.1F) {
            player.sendSystemMessage(Component.literal("§cError: Invalid block! (Unbreakable Blocks)"));
            return;
        }

        if (player.isCreative()) {
            world.setBlock(pos, placementState, 3);
            player.sendSystemMessage(Component.literal("Block exchanged in creative mode."));
        } else {
            if (hasBlockInInventory(player, newBlock)) {
                removeBlockFromInventory(player, newBlock);
                world.setBlock(pos, placementState, 3);

                if (oldblock != Blocks.AIR) {
                    // Try to add to player inventory first, drop if inventory is full
                    addBlockToInventoryOrDrop(stack, player, world, pos, oldState);
                }
                player.sendSystemMessage(Component.literal("Block exchanged successfully."));
            } else {
                player.sendSystemMessage(Component.literal("§cError: Out of block!"));
            }
        }
    }

    private static void exchangeArea(ItemStack stack, Player player, Level world, BlockPos centerPos, Direction face, BlockState newState, Block newBlock, int range, ExchangerMode mode, Block targetBlock) {
        int exchanged = 0;
        int failed = 0;

        List<BlockPos> positions = getPositionsForMode(centerPos, face, range, mode);
        for (BlockPos pos : positions) {
            BlockState oldState = world.getBlockState(pos);
            Block oldBlock = oldState.getBlock();
            float blockHardness = oldState.getDestroySpeed(world, pos);

            // Get placement state based on directional setting
            BlockState placementState = getPlacementState(stack, newState, pos, face);


            // Skip if same block, has tile entity, unbreakable, or old block is air
            if ((newBlock == oldBlock && placementState == oldState) ||
                world.getBlockEntity(pos) != null ||
                blockHardness < -0.1F ||
                oldBlock == Blocks.AIR) {
                continue;
            }

            // Check selective replacement - only replace blocks matching the target type
            if (getSelectiveReplacement(stack)) {
                if (oldBlock != targetBlock) {
                    continue; // Skip this block as it doesn't match the target type
                }
            }

            // Check fuzzy placement
            if (getFuzzyPlacement(stack)) {
                int chance = getFuzzyPlacementChance(stack);
                if (world.random.nextInt(100) >= chance) {
                    continue; // Skip this block based on fuzzy placement chance
                }
            }

            if (player.isCreative()) {
                world.setBlock(pos, placementState, 3);
                exchanged++;
            } else {
                if (hasBlockInInventory(player, newBlock)) {
                    removeBlockFromInventory(player, newBlock);
                    world.setBlock(pos, placementState, 3);

                    if (oldBlock != Blocks.AIR) {
                        addBlockToInventoryOrDrop(stack, player, world, pos, oldState);
                    }
                    exchanged++;
                } else {
                    failed++;
                }
            }
        }

        if (exchanged > 0) {
            player.sendSystemMessage(Component.literal("Exchanged " + exchanged + " blocks" + (failed > 0 ? " (" + failed + " failed - out of blocks)" : "")));
        } else if (failed > 0) {
            player.sendSystemMessage(Component.literal("§cError: Out of block!"));
        }
    }

    private static boolean hasBlockInInventory(Player player, Block block) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty() && Block.byItem(stack.getItem()) == block) {
                return true;
            }
        }
        return false;
    }

    private static void removeBlockFromInventory(Player player, Block block) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty() && Block.byItem(stack.getItem()) == block) {
                stack.shrink(1);
                return;
            }
        }
    }

    private static void addBlockToInventoryOrDrop(ItemStack exchangerStack, Player player, Level world, BlockPos pos, BlockState blockState) {
        // Check if void items is enabled
        if (getVoidItems(exchangerStack)) {
            // Void the items - do nothing
            return;
        }

        Block block = blockState.getBlock();
        ItemStack blockStack = new ItemStack(block.asItem());

        if (!blockStack.isEmpty()) {
            // Check if force drop is enabled
            if (getForceDropItems(exchangerStack)) {
                // Force drop items regardless of inventory space
                Block.dropResources(blockState, world, pos);
                return;
            }

            // Try to add to player inventory
            if (player.getInventory().add(blockStack)) {
                // Successfully added to inventory
                return;
            }
        }

        // Inventory is full or item couldn't be added, drop it normally
        Block.dropResources(blockState, world, pos);
    }

    public static void increaseRange(ItemStack stack, Player player) {
        ensureTagsExist(stack);
        if (stack.getItem() instanceof IExchanger exchanger) {
            int currentRange = NBTHelper.getInt(stack, "range");
            int maxRange = exchanger.getMaxRange();

            if (currentRange < maxRange) {
                currentRange++;
                NBTHelper.setInt(stack, "range", currentRange);
                String rangeStr = currentRange < rangeList.length ? rangeList[currentRange] : (currentRange + "x" + currentRange);
                player.sendSystemMessage(Component.literal("Range increased to: " + rangeStr));
            } else {
                String maxRangeStr = maxRange < rangeList.length ? rangeList[maxRange] : (maxRange + "x" + maxRange);
                player.sendSystemMessage(Component.literal("Range already at maximum: " + maxRangeStr));
            }
        }
    }

    public static void decreaseRange(ItemStack stack, Player player) {
        ensureTagsExist(stack);
        int currentRange = NBTHelper.getInt(stack, "range");

        if (currentRange > 0) {
            currentRange--;
            NBTHelper.setInt(stack, "range", currentRange);
            String rangeStr = currentRange < rangeList.length ? rangeList[currentRange] : (currentRange + "x" + currentRange);
            player.sendSystemMessage(Component.literal("Range decreased to: " + rangeStr));
        } else {
            player.sendSystemMessage(Component.literal("Range already at minimum: " + rangeList[0]));
        }
    }

    public static int getCurrentRange(ItemStack stack) {
        // Ensure NBT exists but don't overwrite existing values
        if (!NBTHelper.hasTag(stack)) {
            setDefaultTagCompound(stack);
        }
        return NBTHelper.getInt(stack, "range");
    }

    public static void switchMode(ItemStack stack, Player player) {
        ensureTagsExist(stack);
        ExchangerMode currentMode = getCurrentMode(stack);
        ExchangerMode nextMode = currentMode.next();
        NBTHelper.setInt(stack, "mode", nextMode.ordinal());
        player.sendSystemMessage(Component.literal("Mode switched to: " + nextMode.getDisplayName()));
    }

    public static ExchangerMode getCurrentMode(ItemStack stack) {
        ensureTagsExist(stack);
        int modeOrdinal = NBTHelper.getInt(stack, "mode");
        return ExchangerMode.fromOrdinal(modeOrdinal);
    }

    public static void toggleDirectionalPlacement(ItemStack stack, Player player) {
        ensureTagsExist(stack);
        boolean currentState = getDirectionalPlacement(stack);
        boolean newState = !currentState;
        NBTHelper.setBoolean(stack, "directionalPlacement", newState);
        player.sendSystemMessage(Component.literal("Directional Placement: " + (newState ? "ON" : "OFF")));
    }

    public static boolean getDirectionalPlacement(ItemStack stack) {
        ensureTagsExist(stack);
        return NBTHelper.getBoolean(stack, "directionalPlacement");
    }

    private static BlockState getPlacementState(ItemStack stack, BlockState originalState, BlockPos pos, Direction face) {
        boolean directionalPlacement = getDirectionalPlacement(stack);

        if (!directionalPlacement) {
            // Use default block state (no directional properties)
            return originalState.getBlock().defaultBlockState();
        } else {
            // Use the original selected state as-is
            return originalState;
        }
    }

    public static List<BlockPos> getPositionsForMode(BlockPos center, Direction face, int range, ExchangerMode mode) {
        return switch (mode) {
            case PLANE -> getPlanePositions(center, face, range);
            case HORIZONTAL_COLUMN -> getHorizontalColumnPositions(center, face, range);
            case VERTICAL_COLUMN -> getVerticalColumnPositions(center, range);
        };
    }

    private static List<BlockPos> getPlanePositions(BlockPos center, Direction face, int range) {
        List<BlockPos> positions = new ArrayList<>();

        // Calculate the two perpendicular directions to the face
        Direction dir1, dir2;

        switch (face) {
            case UP, DOWN -> {
                dir1 = Direction.NORTH;
                dir2 = Direction.EAST;
            }
            case NORTH, SOUTH -> {
                dir1 = Direction.UP;
                dir2 = Direction.EAST;
            }
            case EAST, WEST -> {
                dir1 = Direction.UP;
                dir2 = Direction.NORTH;
            }
            default -> {
                dir1 = Direction.NORTH;
                dir2 = Direction.EAST;
            }
        }

        // Generate positions in a square around the center
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                BlockPos pos = center
                    .relative(dir1, i)
                    .relative(dir2, j);
                positions.add(pos);
            }
        }

        return positions;
    }

    private static List<BlockPos> getHorizontalColumnPositions(BlockPos center, Direction face, int range) {
        List<BlockPos> positions = new ArrayList<>();

        // Determine the horizontal direction perpendicular to the face being targeted
        Direction horizontalDirection;

        if (face == Direction.UP || face == Direction.DOWN) {
            // When targeting up/down faces, use North-South as the horizontal line
            horizontalDirection = Direction.NORTH;
        } else {
            // When targeting side faces, use the perpendicular horizontal direction
            switch (face) {
                case NORTH, SOUTH -> horizontalDirection = Direction.EAST;
                case EAST, WEST -> horizontalDirection = Direction.NORTH;
                default -> horizontalDirection = Direction.NORTH;
            }
        }

        // Generate positions in a single horizontal line
        for (int i = -range; i <= range; i++) {
            positions.add(center.relative(horizontalDirection, i));
        }

        return positions;
    }

    private static List<BlockPos> getVerticalColumnPositions(BlockPos center, int range) {
        List<BlockPos> positions = new ArrayList<>();

        // Generate positions in a vertical line (Y-axis)
        for (int i = -range; i <= range; i++) {
            positions.add(center.relative(Direction.UP, i));
        }

        return positions;
    }

    // Fuzzy Placement methods
    public static void toggleFuzzyPlacement(ItemStack stack, Player player) {
        ensureTagsExist(stack);
        boolean currentState = getFuzzyPlacement(stack);
        boolean newState = !currentState;
        NBTHelper.setBoolean(stack, "fuzzyPlacement", newState);
        player.sendSystemMessage(Component.literal("Fuzzy Placement: " + (newState ? "ON" : "OFF")));
    }

    public static boolean getFuzzyPlacement(ItemStack stack) {
        ensureTagsExist(stack);
        return NBTHelper.getBoolean(stack, "fuzzyPlacement");
    }

    public static void setFuzzyPlacementChance(ItemStack stack, Player player, int chance) {
        ensureTagsExist(stack);
        chance = Math.max(0, Math.min(100, chance)); // Clamp between 0-100
        NBTHelper.setInt(stack, "fuzzyPlacementChance", chance);
        player.sendSystemMessage(Component.literal("Fuzzy Placement Chance: " + chance + "%"));
    }

    public static int getFuzzyPlacementChance(ItemStack stack) {
        ensureTagsExist(stack);
        return NBTHelper.getInt(stack, "fuzzyPlacementChance");
    }

    // Force Drop Items methods
    public static void toggleForceDropItems(ItemStack stack, Player player) {
        ensureTagsExist(stack);
        boolean currentState = getForceDropItems(stack);
        boolean newState = !currentState;
        NBTHelper.setBoolean(stack, "forceDropItems", newState);
        player.sendSystemMessage(Component.literal("Force Drop Items: " + (newState ? "ON" : "OFF")));
    }

    public static boolean getForceDropItems(ItemStack stack) {
        ensureTagsExist(stack);
        return NBTHelper.getBoolean(stack, "forceDropItems");
    }

    // Void Items methods
    public static void toggleVoidItems(ItemStack stack, Player player) {
        ensureTagsExist(stack);
        boolean currentState = getVoidItems(stack);
        boolean newState = !currentState;
        NBTHelper.setBoolean(stack, "voidItems", newState);
        player.sendSystemMessage(Component.literal("Void Items: " + (newState ? "ON" : "OFF")));
    }

    public static boolean getVoidItems(ItemStack stack) {
        ensureTagsExist(stack);
        return NBTHelper.getBoolean(stack, "voidItems");
    }

    // Selective Replacement methods
    public static void toggleSelectiveReplacement(ItemStack stack, Player player) {
        ensureTagsExist(stack);
        boolean currentState = getSelectiveReplacement(stack);
        boolean newState = !currentState;
        NBTHelper.setBoolean(stack, "selectiveReplacement", newState);
        player.sendSystemMessage(Component.literal("Selective Replacement: " + (newState ? "ON" : "OFF")));
    }

    public static boolean getSelectiveReplacement(ItemStack stack) {
        ensureTagsExist(stack);
        return NBTHelper.getBoolean(stack, "selectiveReplacement");
    }
}