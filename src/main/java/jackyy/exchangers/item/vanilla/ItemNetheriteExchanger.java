package jackyy.exchangers.item.vanilla;

import jackyy.exchangers.item.ItemExchangerBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemNetheriteExchanger extends ItemExchangerBase {

    public ItemNetheriteExchanger() {
        super(new Item.Properties().durability(16384).rarity(Rarity.EPIC));
    }

    @Override
    public int getTier() {
        return 7;
    }

    @Override
    public String getHarvestLevel() {
        return "minecraft:netherite";
    }

    @Override
    public int getMaxRange() {
        return 7;
    }
}