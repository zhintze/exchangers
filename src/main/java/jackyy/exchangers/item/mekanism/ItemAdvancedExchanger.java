package jackyy.exchangers.item.mekanism;

import jackyy.exchangers.item.ItemExchangerBasePowered;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemAdvancedExchanger extends ItemExchangerBasePowered {

    public ItemAdvancedExchanger() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public int getTier() {
        return 5;
    }

    @Override
    public String getHarvestLevel() {
        return "minecraft:iron";
    }

    @Override
    public int getMaxRange() {
        return 5;
    }

    @Override
    public int getMaxEnergyCapacity() {
        return 400000; // 400k FE for Advanced tier
    }

    @Override
    public int getEnergyPerBlock() {
        return 150; // 150 FE per block (more efficient than Basic)
    }
}