package jackyy.exchangers.item.vanilla;

import jackyy.exchangers.item.ItemExchangerBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemDiamondExchanger extends ItemExchangerBase {

    public ItemDiamondExchanger() {
        super(new Item.Properties().durability(4096).rarity(Rarity.RARE));
    }

    @Override
    public int getTier() {
        return 4;
    }

    @Override
    public String getHarvestLevel() {
        return "minecraft:diamond";
    }

    @Override
    public int getMaxRange() {
        return 4;
    }
}