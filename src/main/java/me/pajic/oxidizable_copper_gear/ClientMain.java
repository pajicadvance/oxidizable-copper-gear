package me.pajic.oxidizable_copper_gear;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@Mod(value = Main.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
public class ClientMain {

    public ClientMain(IEventBus modEventBus) {
        modEventBus.addListener(this::registerDefaultResourcePack);
    }

    private void registerDefaultResourcePack(AddPackFindersEvent event) {
        event.addPackFinders(
                Main.withModNamespace("resourcepacks/default_rp"),
                PackType.CLIENT_RESOURCES,
                Component.literal("Oxidizable Copper Gear Default Pack"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
    }

    public static ResourceKey<EquipmentAsset> getAssetId(ItemStack stack, Equippable equippable, ResourceKey<EquipmentAsset> original) {
        if (stack.is(Main.OXIDIZABLE)) {
            String state = switch (Main.getItemOxidation(stack)) {
                case 1 -> "exposed_";
                case 2 -> "weathered_";
                case 3 -> "oxidized_";
                default -> "";
            };
            if (!state.isEmpty()) return ResourceKey.create(
                    EquipmentAssets.ROOT_ID,
                    Main.withModNamespace(state + equippable.assetId().get().location().getPath())
            );
        }
        return original;
    }
}
