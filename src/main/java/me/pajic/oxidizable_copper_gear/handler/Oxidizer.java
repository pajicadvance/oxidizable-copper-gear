package me.pajic.oxidizable_copper_gear.handler;

import it.unimi.dsi.fastutil.objects.ObjectBooleanImmutablePair;
import it.unimi.dsi.fastutil.objects.ObjectBooleanPair;
import me.pajic.oxidizable_copper_gear.Main;
import me.pajic.oxidizable_copper_gear.data.CommonData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class Oxidizer {

    // magic numbers:
    // 0.00083333F: 1 / 1200, attempt to oxidize every 1200 ticks/60 seconds on average
    // 0.02844444F: vanilla chance to oxidize copper related blocks on random tick divided by two
    public static ObjectBooleanPair<ItemStack> tryOxidize(ItemStack stack, RandomSource random, boolean copy) {
        if (stack.is(CommonData.OXIDIZABLE) && !stack.has(CommonData.WAXED) && random.nextFloat() < 0.00083333F) {
            int oxidation = stack.getOrDefault(CommonData.OXIDATION, 0);
            if (oxidation < 3) {
                float chanceModifier = stack.isDamageableItem() ? Mth.clampedMap(
                        stack.getMaxDamage() - stack.getDamageValue(),
                        stack.getMaxDamage() * ((float) (3 - oxidation) / 4F),
                        stack.getMaxDamage() * ((float) (4 - oxidation) / 4F),
                        0.5F, 1
                ) : 1;
                if (random.nextFloat() < 0.02844444F / chanceModifier) {
                    Main.debugLog(
                            "Ticking oxidation for item {}, chance modifier was {}",
                            stack.getHoverName().getString(), chanceModifier
                    );
                    if (copy) {
                        ItemStack updatedStack = stack.copy();
                        updatedStack.set(CommonData.OXIDATION, oxidation + 1);
                        return new ObjectBooleanImmutablePair<>(updatedStack, true);
                    } else {
                        stack.set(CommonData.OXIDATION, oxidation + 1);
                    }
                }
            }
        }
        return new ObjectBooleanImmutablePair<>(stack, false);
    }
}
