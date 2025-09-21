package jackyy.exchangers.item.special;

import jackyy.exchangers.item.ItemExchangerBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemTuberousExchanger extends ItemExchangerBase {

    public ItemTuberousExchanger() {
        super(new Item.Properties().durability(296).rarity(Rarity.RARE));
    }

    @Override
    public int getTier() {
        return 2;
    }

    @Override
    public String getHarvestLevel() {
        return "minecraft:stone";
    }

    @Override
    public int getMaxRange() {
        return 2;
    }
}