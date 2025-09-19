package me.pajic.oxidizable_copper_gear.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.pajic.oxidizable_copper_gear.data.CommonData;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder {

    @ModifyReturnValue(
            method = "getItemName",
            at = @At("RETURN")
    )
    private Component addOxidationPrefix(Component original) {
        Component name = switch (getOrDefault(CommonData.OXIDATION, 0)) {
            case 1 -> Component.translatable("text.oxidizable_copper_gear.exposed_prefix").append(original);
            case 2 -> Component.translatable("text.oxidizable_copper_gear.weathered_prefix").append(original);
            case 3 -> Component.translatable("text.oxidizable_copper_gear.oxidized_prefix").append(original);
            default -> original;
        };
        return has(CommonData.WAXED) ? Component.translatable("text.oxidizable_copper_gear.waxed_prefix").append(name) : name;
    }
}
