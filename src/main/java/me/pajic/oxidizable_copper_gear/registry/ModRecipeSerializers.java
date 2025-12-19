package me.pajic.oxidizable_copper_gear.registry;

import me.pajic.oxidizable_copper_gear.recipe.ItemAxingRecipe;
import me.pajic.oxidizable_copper_gear.recipe.ItemWaxingRecipe;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeSerializers {

	public static RecipeSerializer<ItemWaxingRecipe> ITEM_WAXING = new CustomRecipe.Serializer<>(ItemWaxingRecipe::new);

	public static RecipeSerializer<ItemAxingRecipe> ITEM_AXING = new CustomRecipe.Serializer<>(ItemAxingRecipe::new);
}
