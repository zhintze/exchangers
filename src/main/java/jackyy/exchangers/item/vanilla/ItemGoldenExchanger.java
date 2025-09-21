package jackyy.exchangers.item.vanilla;

import jackyy.exchangers.item.ItemExchangerBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemGoldenExchanger extends ItemExchangerBase {

    public ItemGoldenExchanger() {
        super(new Item.Properties().durability(512).rarity(Rarity.UNCOMMON));
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