package jackyy.exchangers.handler;

import jackyy.exchangers.client.gui.ExchangersGuiScreen;
import jackyy.exchangers.client.keybind.Keys;
import jackyy.exchangers.handler.network.NetworkHandler;
import jackyy.exchangers.handler.network.packet.*;
import jackyy.exchangers.util.IExchanger;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(modid = "exchangers", value = Dist.CLIENT)
public class ClientKeyHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) {
            return;
        }

        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.isEmpty() || !(heldItem.getItem() instanceof IExchanger)) {
            return;
        }

        // Send network packets to server for proper client/server sync
        if (Keys.INCREASE_RANGE.consumeClick()) {
            NetworkHandler.sendToServer(new PacketIncreaseRange());
        } else if (Keys.DECREASE_RANGE.consumeClick()) {
            NetworkHandler.sendToServer(new PacketDecreaseRange());
        } else if (Keys.SWITCH_MODE.consumeClick()) {
            NetworkHandler.sendToServer(new PacketSwitchMode());
        } else if (Keys.TOGGLE_DIRECTIONAL_PLACEMENT.consumeClick()) {
            NetworkHandler.sendToServer(new PacketToggleDirectionalPlacement());
        } else if (Keys.OPEN_GUI.consumeClick()) {
            Minecraft.getInstance().setScreen(new ExchangersGuiScreen());
        } else if (Keys.TOGGLE_FUZZY_PLACEMENT.consumeClick()) {
            NetworkHandler.sendToServer(new PacketToggleFuzzyPlacement());
        } else if (Keys.TOGGLE_VOID_ITEMS.consumeClick()) {
            NetworkHandler.sendToServer(new PacketToggleVoidItems());
        } else if (Keys.TOGGLE_FORCE_DROP.consumeClick()) {
            NetworkHandler.sendToServer(new PacketToggleForceDropItems());
        }
    }

    // Removed wireframe block highlighting to match original mod behavior
    // @SubscribeEvent
    // public static void onRenderLevel(RenderLevelStageEvent event) {
    //     if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
    //         ExchangerPreviewRenderer.renderBlockPreviews(
    //             event.getPoseStack(),
    //             event.getCamera().getPosition()
    //         );
    //     }
    // }
}