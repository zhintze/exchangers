package jackyy.exchangers.item;

import jackyy.exchangers.handler.ExchangerHandler;
import jackyy.exchangers.util.IExchanger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ItemExchangerBase extends Item implements IExchanger {

    public ItemExchangerBase(Properties props) {
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
            ExchangerHandler.setDefaultTagCompound(mainHandStack);

            if (player.isShiftKeyDown()) {
                ExchangerHandler.selectBlock(mainHandStack, player, world, pos);
            } else {
                ExchangerHandler.placeBlock(mainHandStack, player, world, pos, side, context);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.isDamaged();
    }
}