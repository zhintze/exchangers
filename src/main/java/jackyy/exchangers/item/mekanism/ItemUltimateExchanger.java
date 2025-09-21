package jackyy.exchangers.item.mekanism;

import jackyy.exchangers.item.ItemExchangerBasePowered;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemUltimateExchanger extends ItemExchangerBasePowered {

    public ItemUltimateExchanger() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
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

    @Override
    public int getMaxEnergyCapacity() {
        return 6400000; // 6.4M FE for Ultimate tier
    }

    @Override
    public int getEnergyPerBlock() {
        return 50; // 50 FE per block (extremely efficient)
    }
}