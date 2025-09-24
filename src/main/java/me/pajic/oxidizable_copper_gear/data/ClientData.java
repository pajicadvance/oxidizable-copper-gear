package me.pajic.oxidizable_copper_gear.data;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class ClientData {

    private static final ResourceKey<EquipmentAsset> EXPOSED_COPPER = EquipmentAssets.createId("exposed_copper");
    private static final ResourceKey<EquipmentAsset> WEATHERED_COPPER = EquipmentAssets.createId("weathered_copper");
    private static final ResourceKey<EquipmentAsset> OXIDIZED_COPPER = EquipmentAssets.createId("oxidized_copper");

    public static ResourceKey<EquipmentAsset> getAssetId(ItemStack stack, ResourceKey<EquipmentAsset> original) {
        if (stack.is(CommonData.OXIDIZABLE)) {
            return switch (CommonData.getItemOxidation(stack)) {
                case 1 -> EXPOSED_COPPER;
                case 2 -> WEATHERED_COPPER;
                case 3 -> OXIDIZED_COPPER;
                default -> original;
            };
        }
        return original;
    }
}
