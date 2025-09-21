package jackyy.exchangers.registry;

import jackyy.exchangers.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MODID);

    public static final Supplier<CreativeModeTab> MAIN_CREATIVE_TAB = CREATIVE_MODE_TABS.register("main_creative_tab", () ->
        CreativeModeTab.builder()
            .title(Component.translatable("item_group.exchangers.main_creative_tab"))
            .icon(() -> new ItemStack(ModItems.STONE_EXCHANGER.get()))
            .displayItems((parameters, output) -> {
                // Vanilla Exchangers
                output.accept(ModItems.WOODEN_EXCHANGER.get());
                output.accept(ModItems.STONE_EXCHANGER.get());
                output.accept(ModItems.GOLDEN_EXCHANGER.get());
                output.accept(ModItems.COPPER_EXCHANGER.get());
                output.accept(ModItems.IRON_EXCHANGER.get());
                output.accept(ModItems.DIAMOND_EXCHANGER.get());
                output.accept(ModItems.EMERALD_EXCHANGER.get());
                output.accept(ModItems.AMETHYST_EXCHANGER.get());
                output.accept(ModItems.OBSIDIAN_EXCHANGER.get());
                output.accept(ModItems.NETHERITE_EXCHANGER.get());
                output.accept(ModItems.END_EXCHANGER.get());

                // Special Exchangers
                output.accept(ModItems.BEE_EXCHANGER.get());
                output.accept(ModItems.TUBEROUS_EXCHANGER.get());
                output.accept(ModItems.CREATIVE_EXCHANGER.get());

                // Mekanism Exchangers
                output.accept(ModItems.BASIC_EXCHANGER.get());
                output.accept(ModItems.ADVANCED_EXCHANGER.get());
                output.accept(ModItems.ELITE_EXCHANGER.get());
                output.accept(ModItems.ULTIMATE_EXCHANGER.get());

                // Mekanism Exchanger Cores
                output.accept(ModItems.MEKANISM_EXCHANGER_CORE_T1.get());
                output.accept(ModItems.MEKANISM_EXCHANGER_CORE_T2.get());
                output.accept(ModItems.MEKANISM_EXCHANGER_CORE_T3.get());

                // Immersive Engineering Exchangers
                output.accept(ModItems.LV_EXCHANGER.get());
                output.accept(ModItems.MV_EXCHANGER.get());
                output.accept(ModItems.HV_EXCHANGER.get());

                // IE Exchanger Cores
                output.accept(ModItems.IE_EXCHANGER_CORE_T1.get());
                output.accept(ModItems.IE_EXCHANGER_CORE_T2.get());
                output.accept(ModItems.IE_EXCHANGER_CORE_T3.get());
            })
            .build()
    );
}