package me.pajic.oxidizable_copper_gear;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectBooleanImmutablePair;
import it.unimi.dsi.fastutil.objects.ObjectBooleanPair;
import me.pajic.oxidizable_copper_gear.recipe.ItemAxingRecipe;
import me.pajic.oxidizable_copper_gear.recipe.ItemWaxingRecipe;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.LoggerFactory;

@Mod(Main.MOD_ID)
public class Main {
    public static final String MOD_ID = "oxidizable_copper_gear";
    private static final Logger LOGGER = LoggerFactory.getLogger("Oxidizable Copper Gear");
    private static final boolean DEBUG = !FMLLoader.getCurrent().isProduction();

    public static final DataComponentType<Integer> OXIDATION = DataComponentType.<Integer>builder().persistent(Codec.INT).build();
    public static final DataComponentType<Boolean> WAXED = DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build();

    public static final TagKey<Item> OXIDIZABLE = TagKey.create(
            Registries.ITEM,
            withModNamespace("oxidizable")
    );

    public static RecipeSerializer<ItemWaxingRecipe> ITEM_WAXING = new CustomRecipe.Serializer<>(ItemWaxingRecipe::new);
    public static RecipeSerializer<ItemAxingRecipe> ITEM_AXING = new CustomRecipe.Serializer<>(ItemAxingRecipe::new);

    public Main(IEventBus modEventBus) {
        modEventBus.addListener(this::registerData);
        modEventBus.addListener(this::registerDefaultDatapack);
        modEventBus.addListener(this::addCreative);
    }

    private void registerDefaultDatapack(AddPackFindersEvent event) {
        event.addPackFinders(
                withModNamespace("resourcepacks/default_dp"),
                PackType.SERVER_DATA,
                Component.literal("Oxidizable Copper Gear Default Pack"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP
        );
    }

    private void registerData(RegisterEvent event) {
        event.register(
                Registries.DATA_COMPONENT_TYPE,
                registry -> {
                    registry.register(withModNamespace("oxidation"), OXIDATION);
                    registry.register(withModNamespace("waxed"), WAXED);
                }
        );
        event.register(
                Registries.RECIPE_SERIALIZER,
                registry -> {
                    registry.register(ResourceLocation.withDefaultNamespace("crafting_special_item_waxing"), ITEM_WAXING);
                    registry.register(ResourceLocation.withDefaultNamespace("crafting_special_item_axing"), ITEM_AXING);
                }
        );
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.SEARCH) BuiltInRegistries.ITEM.getTagOrEmpty(OXIDIZABLE).forEach(item -> {
            ItemStack originalItem = new ItemStack(item);
            if (event.getParentEntries().contains(originalItem)) {
                debugLog("item " + originalItem.getItemName().getString());
                debugLog("tab " + event.getTab().getDisplayName().getString());
                for (int i = 3; i > 0; i--) {
                    debugLog("oxidation {}", i);
                    ItemStack oxidizedItem = new ItemStack(item);
                    oxidizedItem.set(OXIDATION, i);
                    ItemStack waxedOxidizedItem = oxidizedItem.copy();
                    waxedOxidizedItem.set(WAXED, true);
                    debugLog("{}", waxedOxidizedItem.getItemName().getString());
                    event.insertAfter(originalItem, waxedOxidizedItem, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    debugLog("{}", oxidizedItem.getItemName().getString());
                    event.insertAfter(originalItem, oxidizedItem, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
                ItemStack waxedItem = originalItem.copy();
                waxedItem.set(WAXED, true);
                event.insertAfter(originalItem, waxedItem, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        });
    }

    public static ObjectBooleanPair<ItemStack> tryOxidize(ItemStack stack, long worldTime, RandomSource random, boolean copy) {
        if (stack.is(OXIDIZABLE) && !stack.has(WAXED) && worldTime % 1200 == 0) {
            int oxidation = getItemOxidation(stack);
            if (oxidation < 3) {
                float chanceModifier = stack.isDamageableItem() ? Mth.clampedMap(
                        stack.getMaxDamage() - stack.getDamageValue(),
                        stack.getMaxDamage() * ((float) (2 - oxidation) / 4F),
                        stack.getMaxDamage() * ((float) (4 - oxidation) / 4F),
                        0.5F, 2
                ) : 1;
                if (random.nextFloat() < (32F / 1125) / chanceModifier) {
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
