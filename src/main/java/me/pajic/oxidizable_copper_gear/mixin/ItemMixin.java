package me.pajic.oxidizable_copper_gear.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.oxidizable_copper_gear.util.CommonUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin {

    @WrapMethod(method = "inventoryTick")
    private void oxidizePlayerCopperGear(ItemStack itemStack, ServerLevel level, Entity owner, EquipmentSlot slot, Operation<Void> original) {
        CommonUtil.tryOxidize(itemStack, level.getGameTime(), level.getRandom(), false);
        original.call(itemStack, level, owner, slot);
    }
}
