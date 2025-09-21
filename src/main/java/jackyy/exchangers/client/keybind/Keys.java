package jackyy.exchangers.client.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

public class Keys {
    public static final String CATEGORY = "key.categories.exchangers";

    public static final KeyMapping INCREASE_RANGE = new KeyMapping(
        "key.exchangers.increase_range",
        ExchangersKeyConflictContext.EXCHANGER_CONFLICT_CONTEXT,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_PERIOD,
        CATEGORY
    );

    public static final KeyMapping DECREASE_RANGE = new KeyMapping(
        "key.exchangers.decrease_range",
        ExchangersKeyConflictContext.EXCHANGER_CONFLICT_CONTEXT,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_COMMA,
        CATEGORY
    );

    public static final KeyMapping SWITCH_MODE = new KeyMapping(
        "key.exchangers.switch_mode",
        ExchangersKeyConflictContext.EXCHANGER_CONFLICT_CONTEXT,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_M,
        CATEGORY
    );

    public static final KeyMapping TOGGLE_FUZZY_PLACEMENT = new KeyMapping(
        "key.exchangers.toggle_fuzzy_placement",
        ExchangersKeyConflictContext.EXCHANGER_CONFLICT_CONTEXT,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_F,
        CATEGORY
    );

    public static final KeyMapping TOGGLE_DIRECTIONAL_PLACEMENT = new KeyMapping(
        "key.exchangers.toggle_directional_placement",
        ExchangersKeyConflictContext.EXCHANGER_CONFLICT_CONTEXT,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_D,
        CATEGORY
    );

    public static final KeyMapping TOGGLE_VOID_ITEMS = new KeyMapping(
        "key.exchangers.toggle_void_items",
        ExchangersKeyConflictContext.EXCHANGER_CONFLICT_CONTEXT,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_V,
        CATEGORY
    );

    public static final KeyMapping TOGGLE_FORCE_DROP = new KeyMapping(
        "key.exchangers.toggle_force_drop",
        ExchangersKeyConflictContext.EXCHANGER_CONFLICT_CONTEXT,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_G,
        CATEGORY
    );

    public static final KeyMapping TOGGLE_SELECTIVE_REPLACEMENT = new KeyMapping(
        "key.exchangers.toggle_selective_replacement",
        ExchangersKeyConflictContext.EXCHANGER_CONFLICT_CONTEXT,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_R,
        CATEGORY
    );

    public static final KeyMapping OPEN_GUI = new KeyMapping(
        "key.exchangers.open_gui",
        ExchangersKeyConflictContext.EXCHANGER_CONFLICT_CONTEXT,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_C,
        CATEGORY
    );
}