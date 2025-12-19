package me.pajic.oxidizable_copper_gear.util;

import me.pajic.oxidizable_copper_gear.OCG;
import me.pajic.oxidizable_copper_gear.registry.ModTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;

public class ClientUtil {

	public static ResourceKey<EquipmentAsset> getAssetId(ItemStack stack, Equippable equippable, ResourceKey<EquipmentAsset> original) {
		if (stack.is(ModTags.OXIDIZABLE)) {
			String state = switch (CommonUtil.getItemOxidation(stack)) {
				case 1 -> "exposed_";
				case 2 -> "weathered_";
				case 3 -> "oxidized_";
				default -> "";
			};
			if (!state.isEmpty()) return ResourceKey.create(
					EquipmentAssets.ROOT_ID,
					OCG.id(state + equippable.assetId().get().identifier().getPath())
			);
		}
		return original;
	}
}
