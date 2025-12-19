package me.pajic.oxidizable_copper_gear.platform.neoforge;

//? neoforge {

/*import me.pajic.oxidizable_copper_gear.OCG;
import me.pajic.oxidizable_copper_gear.platform.Platform;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(modid = OCG.MOD_ID, value = Dist.CLIENT)
public class NeoforgeClientEventSubscriber {

	@SubscribeEvent
	private static void initDefaultResourcePack(AddPackFindersEvent event) {
		event.addPackFinders(
				OCG.id(OCG.xplat().packPath(Platform.VersionedPackType.ASSETS)),
				PackType.CLIENT_RESOURCES,
				Component.literal("Oxidizable Copper Gear Default Pack"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
	}
}
*///?}
