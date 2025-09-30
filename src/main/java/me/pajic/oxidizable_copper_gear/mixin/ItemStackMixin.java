package me.pajic.oxidizable_copper_gear.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.pajic.oxidizable_copper_gear.Main;
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
        Component name = switch (Main.getItemOxidation((ItemStack) (Object) this)) {
            case 1 -> Component.translatable("text.oxidizable_copper_gear.exposed", original);
            case 2 -> Component.translatable("text.oxidizable_copper_gear.weathered", original);
            case 3 -> Component.translatable("text.oxidizable_copper_gear.oxidized", original);
            default -> original;
        };
        return has(Main.WAXED) ? Component.translatable("text.oxidizable_copper_gear.waxed", name) : name;
    }
}
