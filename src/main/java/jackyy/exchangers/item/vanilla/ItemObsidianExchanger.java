package jackyy.exchangers.item.vanilla;

import jackyy.exchangers.item.ItemExchangerBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemObsidianExchanger extends ItemExchangerBase {

    public ItemObsidianExchanger() {
        super(new Item.Properties().durability(10240).rarity(Rarity.EPIC));
    }

    @Override
    public int getTier() {
        return 6;
    }

    @Override
    public String getHarvestLevel() {
        return "minecraft:diamond";
    }

    @Override
    public int getMaxRange() {
        return 6;
    }
}