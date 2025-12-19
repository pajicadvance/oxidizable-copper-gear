package me.pajic.oxidizable_copper_gear.recipe;

import me.pajic.oxidizable_copper_gear.registry.ModDataComponents;
import me.pajic.oxidizable_copper_gear.registry.ModRecipeSerializers;
import me.pajic.oxidizable_copper_gear.registry.ModTags;
import me.pajic.oxidizable_copper_gear.OCG;
import me.pajic.oxidizable_copper_gear.util.CommonUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemAxingRecipe extends CustomRecipe {

    public ItemAxingRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, @NotNull Level level) {
        if (input.size() != 2) {
            return false;
        } else {
            boolean hasDewaxableItem = false;
            boolean hasOxidizableItem = false;
            boolean hasAxe = false;

            for (int i = 0; i < input.size(); i++) {
                ItemStack itemStack = input.getItem(i);
                if (!itemStack.isEmpty()) {
                    if (itemStack.is(ItemTags.AXES) && !itemStack.has(ModDataComponents.WAXED)) {
                        if (hasAxe) return false;
                        hasAxe = true;
                    } else {
                        if (!itemStack.is(ModTags.OXIDIZABLE)) return false;
                        if (hasDewaxableItem || hasOxidizableItem) return false;
                        if (itemStack.has(ModDataComponents.WAXED)) hasDewaxableItem = true;
                        else hasOxidizableItem = true;
                    }
                }
            }

            OCG.debugLog(
                    "hasDewaxableItem {} hasOxidizableItem {} hasAxe {}",
                    hasDewaxableItem, hasOxidizableItem, hasAxe
            );
            return hasAxe && (hasDewaxableItem || hasOxidizableItem);
        }
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input, @NotNull HolderLookup.Provider registries) {
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack itemStack2 = input.getItem(i);
            OCG.debugLog(
                    "itemStack {} oxidizable {} waxed {}",
                    itemStack2.getHoverName().getString(),
                    itemStack2.is(ModTags.OXIDIZABLE),
                    itemStack2.has(ModDataComponents.WAXED)
            );
            if (itemStack2.is(ModTags.OXIDIZABLE)) {
                if (itemStack2.has(ModDataComponents.WAXED)) {
                    itemStack = itemStack2.copy();
                    itemStack.remove(ModDataComponents.WAXED);
					OCG.debugLog("unwaxing {}", itemStack.getHoverName().getString());
                } else if (CommonUtil.getItemOxidation(itemStack2) > 0) {
                    itemStack = itemStack2.copy();
					CommonUtil.incrementItemOxidation(itemStack, -1);
					OCG.debugLog("reducing oxidation on {}", itemStack.getHoverName().getString());
                }
            }
        }
		OCG.debugLog("returning {}", itemStack.getHoverName().getString());
        return itemStack;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> items = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            ItemStack itemStack = input.getItem(i);
            if (itemStack.is(ItemTags.AXES) && !itemStack.has(ModDataComponents.WAXED)) {
                ItemStack itemStack2 = itemStack.copy();
                itemStack2.setDamageValue(itemStack2.getDamageValue() + 1);
                items.set(i, itemStack2);
            }
        }
        return items;
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializers.ITEM_AXING;
    }
}
