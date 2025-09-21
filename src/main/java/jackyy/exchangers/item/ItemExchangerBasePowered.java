package jackyy.exchangers.item;

import jackyy.exchangers.handler.ExchangerHandler;
import jackyy.exchangers.util.IExchanger;
import jackyy.exchangers.util.helper.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.List;

public abstract class ItemExchangerBasePowered extends Item implements IExchanger {

    public ItemExchangerBasePowered(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Direction side = context.getClickedFace();

        if (!world.isClientSide() && player != null) {
            ItemStack mainHandStack = player.getMainHandItem();
            ExchangerHandler.ensureTagsExist(mainHandStack);

            if (player.isShiftKeyDown()) {
                ExchangerHandler.selectBlock(mainHandStack, player, world, pos);
            } else {
                // Check if has enough energy for operation
                int energyPerBlock = getEnergyPerBlock();
                int currentRange = ExchangerHandler.getCurrentRange(mainHandStack);
                int blocksToExchange = calculateBlocksToExchange(currentRange);
                int totalEnergyNeeded = energyPerBlock * blocksToExchange;

                if (player.isCreative() || hasEnoughEnergy(mainHandStack, totalEnergyNeeded)) {
                    ExchangerHandler.placeBlock(mainHandStack, player, world, pos, side, context);
                    if (!player.isCreative()) {
                        consumeEnergy(mainHandStack, totalEnergyNeeded);
                    }
                } else {
                    player.sendSystemMessage(Component.literal("§cNot enough energy! Need " + totalEnergyNeeded + " FE"));
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private int calculateBlocksToExchange(int range) {
        if (range == 0) return 1;
        // For area exchanges, calculate approximate block count
        return (range * 2 + 1) * (range * 2 + 1);
    }

    private boolean hasEnoughEnergy(ItemStack stack, int energyNeeded) {
        return getEnergyStored(stack) >= energyNeeded;
    }

    private void consumeEnergy(ItemStack stack, int energy) {
        int currentEnergy = getEnergyStored(stack);
        setEnergyStored(stack, Math.max(0, currentEnergy - energy));
    }

    public int getEnergyStored(ItemStack stack) {
        return NBTHelper.getInt(stack, "energy");
    }

    public void setEnergyStored(ItemStack stack, int energy) {
        NBTHelper.setInt(stack, "energy", Math.max(0, Math.min(energy, getMaxEnergyCapacity())));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getEnergyStored(stack) < getMaxEnergyCapacity();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F * getEnergyStored(stack) / getMaxEnergyCapacity());
    }

    @Override
    public int getBarColor(ItemStack stack) {
        float energyPercent = (float) getEnergyStored(stack) / getMaxEnergyCapacity();
        return energyPercent > 0.5F ? 0x00FF00 : energyPercent > 0.25F ? 0xFFFF00 : 0xFF0000;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        int energy = getEnergyStored(stack);
        int maxEnergy = getMaxEnergyCapacity();
        tooltip.add(Component.literal("Energy: " + energy + " / " + maxEnergy + " FE"));
        tooltip.add(Component.literal("Energy per block: " + getEnergyPerBlock() + " FE"));
    }

    // Abstract methods to be implemented by subclasses
    public abstract int getMaxEnergyCapacity();
    public abstract int getEnergyPerBlock();
}