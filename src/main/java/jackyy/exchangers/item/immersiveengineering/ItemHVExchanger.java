package jackyy.exchangers.item.immersiveengineering;

import jackyy.exchangers.item.ItemExchangerBasePowered;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemHVExchanger extends ItemExchangerBasePowered {

    public ItemHVExchanger() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
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

    @Override
    public int getMaxEnergyCapacity() {
        return 800000; // 800k FE for HV tier
    }

    @Override
    public int getEnergyPerBlock() {
        return 150; // 150 FE per block (very efficient)
    }
}