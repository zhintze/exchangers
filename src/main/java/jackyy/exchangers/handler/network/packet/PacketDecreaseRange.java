package jackyy.exchangers.handler.network.packet;

import io.netty.buffer.ByteBuf;
import jackyy.exchangers.handler.ExchangerHandler;
import jackyy.exchangers.util.IExchanger;
import jackyy.exchangers.util.Reference;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketDecreaseRange() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PacketDecreaseRange> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Reference.MODID, "decrease_range"));
    public static final StreamCodec<ByteBuf, PacketDecreaseRange> STREAM_CODEC = StreamCodec.unit(new PacketDecreaseRange());

    @Override
    public Type<PacketDecreaseRange> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                ItemStack heldItem = player.getMainHandItem();
                if (!heldItem.isEmpty() && heldItem.getItem() instanceof IExchanger) {
                    ExchangerHandler.decreaseRange(heldItem, player);
                    player.getInventory().setChanged();
                }
            }
        });
    }

}