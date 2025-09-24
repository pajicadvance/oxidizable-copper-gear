package me.pajic.oxidizable_copper_gear.recipe;

import me.pajic.oxidizable_copper_gear.Main;
import me.pajic.oxidizable_copper_gear.data.CommonData;
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
    public boolean matches(CraftingInput input, Level level) {
        if (input.size() != 2) {
            return false;
        } else {
            boolean hasDewaxableItem = false;
            boolean hasOxidizableItem = false;
            boolean hasAxe = false;

            for (int i = 0; i < input.size(); i++) {
                ItemStack itemStack = input.getItem(i);
                if (!itemStack.isEmpty()) {
                    if (itemStack.is(ItemTags.AXES) && !itemStack.has(CommonData.WAXED)) {
                        if (hasAxe) return false;
                        hasAxe = true;
                    } else {
                        if (!itemStack.is(CommonData.OXIDIZABLE)) return false;
                        if (hasDewaxableItem || hasOxidizableItem) return false;
                        if (itemStack.has(CommonData.WAXED)) hasDewaxableItem = true;
                        else hasOxidizableItem = true;
                    }
                }
            }

            Main.debugLog(
                    "hasDewaxableItem {} hasOxidizableItem {} hasAxe {}",
                    hasDewaxableItem, hasOxidizableItem, hasAxe
            );
            return hasAxe && (hasDewaxableItem || hasOxidizableItem);
        }
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack itemStack2 = input.getItem(i);
            Main.debugLog(
                    "itemStack {} oxidizable {} waxed {}",
                    itemStack2.getHoverName().getString(),
                    itemStack2.is(CommonData.OXIDIZABLE),
                    itemStack2.has(CommonData.WAXED)
            );
            if (itemStack2.is(CommonData.OXIDIZABLE)) {
                if (itemStack2.has(CommonData.WAXED)) {
                    itemStack = itemStack2.copy();
                    itemStack.remove(CommonData.WAXED);
                    Main.debugLog("unwaxing {}", itemStack.getHoverName().getString());
                } else if (CommonData.getItemOxidation(itemStack2) > 0) {
                    itemStack = itemStack2.copy();
                    CommonData.incrementItemOxidation(itemStack, -1);
                    Main.debugLog("reducing oxidation on {}", itemStack.getHoverName().getString());
                }
            }
        }
        Main.debugLog("returning {}", itemStack.getHoverName().getString());
        return itemStack;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> items = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            ItemStack itemStack = input.getItem(i);
            if (itemStack.is(ItemTags.AXES) && !itemStack.has(CommonData.WAXED)) {
                ItemStack itemStack2 = itemStack.copy();
                itemStack2.setDamageValue(itemStack2.getDamageValue() + 1);
                items.set(i, itemStack2);
            }
        }
        return items;
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return CommonData.ITEM_AXING;
    }
}
