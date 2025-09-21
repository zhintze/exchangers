package jackyy.exchangers.item.vanilla;

import jackyy.exchangers.item.ItemExchangerBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemEmeraldExchanger extends ItemExchangerBase {

    public ItemEmeraldExchanger() {
        super(new Item.Properties().durability(8192).rarity(Rarity.EPIC));
    }

    @Override
    public int getTier() {
        return 5;
    }

    @Override
    public String getHarvestLevel() {
        return "minecraft:diamond";
    }

    @Override
    public int getMaxRange() {
        return 5;
    }
}