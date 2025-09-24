package me.pajic.oxidizable_copper_gear.data;

import com.mojang.serialization.Codec;
import me.pajic.oxidizable_copper_gear.Main;
import me.pajic.oxidizable_copper_gear.recipe.ItemAxingRecipe;
import me.pajic.oxidizable_copper_gear.recipe.ItemWaxingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CommonData {

    public static final DataComponentType<Integer> OXIDATION = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Main.withModNamespace("oxidation"),
            DataComponentType.<Integer>builder().persistent(Codec.INT).build()
    );
    public static final DataComponentType<Boolean> WAXED = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Main.withModNamespace("waxed"),
            DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build()
    );
    public static final TagKey<Item> OXIDIZABLE = TagKey.create(
            Registries.ITEM,
            Main.withModNamespace("oxidizable")
    );
    public static RecipeSerializer<ItemWaxingRecipe> ITEM_WAXING = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            "crafting_special_item_waxing",
            new CustomRecipe.Serializer<>(ItemWaxingRecipe::new)
    );
    public static RecipeSerializer<ItemAxingRecipe> ITEM_AXING = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            "crafting_special_item_axing",
            new CustomRecipe.Serializer<>(ItemAxingRecipe::new)
    );

    public static void init() {}

    public static int getItemOxidation(ItemStack stack) {
        return stack.getOrDefault(OXIDATION, 0);
    }

    public static void incrementItemOxidation(ItemStack stack, int increment) {
        int updatedOxidation = getItemOxidation(stack) + increment;
        if (updatedOxidation <= 0) stack.remove(OXIDATION);
        else stack.set(OXIDATION, Mth.clamp(updatedOxidation, 1, 3));
    }
}
