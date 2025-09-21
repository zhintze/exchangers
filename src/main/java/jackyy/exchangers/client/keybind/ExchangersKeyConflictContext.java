package jackyy.exchangers.client.keybind;

import jackyy.exchangers.util.IExchanger;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.settings.IKeyConflictContext;

public class ExchangersKeyConflictContext implements IKeyConflictContext {
    public static final ExchangersKeyConflictContext EXCHANGER_CONFLICT_CONTEXT = new ExchangersKeyConflictContext();

    @Override
    public boolean isActive() {
        return !conflicts(this);
    }

    @Override
    public boolean conflicts(IKeyConflictContext other) {
        if (other == this) {
            return false;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return true;
        }

        ItemStack heldItem = mc.player.getMainHandItem();
        if (heldItem.isEmpty()) {
            return true;
        }

        return !(heldItem.getItem() instanceof IExchanger);
    }
}