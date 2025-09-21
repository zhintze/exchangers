package jackyy.exchangers.item.special;

import jackyy.exchangers.item.ItemExchangerBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemBeeExchanger extends ItemExchangerBase {

    public ItemBeeExchanger() {
        super(new Item.Properties().durability(420).rarity(Rarity.UNCOMMON));
    }

    @Override
    public int getTier() {
        return 3;
    }

    @Override
    public String getHarvestLevel() {
        return "minecraft:iron";
    }

    @Override
    public int getMaxRange() {
        return 3;
    }
}