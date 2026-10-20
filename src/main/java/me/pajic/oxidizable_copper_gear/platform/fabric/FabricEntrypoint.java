package me.pajic.oxidizable_copper_gear.platform.fabric;

//? fabric {

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import me.pajic.oxidizable_copper_gear.OCG;
import me.pajic.oxidizable_copper_gear.registry.ModDataComponents;
import me.pajic.oxidizable_copper_gear.registry.ModRecipeSerializers;
import me.pajic.oxidizable_copper_gear.registry.ModTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

//? <26.1 {
/*import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
*///?} else {
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
//?}

@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, OCG.id("oxidation"), ModDataComponents.OXIDATION);
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, OCG.id("waxed"), ModDataComponents.WAXED);
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.withDefaultNamespace("crafting_special_item_waxing"), ModRecipeSerializers.ITEM_WAXING);
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.withDefaultNamespace("crafting_special_item_axing"), ModRecipeSerializers.ITEM_AXING);
		//~ if <26.1 'CreativeModeTabEvents.MODIFY_OUTPUT_ALL' -> 'ItemGroupEvents.MODIFY_ENTRIES_ALL'
		CreativeModeTabEvents.MODIFY_OUTPUT_ALL.register((tab, entries) ->
				BuiltInRegistries.ITEM.getTagOrEmpty(ModTags.OXIDIZABLE).forEach(item -> {
					ItemStack originalItem = new ItemStack(item);
					if (tab.contains(originalItem)) {
						for (int i = 3; i > 0; i--) {
							ItemStack oxidizedItem = new ItemStack(item);
							oxidizedItem.set(ModDataComponents.OXIDATION, i);
							ItemStack waxedOxidizedItem = oxidizedItem.copy();
							waxedOxidizedItem.set(ModDataComponents.WAXED, true);
							//~ if <26.1 'insertAfter' -> 'addAfter'
							entries.insertAfter(originalItem, waxedOxidizedItem);
							//~ if <26.1 'insertAfter' -> 'addAfter'
							entries.insertAfter(originalItem, oxidizedItem);
						}
						ItemStack waxedItem = originalItem.copy();
						waxedItem.set(ModDataComponents.WAXED, true);
						//~ if <26.1 'insertAfter' -> 'addAfter'
						entries.insertAfter(originalItem, waxedItem);
					}
				})
		);
		FabricLoader.getInstance().getModContainer(OCG.MOD_ID).ifPresent(container ->
				//~ if <26.1 'ResourceLoader.registerBuiltinPack' -> 'ResourceManagerHelper.registerBuiltinResourcePack'
				ResourceLoader.registerBuiltinPack(
						OCG.id(OCG.DEFAULT_DATA_PACK),
						container,
						Component.literal("Oxidizable Copper Gear Default Pack"),
						//~ if <26.1 'PackActivationType' -> 'ResourcePackActivationType'
						PackActivationType.DEFAULT_ENABLED
				)
		);
	}
}
//?}
