package me.pajic.oxidizable_copper_gear.platform.fabric;

//? fabric {

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import me.pajic.oxidizable_copper_gear.OCG;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

//? <26.1 {
/*import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
*///?} else {
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
//?}

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		FabricLoader.getInstance().getModContainer(OCG.MOD_ID).ifPresent(container ->
				//~ if <26.1 'ResourceLoader.registerBuiltinPack' -> 'ResourceManagerHelper.registerBuiltinResourcePack'
				ResourceLoader.registerBuiltinPack(
						OCG.id(OCG.DEFAULT_RESOURCE_PACK),
						container,
						Component.literal("Oxidizable Copper Gear Default Pack"),
						//~ if <26.1 'PackActivationType' -> 'ResourcePackActivationType'
						PackActivationType.ALWAYS_ENABLED
				)
		);
	}
}
//?}
