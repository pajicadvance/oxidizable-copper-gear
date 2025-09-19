package me.pajic.oxidizable_copper_gear.data;

import com.mojang.serialization.Codec;
import me.pajic.oxidizable_copper_gear.Main;
import me.pajic.oxidizable_copper_gear.recipe.ItemDewaxingRecipe;
import me.pajic.oxidizable_copper_gear.recipe.ItemWaxingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
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
    public static RecipeSerializer<ItemDewaxingRecipe> ITEM_DEWAXING = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            "crafting_special_item_dewaxing",
            new CustomRecipe.Serializer<>(ItemDewaxingRecipe::new)
    );

    public static void init() {}
}
