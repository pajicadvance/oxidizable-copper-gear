package me.pajic.oxidizable_copper_gear;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;

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
