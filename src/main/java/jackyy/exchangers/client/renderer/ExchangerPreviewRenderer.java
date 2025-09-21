package jackyy.exchangers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import jackyy.exchangers.handler.ExchangerHandler;
import jackyy.exchangers.util.ExchangerMode;
import jackyy.exchangers.util.IExchanger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class ExchangerPreviewRenderer {

    public static void renderBlockPreviews(PoseStack poseStack, Vec3 cameraPos) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        Level level = mc.level;

        if (player == null || level == null) {
            return;
        }

        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.isEmpty() || !(heldItem.getItem() instanceof IExchanger)) {
            return;
        }

        // Check if player is looking at a block
        HitResult hitResult = mc.hitResult;
        if (!(hitResult instanceof BlockHitResult blockHit) || hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockPos targetPos = blockHit.getBlockPos();
        Direction face = blockHit.getDirection();

        // Get current range and mode
        int range = ExchangerHandler.getCurrentRange(heldItem);
        ExchangerMode mode = ExchangerHandler.getCurrentMode(heldItem);

        // Get positions that would be affected
        List<BlockPos> positions;
        if (range == 0) {
            positions = List.of(targetPos);
        } else {
            positions = ExchangerHandler.getPositionsForMode(targetPos, face, range, mode);
        }

        // Get buffer source from Minecraft renderer
        MultiBufferSource buffer = mc.renderBuffers().bufferSource();

        // Render black outlines for each position
        VertexConsumer lineConsumer = buffer.getBuffer(RenderType.lines());

        for (BlockPos pos : positions) {
            // Skip if block doesn't exist or is out of bounds
            if (!level.isLoaded(pos)) {
                continue;
            }

            // Calculate relative position from camera
            double x = pos.getX() - cameraPos.x;
            double y = pos.getY() - cameraPos.y;
            double z = pos.getZ() - cameraPos.z;

            // Render black outline around the block
            VoxelShape shape = Shapes.block();
            LevelRenderer.renderLineBox(
                poseStack,
                lineConsumer,
                shape.bounds().move(x, y, z),
                0.0f, 0.0f, 0.0f, 0.8f // Black with some transparency
            );
        }
    }

}