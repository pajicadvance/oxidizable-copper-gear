package me.pajic.oxidizable_copper_gear.registry;

import me.pajic.oxidizable_copper_gear.OCG;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {

	public static final TagKey<Item> OXIDIZABLE = TagKey.create(Registries.ITEM, OCG.id("oxidizable"));
}
