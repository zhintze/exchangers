package jackyy.exchangers;

import jackyy.exchangers.handler.network.NetworkHandler;
import jackyy.exchangers.registry.ModCreativeTab;
import jackyy.exchangers.registry.ModItems;
import jackyy.exchangers.util.Reference;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Reference.MODID)
public class Exchangers {

    public Exchangers(IEventBus modEventBus) {
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);

        // Register network handlers
        modEventBus.addListener(NetworkHandler::registerMessages);
    }
}