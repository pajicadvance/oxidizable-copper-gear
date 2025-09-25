package me.pajic.oxidizable_copper_gear;

import me.pajic.oxidizable_copper_gear.data.CommonData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {

    public static final String MOD_ID = "oxidizable_copper_gear";
    private static final Logger LOGGER = LoggerFactory.getLogger("Oxidizable Copper Gear");
    private static final boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();

    @Override
    public void onInitialize() {
        CommonData.init();

        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer ->
            ResourceManagerHelper.registerBuiltinResourcePack(
                    withModNamespace("default_dp"),
                    modContainer,
                    Component.literal("Oxidizable Copper Gear Default Pack"),
                    ResourcePackActivationType.DEFAULT_ENABLED
            )
        );

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((tabs, entries) ->
                BuiltInRegistries.ITEM.getTagOrEmpty(CommonData.OXIDIZABLE).forEach(item -> {
                    ItemStack originalItem = new ItemStack(item);
                    if (tabs.contains(originalItem)) {
                        for (int i = 3; i > 0; i--) {
                            ItemStack oxidizedItem = new ItemStack(item);
                            oxidizedItem.set(CommonData.OXIDATION, i);
                            ItemStack waxedOxidizedItem = oxidizedItem.copy();
                            waxedOxidizedItem.set(CommonData.WAXED, true);
                            entries.addAfter(originalItem, waxedOxidizedItem);
                            entries.addAfter(originalItem, oxidizedItem);
                        }
                        ItemStack waxedItem = originalItem.copy();
                        waxedItem.set(CommonData.WAXED, true);
                        entries.addAfter(originalItem, waxedItem);
                    }
                })
        );
    }

    public static ResourceLocation withModNamespace(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void debugLog(String message, Object ... args) {
        if (DEBUG) LOGGER.info(message, args);
    }
}
