package jackyy.exchangers.item.immersiveengineering;

import jackyy.exchangers.item.ItemExchangerBasePowered;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemLVExchanger extends ItemExchangerBasePowered {

    public ItemLVExchanger() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
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

    @Override
    public int getMaxEnergyCapacity() {
        return 50000; // 50k FE for LV tier
    }

    @Override
    public int getEnergyPerBlock() {
        return 250; // 250 FE per block
    }
}