package me.pajic.oxidizable_copper_gear.platform.fabric;

//? fabric {

import me.pajic.oxidizable_copper_gear.OCG;
import me.pajic.oxidizable_copper_gear.platform.Platform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

@SuppressWarnings("unused")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		initDefaultResourcePack();
	}

	private void initDefaultResourcePack() {
		FabricLoader.getInstance().getModContainer(OCG.MOD_ID).ifPresent(modContainer ->
				ResourceManagerHelper.registerBuiltinResourcePack(
						OCG.id(OCG.xplat().packPath(Platform.VersionedPackType.ASSETS)),
						modContainer,
						Component.literal("Oxidizable Copper Gear Default Pack"),
						ResourcePackActivationType.ALWAYS_ENABLED
				)
		);
	}
}
//?}
