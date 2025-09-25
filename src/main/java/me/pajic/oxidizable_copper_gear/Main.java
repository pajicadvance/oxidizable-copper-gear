package me.pajic.oxidizable_copper_gear;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectBooleanImmutablePair;
import it.unimi.dsi.fastutil.objects.ObjectBooleanPair;
import me.pajic.oxidizable_copper_gear.recipe.ItemAxingRecipe;
import me.pajic.oxidizable_copper_gear.recipe.ItemWaxingRecipe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {

    public static final String MOD_ID = "oxidizable_copper_gear";
    private static final Logger LOGGER = LoggerFactory.getLogger("Oxidizable Copper Gear");
    private static final boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();

    public static final DataComponentType<Integer> OXIDATION = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            withModNamespace("oxidation"),
            DataComponentType.<Integer>builder().persistent(Codec.INT).build()
    );
    public static final DataComponentType<Boolean> WAXED = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            withModNamespace("waxed"),
            DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build()
    );

    public static final TagKey<Item> OXIDIZABLE = TagKey.create(
            Registries.ITEM,
            withModNamespace("oxidizable")
    );

    public static RecipeSerializer<ItemWaxingRecipe> ITEM_WAXING = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            "crafting_special_item_waxing",
            new CustomRecipe.Serializer<>(ItemWaxingRecipe::new)
    );
    public static RecipeSerializer<ItemAxingRecipe> ITEM_AXING = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            "crafting_special_item_axing",
            new CustomRecipe.Serializer<>(ItemAxingRecipe::new)
    );

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer ->
            ResourceManagerHelper.registerBuiltinResourcePack(
                    withModNamespace("default_dp"),
                    modContainer,
                    Component.literal("Oxidizable Copper Gear Default Pack"),
                    ResourcePackActivationType.DEFAULT_ENABLED
            )
        );

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((tabs, entries) ->
                BuiltInRegistries.ITEM.getTagOrEmpty(OXIDIZABLE).forEach(item -> {
                    ItemStack originalItem = new ItemStack(item);
                    if (tabs.contains(originalItem)) {
                        for (int i = 3; i > 0; i--) {
                            ItemStack oxidizedItem = new ItemStack(item);
                            oxidizedItem.set(OXIDATION, i);
                            ItemStack waxedOxidizedItem = oxidizedItem.copy();
                            waxedOxidizedItem.set(WAXED, true);
                            entries.addAfter(originalItem, waxedOxidizedItem);
                            entries.addAfter(originalItem, oxidizedItem);
                        }
                        ItemStack waxedItem = originalItem.copy();
                        waxedItem.set(WAXED, true);
                        entries.addAfter(originalItem, waxedItem);
                    }
                })
        );
    }

    // magic numbers:
    // 0.00083333F: 1 / 1200, attempt to oxidize every 1200 ticks/60 seconds on average
    // 0.02844444F: vanilla chance to oxidize copper related blocks on random tick divided by two
    public static ObjectBooleanPair<ItemStack> tryOxidize(ItemStack stack, RandomSource random, boolean copy) {
        if (stack.is(OXIDIZABLE) && !stack.has(WAXED) && random.nextFloat() < 0.00083333F) {
            int oxidation = getItemOxidation(stack);
            if (oxidation < 3) {
                float chanceModifier = stack.isDamageableItem() ? Mth.clampedMap(
                        stack.getMaxDamage() - stack.getDamageValue(),
                        stack.getMaxDamage() * ((float) (3 - oxidation) / 4F),
                        stack.getMaxDamage() * ((float) (4 - oxidation) / 4F),
                        0.5F, 1
                ) : 1;
                if (random.nextFloat() < 0.02844444F / chanceModifier) {
                    debugLog(
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
        return stack.getOrDefault(OXIDATION, 0);
    }

    public static void incrementItemOxidation(ItemStack stack, int increment) {
        int updatedOxidation = getItemOxidation(stack) + increment;
        if (updatedOxidation <= 0) stack.remove(OXIDATION);
        else stack.set(OXIDATION, Mth.clamp(updatedOxidation, 1, 3));
    }

    public static ResourceLocation withModNamespace(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void debugLog(String message, Object ... args) {
        if (DEBUG) LOGGER.info(message, args);
    }
}
