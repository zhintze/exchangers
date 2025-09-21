package jackyy.exchangers.handler.network;

import jackyy.exchangers.handler.network.packet.*;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkHandler {

    public static void registerMessages(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.0.0");

        // Register all packets
        registrar.playToServer(PacketIncreaseRange.TYPE, PacketIncreaseRange.STREAM_CODEC, (packet, context) -> packet.handle(context));
        registrar.playToServer(PacketDecreaseRange.TYPE, PacketDecreaseRange.STREAM_CODEC, (packet, context) -> packet.handle(context));
        registrar.playToServer(PacketSwitchMode.TYPE, PacketSwitchMode.STREAM_CODEC, (packet, context) -> packet.handle(context));
        registrar.playToServer(PacketToggleDirectionalPlacement.TYPE, PacketToggleDirectionalPlacement.STREAM_CODEC, (packet, context) -> packet.handle(context));
        registrar.playToServer(PacketToggleFuzzyPlacement.TYPE, PacketToggleFuzzyPlacement.STREAM_CODEC, (packet, context) -> packet.handle(context));
        registrar.playToServer(PacketSetFuzzyPlacementChance.TYPE, PacketSetFuzzyPlacementChance.STREAM_CODEC, (packet, context) -> packet.handle(context));
        registrar.playToServer(PacketToggleVoidItems.TYPE, PacketToggleVoidItems.STREAM_CODEC, (packet, context) -> packet.handle(context));
        registrar.playToServer(PacketToggleForceDropItems.TYPE, PacketToggleForceDropItems.STREAM_CODEC, (packet, context) -> packet.handle(context));
    }

    public static void sendToClient(CustomPacketPayload packet, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, packet);
    }

    public static void sendToServer(CustomPacketPayload packet) {
        PacketDistributor.sendToServer(packet);
    }

}