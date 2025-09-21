package jackyy.exchangers.item.special;

import jackyy.exchangers.item.ItemExchangerBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemCreativeExchanger extends ItemExchangerBase {

    public ItemCreativeExchanger() {
        super(new Item.Properties().durability(9001).rarity(Rarity.EPIC));
    }

    @Override
    public int getTier() {
        return 12;
    }

    @Override
    public String getHarvestLevel() {
        return "minecraft:netherite";
    }

    @Override
    public int getMaxRange() {
        return 12;
    }

    @Override
    public boolean isCreative() {
        return true;
    }
}