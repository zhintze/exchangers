package jackyy.exchangers.handler.network.packet;

import io.netty.buffer.ByteBuf;
import jackyy.exchangers.handler.ExchangerHandler;
import jackyy.exchangers.util.IExchanger;
import jackyy.exchangers.util.Reference;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketSetFuzzyPlacementChance(int chance) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PacketSetFuzzyPlacementChance> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Reference.MODID, "set_fuzzy_placement_chance"));
    public static final StreamCodec<ByteBuf, PacketSetFuzzyPlacementChance> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            PacketSetFuzzyPlacementChance::chance,
            PacketSetFuzzyPlacementChance::new
    );

    @Override
    public Type<PacketSetFuzzyPlacementChance> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                ItemStack heldItem = player.getMainHandItem();
                if (!heldItem.isEmpty() && heldItem.getItem() instanceof IExchanger) {
                    ExchangerHandler.setFuzzyPlacementChance(heldItem, player, chance);
                    player.getInventory().setChanged();
                }
            }
        });
    }

}