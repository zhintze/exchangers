package jackyy.exchangers.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class Reference {
    public static final String MODID = "exchangers";
    public static final String NAME = "Exchangers";
    public static final String VERSION = "3.6.0";
    public static final String DEPENDENCIES = "required-after:neoforge";

    public static Component getStateString(boolean state) {
        if (state) {
            return Component.literal("ON").withStyle(ChatFormatting.GREEN);
        } else {
            return Component.literal("OFF").withStyle(ChatFormatting.RED);
        }
    }
}