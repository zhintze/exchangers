package jackyy.exchangers.item.mekanism;

import jackyy.exchangers.item.ItemExchangerBasePowered;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemBasicExchanger extends ItemExchangerBasePowered {

    public ItemBasicExchanger() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public int getTier() {
        return 3;
    }

    @Override
    public String getHarvestLevel() {
        return "minecraft:stone";
    }

    @Override
    public int getMaxRange() {
        return 3;
    }

    @Override
    public int getMaxEnergyCapacity() {
        return 100000; // 100k FE for Basic tier
    }

    @Override
    public int getEnergyPerBlock() {
        return 200; // 200 FE per block
    }
}