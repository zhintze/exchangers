package jackyy.exchangers.registry;

import jackyy.exchangers.item.vanilla.*;
import jackyy.exchangers.item.special.*;
import jackyy.exchangers.item.mekanism.*;
import jackyy.exchangers.item.immersiveengineering.*;
import jackyy.exchangers.util.Reference;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(net.minecraft.core.registries.Registries.ITEM, Reference.MODID);

    // Vanilla Exchangers
    public static final Supplier<Item> WOODEN_EXCHANGER = ITEMS.register("wooden_exchanger", ItemWoodenExchanger::new);
    public static final Supplier<Item> STONE_EXCHANGER = ITEMS.register("stone_exchanger", ItemStoneExchanger::new);
    public static final Supplier<Item> GOLDEN_EXCHANGER = ITEMS.register("golden_exchanger", ItemGoldenExchanger::new);
    public static final Supplier<Item> COPPER_EXCHANGER = ITEMS.register("copper_exchanger", ItemCopperExchanger::new);
    public static final Supplier<Item> IRON_EXCHANGER = ITEMS.register("iron_exchanger", ItemIronExchanger::new);
    public static final Supplier<Item> DIAMOND_EXCHANGER = ITEMS.register("diamond_exchanger", ItemDiamondExchanger::new);
    public static final Supplier<Item> EMERALD_EXCHANGER = ITEMS.register("emerald_exchanger", ItemEmeraldExchanger::new);
    public static final Supplier<Item> AMETHYST_EXCHANGER = ITEMS.register("amethyst_exchanger", ItemAmethystExchanger::new);
    public static final Supplier<Item> OBSIDIAN_EXCHANGER = ITEMS.register("obsidian_exchanger", ItemObsidianExchanger::new);
    public static final Supplier<Item> NETHERITE_EXCHANGER = ITEMS.register("netherite_exchanger", ItemNetheriteExchanger::new);
    public static final Supplier<Item> END_EXCHANGER = ITEMS.register("end_exchanger", ItemEndExchanger::new);

    // Special Exchangers
    public static final Supplier<Item> BEE_EXCHANGER = ITEMS.register("bee_exchanger", ItemBeeExchanger::new);
    public static final Supplier<Item> TUBEROUS_EXCHANGER = ITEMS.register("tuberous_exchanger", ItemTuberousExchanger::new);
    public static final Supplier<Item> CREATIVE_EXCHANGER = ITEMS.register("creative_exchanger", ItemCreativeExchanger::new);

    // Mekanism Exchangers
    public static final Supplier<Item> BASIC_EXCHANGER = ITEMS.register("basic_exchanger", ItemBasicExchanger::new);
    public static final Supplier<Item> ADVANCED_EXCHANGER = ITEMS.register("advanced_exchanger", ItemAdvancedExchanger::new);
    public static final Supplier<Item> ELITE_EXCHANGER = ITEMS.register("elite_exchanger", ItemEliteExchanger::new);
    public static final Supplier<Item> ULTIMATE_EXCHANGER = ITEMS.register("ultimate_exchanger", ItemUltimateExchanger::new);

    // Mekanism Exchanger Cores
    public static final Supplier<Item> MEKANISM_EXCHANGER_CORE_T1 = ITEMS.register("mekanism_exchanger_core_tier1", ItemMekanismExchangerCoreT1::new);
    public static final Supplier<Item> MEKANISM_EXCHANGER_CORE_T2 = ITEMS.register("mekanism_exchanger_core_tier2", ItemMekanismExchangerCoreT2::new);
    public static final Supplier<Item> MEKANISM_EXCHANGER_CORE_T3 = ITEMS.register("mekanism_exchanger_core_tier3", ItemMekanismExchangerCoreT3::new);

    // Immersive Engineering Exchangers
    public static final Supplier<Item> LV_EXCHANGER = ITEMS.register("lv_exchanger", ItemLVExchanger::new);
    public static final Supplier<Item> MV_EXCHANGER = ITEMS.register("mv_exchanger", ItemMVExchanger::new);
    public static final Supplier<Item> HV_EXCHANGER = ITEMS.register("hv_exchanger", ItemHVExchanger::new);

    // IE Exchanger Cores
    public static final Supplier<Item> IE_EXCHANGER_CORE_T1 = ITEMS.register("ie_exchanger_core_tier1", ItemIEExchangerCoreT1::new);
    public static final Supplier<Item> IE_EXCHANGER_CORE_T2 = ITEMS.register("ie_exchanger_core_tier2", ItemIEExchangerCoreT2::new);
    public static final Supplier<Item> IE_EXCHANGER_CORE_T3 = ITEMS.register("ie_exchanger_core_tier3", ItemIEExchangerCoreT3::new);
}