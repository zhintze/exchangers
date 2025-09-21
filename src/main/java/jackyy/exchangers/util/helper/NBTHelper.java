package jackyy.exchangers.util.helper;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class NBTHelper {

    public static CompoundTag getTag(ItemStack stack) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return customData.copyTag();
    }

    public static boolean hasTag(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        return customData != null && !customData.isEmpty();
    }

    public static void updateCustomData(ItemStack stack, CompoundTag tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public static void setInt(ItemStack stack, String key, int val) {
        CompoundTag tag = getTag(stack);
        tag.putInt(key, val);
        updateCustomData(stack, tag);
    }

    public static int getInt(ItemStack stack, String key) {
        return hasTag(stack) ? getTag(stack).getInt(key) : 0;
    }

    public static void setString(ItemStack stack, String key, String val) {
        CompoundTag tag = getTag(stack);
        tag.putString(key, val);
        updateCustomData(stack, tag);
    }

    public static String getString(ItemStack stack, String key) {
        return hasTag(stack) ? getTag(stack).getString(key) : "";
    }

    public static void setBoolean(ItemStack stack, String key, boolean val) {
        CompoundTag tag = getTag(stack);
        tag.putBoolean(key, val);
        updateCustomData(stack, tag);
    }

    public static boolean getBoolean(ItemStack stack, String key) {
        return hasTag(stack) ? getTag(stack).getBoolean(key) : false;
    }

    public static void setCompoundTag(ItemStack stack, String key, CompoundTag value) {
        CompoundTag tag = getTag(stack);
        tag.put(key, value);
        updateCustomData(stack, tag);
    }

    public static boolean hasInt(ItemStack stack, String key) {
        return hasTag(stack) && getTag(stack).contains(key, 3); // 3 = INT tag type
    }

    public static boolean hasBoolean(ItemStack stack, String key) {
        return hasTag(stack) && getTag(stack).contains(key, 1); // 1 = BYTE tag type (boolean)
    }

    public static boolean hasCompoundTag(ItemStack stack, String key) {
        return hasTag(stack) && getTag(stack).contains(key, 10); // 10 = COMPOUND tag type
    }
}