package jackyy.exchangers.handler;

import jackyy.exchangers.client.keybind.Keys;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = "exchangers", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientEventsHandler {

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(Keys.INCREASE_RANGE);
        event.register(Keys.DECREASE_RANGE);
        event.register(Keys.SWITCH_MODE);
        event.register(Keys.TOGGLE_FUZZY_PLACEMENT);
        event.register(Keys.TOGGLE_DIRECTIONAL_PLACEMENT);
        event.register(Keys.TOGGLE_VOID_ITEMS);
        event.register(Keys.TOGGLE_FORCE_DROP);
        event.register(Keys.TOGGLE_SELECTIVE_REPLACEMENT);
        event.register(Keys.OPEN_GUI);
    }

}