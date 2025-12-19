package me.pajic.oxidizable_copper_gear.platform.fabric;

//? fabric {

import me.pajic.oxidizable_copper_gear.OCG;
import me.pajic.oxidizable_copper_gear.platform.Platform;
import me.pajic.oxidizable_copper_gear.registry.ModDataComponents;
import me.pajic.oxidizable_copper_gear.registry.ModRecipeSerializers;
import me.pajic.oxidizable_copper_gear.registry.ModTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("unused")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		initDefaultDataPack();
		initDataComponents();
		initRecipeSerializers();
		initCreativeTabs();
	}

	private static void initDefaultDataPack() {
		FabricLoader.getInstance().getModContainer(OCG.MOD_ID).ifPresent(modContainer ->
				ResourceManagerHelper.registerBuiltinResourcePack(
						OCG.id(OCG.xplat().packPath(Platform.VersionedPackType.DATA)),
						modContainer,
						Component.literal("Oxidizable Copper Gear Default Pack"),
						ResourcePackActivationType.DEFAULT_ENABLED
				)
		);
	}

	private static void initDataComponents() {
		Registry.register(
				BuiltInRegistries.DATA_COMPONENT_TYPE,
				OCG.id("oxidation"),
				ModDataComponents.OXIDATION
		);
		Registry.register(
				BuiltInRegistries.DATA_COMPONENT_TYPE,
				OCG.id("waxed"),
				ModDataComponents.WAXED
		);
	}

	private static void initRecipeSerializers() {
		Registry.register(
				BuiltInRegistries.RECIPE_SERIALIZER,
				"crafting_special_item_waxing",
				ModRecipeSerializers.ITEM_WAXING
		);
		Registry.register(
				BuiltInRegistries.RECIPE_SERIALIZER,
				"crafting_special_item_axing",
				ModRecipeSerializers.ITEM_AXING
		);
	}

	private static void initCreativeTabs() {
		ItemGroupEvents.MODIFY_ENTRIES_ALL.register((tabs, entries) ->
				BuiltInRegistries.ITEM.getTagOrEmpty(ModTags.OXIDIZABLE).forEach(item -> {
					ItemStack originalItem = new ItemStack(item);
					if (tabs.contains(originalItem)) {
						for (int i = 3; i > 0; i--) {
							ItemStack oxidizedItem = new ItemStack(item);
							oxidizedItem.set(ModDataComponents.OXIDATION, i);
							ItemStack waxedOxidizedItem = oxidizedItem.copy();
							waxedOxidizedItem.set(ModDataComponents.WAXED, true);
							entries.addAfter(originalItem, waxedOxidizedItem);
							entries.addAfter(originalItem, oxidizedItem);
						}
						ItemStack waxedItem = originalItem.copy();
						waxedItem.set(ModDataComponents.WAXED, true);
						entries.addAfter(originalItem, waxedItem);
					}
				})
		);
	}
}
//?}
