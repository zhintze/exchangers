package jackyy.exchangers.item.vanilla;

import jackyy.exchangers.item.ItemExchangerBase;
import net.minecraft.world.item.Item;

public class ItemStoneExchanger extends ItemExchangerBase {

    public ItemStoneExchanger() {
        super(new Item.Properties().durability(384));
    }

    @Override
    public int getTier() {
        return 1;
    }

    @Override
    public String getHarvestLevel() {
        return "minecraft:stone";
    }

    @Override
    public int getMaxRange() {
        return 1;
    }
}