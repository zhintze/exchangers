package jackyy.exchangers.item.immersiveengineering;

import jackyy.exchangers.item.ItemExchangerBasePowered;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemMVExchanger extends ItemExchangerBasePowered {

    public ItemMVExchanger() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public int getTier() {
        return 4;
    }

    @Override
    public String getHarvestLevel() {
        return "minecraft:iron";
    }

    @Override
    public int getMaxRange() {
        return 4;
    }

    @Override
    public int getMaxEnergyCapacity() {
        return 200000; // 200k FE for MV tier
    }

    @Override
    public int getEnergyPerBlock() {
        return 200; // 200 FE per block (more efficient)
    }
}