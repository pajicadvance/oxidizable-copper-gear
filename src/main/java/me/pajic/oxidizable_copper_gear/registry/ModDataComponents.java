package me.pajic.oxidizable_copper_gear.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;

public class ModDataComponents {

	public static final DataComponentType<Integer> OXIDATION = DataComponentType.<Integer>builder().persistent(Codec.INT).build();

	public static final DataComponentType<Boolean> WAXED = DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build();
}
