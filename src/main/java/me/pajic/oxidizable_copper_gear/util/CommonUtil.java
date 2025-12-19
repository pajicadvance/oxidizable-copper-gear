package me.pajic.oxidizable_copper_gear.util;

import it.unimi.dsi.fastutil.objects.ObjectBooleanImmutablePair;
import it.unimi.dsi.fastutil.objects.ObjectBooleanPair;
import me.pajic.oxidizable_copper_gear.OCG;
import me.pajic.oxidizable_copper_gear.registry.ModDataComponents;
import me.pajic.oxidizable_copper_gear.registry.ModTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class CommonUtil {

	public static ObjectBooleanPair<ItemStack> tryOxidize(ItemStack stack, long worldTime, RandomSource random, boolean copy) {
		if (stack.is(ModTags.OXIDIZABLE) && !stack.has(ModDataComponents.WAXED) && worldTime % 1200 == 0) {
			int oxidation = getItemOxidation(stack);
			if (oxidation < 3) {
				float chanceModifier = stack.isDamageableItem() ? Mth.clampedMap(
						stack.getMaxDamage() - stack.getDamageValue(),
						stack.getMaxDamage() * ((float) (2 - oxidation) / 4F),
						stack.getMaxDamage() * ((float) (4 - oxidation) / 4F),
						0.5F, 2
				) : 1;
				if (random.nextFloat() < (32F / 1125) / chanceModifier) {
					OCG.debugLog(
							"Ticking oxidation for item {}, chance modifier was {}",
							stack.getHoverName().getString(), chanceModifier
					);
					if (copy) {
						ItemStack updatedStack = stack.copy();
						incrementItemOxidation(updatedStack, 1);
						return new ObjectBooleanImmutablePair<>(updatedStack, true);
					} else {
						incrementItemOxidation(stack, 1);
					}
				}
			}
		}
		return new ObjectBooleanImmutablePair<>(stack, false);
	}

	public static int getItemOxidation(ItemStack stack) {
		return stack.getOrDefault(ModDataComponents.OXIDATION, 0);
	}

	public static void incrementItemOxidation(ItemStack stack, int increment) {
		int updatedOxidation = getItemOxidation(stack) + increment;
		if (updatedOxidation <= 0) stack.remove(ModDataComponents.OXIDATION);
		else stack.set(ModDataComponents.OXIDATION, Mth.clamp(updatedOxidation, 1, 3));
	}
}
