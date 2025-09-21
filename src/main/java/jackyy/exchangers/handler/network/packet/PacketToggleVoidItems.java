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

public record PacketToggleVoidItems() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PacketToggleVoidItems> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Reference.MODID, "toggle_void_items"));
    public static final StreamCodec<ByteBuf, PacketToggleVoidItems> STREAM_CODEC = StreamCodec.unit(new PacketToggleVoidItems());

    @Override
    public Type<PacketToggleVoidItems> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                ItemStack heldItem = player.getMainHandItem();
                if (!heldItem.isEmpty() && heldItem.getItem() instanceof IExchanger) {
                    ExchangerHandler.toggleVoidItems(heldItem, player);
                    player.getInventory().setChanged();
                }
            }
        });
    }

}