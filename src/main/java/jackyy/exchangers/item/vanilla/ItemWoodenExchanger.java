package jackyy.exchangers.item.vanilla;

import jackyy.exchangers.item.ItemExchangerBase;
import net.minecraft.world.item.Item;

public class ItemWoodenExchanger extends ItemExchangerBase {

    public ItemWoodenExchanger() {
        super(new Item.Properties().durability(256));
    }

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public String getHarvestLevel() {
        return "minecraft:wood";
    }

    @Override
    public int getMaxRange() {
        return 0;
    }
}