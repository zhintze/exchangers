package jackyy.exchangers.item.mekanism;

import jackyy.exchangers.item.ItemExchangerBasePowered;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemEliteExchanger extends ItemExchangerBasePowered {

    public ItemEliteExchanger() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
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

    @Override
    public int getMaxEnergyCapacity() {
        return 1600000; // 1.6M FE for Elite tier
    }

    @Override
    public int getEnergyPerBlock() {
        return 100; // 100 FE per block (very efficient)
    }
}