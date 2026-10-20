package me.pajic.oxidizable_copper_gear.registry;

import me.pajic.oxidizable_copper_gear.recipe.ItemAxingRecipe;
import me.pajic.oxidizable_copper_gear.recipe.ItemWaxingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

//? <26.1
//import net.minecraft.world.item.crafting.CustomRecipe;

public class ModRecipeSerializers {

	public static RecipeSerializer<ItemWaxingRecipe> ITEM_WAXING =
			//? <26.1 {
			/*new CustomRecipe.Serializer<>(ItemWaxingRecipe::new);
			*///?} else {
			new RecipeSerializer<>(ItemWaxingRecipe.MAP_CODEC, ItemWaxingRecipe.STREAM_CODEC);
			//?}

	public static RecipeSerializer<ItemAxingRecipe> ITEM_AXING =
			//? <26.1 {
			/*new CustomRecipe.Serializer<>(ItemAxingRecipe::new);
			*///?} else {
			new RecipeSerializer<>(ItemAxingRecipe.MAP_CODEC, ItemAxingRecipe.STREAM_CODEC);
			//?}
}
