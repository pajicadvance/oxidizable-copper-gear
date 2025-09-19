package me.pajic.oxidizable_copper_gear;

import me.pajic.oxidizable_copper_gear.data.CommonData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {

    public static final String MOD_ID = "oxidizable_copper_gear";
    private static final Logger LOGGER = LoggerFactory.getLogger("Oxidizable Copper Gear");
    private static final boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();

    @Override
    public void onInitialize() {
        CommonData.init();
    }

    public static ResourceLocation withModNamespace(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void debugLog(String message, Object ... args) {
        if (DEBUG) LOGGER.info(message, args);
    }
}
