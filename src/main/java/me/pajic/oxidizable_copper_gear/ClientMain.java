package me.pajic.oxidizable_copper_gear;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getModContainer(Main.MOD_ID).ifPresent(modContainer ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                        Main.withModNamespace("default_rp"),
                        modContainer,
                        Component.literal("Oxidizable Copper Gear Default Pack"),
                        ResourcePackActivationType.ALWAYS_ENABLED
                )
        );
    }
}
