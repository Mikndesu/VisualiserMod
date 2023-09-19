package com.github.mikn.visualiser.asm.mixin;

import com.github.mikn.visualiser.client.ShulkerBoxTooltip;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "getTooltipImage(Lnet/minecraft/world/item/ItemStack;)Ljava/util/Optional;", at = @At("RETURN"), cancellable = true)
    private void visualiser$getTooltipImage(ItemStack stack, CallbackInfoReturnable<Optional<ShulkerBoxTooltip>> cir) {
        if(stack.is(Items.SHULKER_BOX)) {
            NonNullList<ItemStack> nonNullList = NonNullList.create();
            // Temporarily add substitute items to the list.
            ItemStack itemStack = new ItemStack(Items.ACACIA_DOOR);
            itemStack.setCount(64);
            nonNullList.add(itemStack);
            var o = Optional.of(new ShulkerBoxTooltip(nonNullList, 1));
            cir.setReturnValue(o);
        }
    }

}
