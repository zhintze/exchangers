package jackyy.exchangers.client.gui;

import com.google.common.collect.ImmutableList;
import jackyy.exchangers.handler.ExchangerHandler;
import jackyy.exchangers.handler.network.NetworkHandler;
import jackyy.exchangers.handler.network.packet.*;
import jackyy.exchangers.item.ItemExchangerBase;
import jackyy.exchangers.util.ExchangerMode;
import jackyy.exchangers.util.IExchanger;
import jackyy.exchangers.util.Reference;
import jackyy.exchangers.util.helper.NBTHelper;
import jackyy.exchangers.util.helper.StringHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class ExchangersGuiScreen extends Screen {
    private static Minecraft mc = Minecraft.getInstance();
    private final ResourceLocation GUI_IMAGE = ResourceLocation.fromNamespaceAndPath("exchangers", "textures/gui/gui_exchanger.png");
    private final WidgetSprites LEFT_ARROW = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath("exchangers", "left_arrow_normal"),
            ResourceLocation.fromNamespaceAndPath("exchangers", "left_arrow_disabled"),
            ResourceLocation.fromNamespaceAndPath("exchangers", "left_arrow_highlighted")
    );
    private final WidgetSprites RIGHT_ARROW = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath("exchangers", "right_arrow_normal"),
            ResourceLocation.fromNamespaceAndPath("exchangers", "right_arrow_disabled"),
            ResourceLocation.fromNamespaceAndPath("exchangers", "right_arrow_highlighted")
    );
    private static final int W = 180;
    private static final int H = 150;
    private String chance;

    private ImageButtonExt decreaseRangeButton;
    private ImageButtonExt increaseRangeButton;
    private ToggleButton modeSwitchButton;
    private ToggleButton forceDropItemsButton;
    private ToggleButton directionalPlacementButton;
    private ToggleButton fuzzyPlacementButton;
    private ToggleButton voidItemsButton;
    private ToggleButton selectiveReplacementButton;

    private EditBox fuzzyPlacementChanceField;

    public ExchangersGuiScreen() {
        super(Component.translatable("screen.exchangers.exchanger_gui.title"));
        this.width = W;
        this.height = H;
    }

    @Override
    public void init() {
        int relativeX = (this.width - W) / 2;
        int relativeY = (this.height - H) / 2;
        ItemStack stack = mc.player.getMainHandItem();
        CompoundTag tag = NBTHelper.getTag(stack);
        chance = Integer.toString(tag.getInt("fuzzyPlacementChance"));
        decreaseRangeButton = new ImageButtonExt(relativeX + 54, relativeY + 31, 10, 15, LEFT_ARROW,
                button -> NetworkHandler.sendToServer(new PacketDecreaseRange()));
        increaseRangeButton = new ImageButtonExt(relativeX + 116, relativeY + 31, 10, 15, RIGHT_ARROW,
                button -> NetworkHandler.sendToServer(new PacketIncreaseRange()));
        modeSwitchButton = new ToggleButton(relativeX + 20, relativeY + 66, 20, 20, Component.literal("\u29C8"),
                button -> NetworkHandler.sendToServer(new PacketSwitchMode()), narration -> Component.literal("Mode Switch"));
        forceDropItemsButton = new ToggleButton(relativeX + 60, relativeY + 66, 20, 20, Component.literal("\u2B0A"),
                button -> NetworkHandler.sendToServer(new PacketToggleForceDropItems()), narration -> Component.literal("Force Drop Items"));
        directionalPlacementButton = new ToggleButton(relativeX + 100, relativeY + 66, 20, 20, Component.literal("\u2927"),
                button -> NetworkHandler.sendToServer(new PacketToggleDirectionalPlacement()), narration -> Component.literal("Directional Placement"));
        fuzzyPlacementButton = new ToggleButton(relativeX + 52, relativeY + 106, 20, 20, Component.literal("\u224B"),
                button -> NetworkHandler.sendToServer(new PacketToggleFuzzyPlacement()), narration -> Component.literal("Fuzzy Placement"));
        voidItemsButton = new ToggleButton(relativeX + 140, relativeY + 66, 20, 20, Component.literal("\u2A37"),
                button -> NetworkHandler.sendToServer(new PacketToggleVoidItems()), narration -> Component.literal("Void Items"));
        selectiveReplacementButton = new ToggleButton(relativeX + 20, relativeY + 106, 20, 20, Component.literal("\u2699"),
                button -> NetworkHandler.sendToServer(new PacketToggleSelectiveReplacement()), narration -> Component.literal("Selective Replacement"));
        this.addRenderableWidget(decreaseRangeButton);
        this.addRenderableWidget(increaseRangeButton);
        this.addRenderableWidget(modeSwitchButton);
        this.addRenderableWidget(forceDropItemsButton);
        this.addRenderableWidget(directionalPlacementButton);
        this.addRenderableWidget(fuzzyPlacementButton);
        this.addRenderableWidget(voidItemsButton);
        this.addRenderableWidget(selectiveReplacementButton);
        fuzzyPlacementChanceField = new EditBox(this.font, relativeX + 92, relativeY + 108, 26, 16, Component.empty());
        fuzzyPlacementChanceField.setMaxLength(3);
        fuzzyPlacementChanceField.setValue(chance);
        fuzzyPlacementChanceField.setHint(Component.literal("100"));
        fuzzyPlacementChanceField.setFocused(false);
        fuzzyPlacementChanceField.setCanLoseFocus(true);
        this.addRenderableWidget(fuzzyPlacementChanceField);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        ItemStack stack = mc.player.getMainHandItem();
        ItemExchangerBase exchanger = (ItemExchangerBase) stack.getItem();
        int relativeX = (this.width - W) / 2;
        int relativeY = (this.height - H) / 2;
        guiGraphics.renderItem(stack, relativeX + 82, relativeY + 8);

        // Get fresh data each frame to ensure GUI stays synchronized
        int range = ExchangerHandler.getCurrentRange(stack);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        // Update button states with fresh data
        decreaseRangeButton.setButtonDisabled(range == 0);
        increaseRangeButton.setButtonDisabled(range == exchanger.getMaxRange());
        forceDropItemsButton.setButtonToggled(ExchangerHandler.getForceDropItems(stack));
        directionalPlacementButton.setButtonToggled(ExchangerHandler.getDirectionalPlacement(stack));
        fuzzyPlacementButton.setButtonToggled(ExchangerHandler.getFuzzyPlacement(stack));
        voidItemsButton.setButtonToggled(ExchangerHandler.getVoidItems(stack));
        selectiveReplacementButton.setButtonToggled(ExchangerHandler.getSelectiveReplacement(stack));

        // Get fresh tag for tooltips
        CompoundTag tag = NBTHelper.getTag(stack);
        if (decreaseRangeButton.isHovered()) {
            drawToolTip(guiGraphics, Collections.singletonList(StringHelper.localize(Reference.MODID, "tooltip.decrease_range_button")), mouseX, mouseY);
        }
        if (increaseRangeButton.isHovered()) {
            drawToolTip(guiGraphics, Collections.singletonList(StringHelper.localize(Reference.MODID, "tooltip.increase_range_button")), mouseX, mouseY);
        }
        if (modeSwitchButton.isHovered()) {
            int mode = tag.getInt("mode");
            ExchangerMode currentMode = ExchangerMode.fromOrdinal(mode);
            switch (mode) {
                case 0 ->
                        drawToolTip(guiGraphics, ImmutableList.of(StringHelper.localize(Reference.MODID, "tooltip.mode_switch_button"),
                                StringHelper.localize(Reference.MODID, "tooltip.current_mode", Component.literal(currentMode.getDisplayName()).withStyle(ChatFormatting.GREEN))), mouseX, mouseY);
                case 1 ->
                        drawToolTip(guiGraphics, ImmutableList.of(StringHelper.localize(Reference.MODID, "tooltip.mode_switch_button"),
                                StringHelper.localize(Reference.MODID, "tooltip.current_mode", Component.literal(currentMode.getDisplayName()).withStyle(ChatFormatting.GREEN))), mouseX, mouseY);
                case 2 ->
                        drawToolTip(guiGraphics, ImmutableList.of(StringHelper.localize(Reference.MODID, "tooltip.mode_switch_button"),
                                StringHelper.localize(Reference.MODID, "tooltip.current_mode", Component.literal(currentMode.getDisplayName()).withStyle(ChatFormatting.GREEN))), mouseX, mouseY);
            }
        }
        if (forceDropItemsButton.isHovered()) {
            drawToolTip(guiGraphics, ImmutableList.of(StringHelper.localize(Reference.MODID, "tooltip.force_drop_items_button_name"),
                    StringHelper.localize(Reference.MODID, "tooltip.force_drop_items_button_desc"),
                    StringHelper.localize(Reference.MODID, "tooltip.state", Reference.getStateString(tag.getBoolean("forceDropItems")))), mouseX, mouseY);
        }
        if (directionalPlacementButton.isHovered()) {
            drawToolTip(guiGraphics, ImmutableList.of(StringHelper.localize(Reference.MODID, "tooltip.directional_placement_button_name"),
                    StringHelper.localize(Reference.MODID, "tooltip.directional_placement_button_desc"),
                    StringHelper.localize(Reference.MODID, "tooltip.state", Reference.getStateString(tag.getBoolean("directionalPlacement")))), mouseX, mouseY);
        }
        if (fuzzyPlacementButton.isHovered()) {
            drawToolTip(guiGraphics, ImmutableList.of(StringHelper.localize(Reference.MODID, "tooltip.fuzzy_placement_button_name"),
                    StringHelper.localize(Reference.MODID, "tooltip.fuzzy_placement_button_desc"),
                    StringHelper.localize(Reference.MODID, "tooltip.state", Reference.getStateString(tag.getBoolean("fuzzyPlacement")))), mouseX, mouseY);
        }
        if (voidItemsButton.isHovered()) {
            drawToolTip(guiGraphics, ImmutableList.of(StringHelper.localize(Reference.MODID, "tooltip.void_items_button_name"),
                    StringHelper.localize(Reference.MODID, "tooltip.void_items_button_desc"),
                    StringHelper.localize(Reference.MODID, "tooltip.state", Reference.getStateString(tag.getBoolean("voidItems")))), mouseX, mouseY);
        }
        if (selectiveReplacementButton.isHovered()) {
            drawToolTip(guiGraphics, ImmutableList.of(StringHelper.localize(Reference.MODID, "tooltip.selective_replacement_button_name"),
                    StringHelper.localize(Reference.MODID, "tooltip.selective_replacement_button_desc"),
                    StringHelper.localize(Reference.MODID, "tooltip.state", Reference.getStateString(tag.getBoolean("selectiveReplacement")))), mouseX, mouseY);
        }
        if (fuzzyPlacementChanceField.isMouseOver(mouseX, mouseY)) {
            drawToolTip(guiGraphics, ImmutableList.of(StringHelper.localize(Reference.MODID, "tooltip.fuzzy_placement_chance_box_name"),
                    StringHelper.localize(Reference.MODID, "tooltip.fuzzy_placement_chance_box_desc")), mouseX, mouseY);
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        Font fontRenderer = mc.font;
        int relativeX = (this.width - W) / 2;
        int relativeY = (this.height - H) / 2;
        guiGraphics.blit(GUI_IMAGE, relativeX, relativeY, 0, 0, W, H);

        // Get range consistently from the same tag used in render()
        ItemStack stack = mc.player.getMainHandItem();
        if (!stack.isEmpty() && stack.getItem() instanceof ItemExchangerBase) {
            int range = ExchangerHandler.getCurrentRange(stack);
            // Bounds check to prevent array index errors
            if (range >= 0 && range < ExchangerHandler.rangeList.length) {
                String exchangeRange = ExchangerHandler.rangeList[range];
                guiGraphics.drawString(fontRenderer, exchangeRange, relativeX + 90 - fontRenderer.width(exchangeRange) / 2.0f, relativeY + 34, -1, true);
            }
        }

        guiGraphics.drawString(fontRenderer, "%", relativeX + 121, relativeY + 112, -1, true);
    }

    private void drawToolTip(GuiGraphics guiGraphics, List<Component> tooltips, int x, int y) {
        Screen screen = mc.screen;
        if (screen == null) {
            return;
        }
        guiGraphics.renderTooltip(font, tooltips, ItemStack.EMPTY.getTooltipImage(), x, y);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (fuzzyPlacementChanceField.isFocused()) {
            if (!fuzzyPlacementChanceField.mouseClicked(mouseX, mouseY, button)) {
                fuzzyPlacementChanceField.setFocused(false);
                parseFuzzyPlacementChance(fuzzyPlacementChanceField, 1, 100);
            }
        } else {
            if (!fuzzyPlacementChanceField.getValue().isEmpty()) {
                parseFuzzyPlacementChance(fuzzyPlacementChanceField, 1, 100);
            } else {
                fuzzyPlacementChanceField.setValue(chance);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void parseFuzzyPlacementChance(EditBox field, int min, int max) {
        try {
            int value = Integer.parseInt(field.getValue());
            boolean valid = value >= min && value <= max;
            if (valid) {
                NetworkHandler.sendToServer(new PacketSetFuzzyPlacementChance(value));
                this.chance = String.valueOf(value);
            } else {
                NetworkHandler.sendToServer(new PacketSetFuzzyPlacementChance(max));
                this.chance = String.valueOf(max);
                field.setValue(String.valueOf(max));
            }
        } catch (NumberFormatException exception) {
            NetworkHandler.sendToServer(new PacketSetFuzzyPlacementChance(max));
            this.chance = String.valueOf(max);
            field.setValue(String.valueOf(max));
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        super.onClose();
    }
}