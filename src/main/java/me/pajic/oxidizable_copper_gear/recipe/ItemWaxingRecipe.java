package me.pajic.oxidizable_copper_gear.recipe;

import me.pajic.oxidizable_copper_gear.data.CommonData;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemWaxingRecipe extends CustomRecipe {

    public ItemWaxingRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.size() != 2) {
            return false;
        } else {
            boolean hasWaxableItem = false;
            boolean hasHoneycomb = false;

            for (int i = 0; i < input.size(); i++) {
                ItemStack itemStack = input.getItem(i);
                if (!itemStack.isEmpty()) {
                    if (itemStack.is(Items.HONEYCOMB)) {
                        if (hasHoneycomb) return false;
                        hasHoneycomb = true;
                    } else {
                        if (!itemStack.is(CommonData.OXIDIZABLE) || itemStack.has(CommonData.WAXED)) return false;
                        if (hasWaxableItem) return false;
                        hasWaxableItem = true;
                    }
                }
            }

            return hasWaxableItem && hasHoneycomb;
        }
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack itemStack2 = input.getItem(i);
            if (itemStack2.is(CommonData.OXIDIZABLE)) {
                itemStack = itemStack2.copy();
                itemStack.set(CommonData.WAXED, true);
            }
        }
        return itemStack;
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return CommonData.ITEM_WAXING;
    }
}
