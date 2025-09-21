package jackyy.exchangers.item.vanilla;

import jackyy.exchangers.item.ItemExchangerBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemCopperExchanger extends ItemExchangerBase {

    public ItemCopperExchanger() {
        super(new Item.Properties().durability(1024).rarity(Rarity.UNCOMMON));
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