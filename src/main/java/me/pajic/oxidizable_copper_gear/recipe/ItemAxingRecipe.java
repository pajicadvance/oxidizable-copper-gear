package me.pajic.oxidizable_copper_gear.recipe;

import me.pajic.oxidizable_copper_gear.registry.ModDataComponents;
import me.pajic.oxidizable_copper_gear.registry.ModRecipeSerializers;
import me.pajic.oxidizable_copper_gear.registry.ModTags;
import me.pajic.oxidizable_copper_gear.OCG;
import me.pajic.oxidizable_copper_gear.util.CommonUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

//? >=26.1 {
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
//?} else {
/*import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.CraftingBookCategory;
*///?}

public class ItemAxingRecipe extends CustomRecipe {

    //? >=26.1 {
    private static final ItemAxingRecipe INSTANCE = new ItemAxingRecipe();
    public static final MapCodec<ItemAxingRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemAxingRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    //?} else {
    /*public ItemAxingRecipe(CraftingBookCategory category) {
        super(category);
    }
    *///?}

    @Override
    public boolean matches(@NotNull CraftingInput input, @NotNull Level level) {
        return findTarget(input) >= 0;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput input/*? <26.1 {*//*, @NotNull HolderLookup.Provider registries*//*?}*/) {
        int target = findTarget(input);
        if (target < 0) return ItemStack.EMPTY;
        ItemStack itemStack = input.getItem(target).copy();
        if (itemStack.has(ModDataComponents.WAXED)) {
            itemStack.remove(ModDataComponents.WAXED);
            OCG.debugLog("unwaxing {}", itemStack.getHoverName().getString());
        } else {
            CommonUtil.incrementItemOxidation(itemStack, -1);
            OCG.debugLog("reducing oxidation on {}", itemStack.getHoverName().getString());
        }
        return itemStack;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(@NotNull CraftingInput input) {
        NonNullList<ItemStack> items = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        int target = findTarget(input);
        if (target < 0) return items;
        for (int i = 0; i < input.size(); i++) {
            ItemStack axe = input.getItem(i);
            if (i == target || axe.isEmpty()) continue;
            ItemStack remainingAxe = axe.copy();
            if (remainingAxe.isDamageableItem()) {
                int damage = remainingAxe.getDamageValue() + 1;
                if (damage >= remainingAxe.getMaxDamage()) remainingAxe = ItemStack.EMPTY;
                else remainingAxe.setDamageValue(damage);
            }
            items.set(i, remainingAxe);
        }
        return items;
    }

    private static int findTarget(CraftingInput input) {
        if (input.ingredientCount() != 2) return -1;
        int first = -1;
        int second = -1;
        for (int i = 0; i < input.size(); i++) {
            if (input.getItem(i).isEmpty()) continue;
            if (first < 0) first = i;
            else second = i;
        }
        if (isTarget(input.getItem(first)) && isAxe(input.getItem(second))) return first;
        if (isTarget(input.getItem(second)) && isAxe(input.getItem(first))) return second;
        return -1;
    }

    private static boolean isTarget(ItemStack stack) {
        return stack.is(ModTags.OXIDIZABLE) && (stack.has(ModDataComponents.WAXED) || CommonUtil.getItemOxidation(stack) > 0);
    }

    private static boolean isAxe(ItemStack stack) {
        return stack.is(ItemTags.AXES) && !stack.has(ModDataComponents.WAXED);
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializers.ITEM_AXING;
    }
}
