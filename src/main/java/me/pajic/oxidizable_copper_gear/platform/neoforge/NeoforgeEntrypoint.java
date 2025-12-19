package me.pajic.oxidizable_copper_gear.platform.neoforge;

//? neoforge {

/*import me.pajic.oxidizable_copper_gear.OCG;
import me.pajic.oxidizable_copper_gear.platform.Platform;
import me.pajic.oxidizable_copper_gear.registry.ModDataComponents;
import me.pajic.oxidizable_copper_gear.registry.ModRecipeSerializers;
import me.pajic.oxidizable_copper_gear.registry.ModTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(OCG.MOD_ID)
@EventBusSubscriber(modid = OCG.MOD_ID)
public class NeoforgeEntrypoint {

	@SubscribeEvent
	private static void initDefaultDataPack(AddPackFindersEvent event) {
		event.addPackFinders(
				OCG.id(OCG.xplat().packPath(Platform.VersionedPackType.DATA)),
				PackType.SERVER_DATA,
				Component.literal("Oxidizable Copper Gear Default Pack"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
	}

	@SubscribeEvent
	private static void initRegistry(RegisterEvent event) {
		event.register(
				Registries.DATA_COMPONENT_TYPE,
				registry -> {
					registry.register(OCG.id("oxidation"), ModDataComponents.OXIDATION);
					registry.register(OCG.id("waxed"), ModDataComponents.WAXED);
				}
		);
		event.register(
				Registries.RECIPE_SERIALIZER,
				registry -> {
					registry.register(Identifier.withDefaultNamespace("crafting_special_item_waxing"), ModRecipeSerializers.ITEM_WAXING);
					registry.register(Identifier.withDefaultNamespace("crafting_special_item_axing"), ModRecipeSerializers.ITEM_AXING);
				}
		);
	}

	@SubscribeEvent
	private static void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() != CreativeModeTabs.SEARCH) BuiltInRegistries.ITEM.getTagOrEmpty(ModTags.OXIDIZABLE).forEach(item -> {
			ItemStack originalItem = new ItemStack(item);
			if (event.getParentEntries().contains(originalItem)) {
				for (int i = 3; i > 0; i--) {
					ItemStack oxidizedItem = new ItemStack(item);
					oxidizedItem.set(ModDataComponents.OXIDATION, i);
					ItemStack waxedOxidizedItem = oxidizedItem.copy();
					waxedOxidizedItem.set(ModDataComponents.WAXED, true);
					event.insertAfter(originalItem, waxedOxidizedItem, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
					event.insertAfter(originalItem, oxidizedItem, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
				}
				ItemStack waxedItem = originalItem.copy();
				waxedItem.set(ModDataComponents.WAXED, true);
				event.insertAfter(originalItem, waxedItem, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			}
		});
	}
}
*///?}
