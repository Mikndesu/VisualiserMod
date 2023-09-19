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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "getTooltipImage(Lnet/minecraft/world/item/ItemStack;)Ljava/util/Optional;", at = @At("RETURN"), cancellable = true)
    private void visualiser$getTooltipImage(ItemStack stack, CallbackInfoReturnable<Optional<ShulkerBoxTooltip>> cir) {
        if(stack.is(Items.SHULKER_BOX)) {
            NonNullList<ItemStack> nonNullList = NonNullList.create();
            ArrayList<ItemStack> list = new ArrayList<>(Arrays.asList(new ItemStack(Items.ACACIA_DOOR), new ItemStack(Items.END_STONE), new ItemStack(Items.DIAMOND), new ItemStack(Items.EMERALD), new ItemStack(Items.DIRT), new ItemStack(Items.STONE), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.ANCIENT_DEBRIS), new ItemStack(Items.DEEPSLATE), new ItemStack(Items.APPLE), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.END_STONE_BRICK_STAIRS), new ItemStack(Items.COAL_ORE)));
            // Temporarily add substitute items to the list.
            list.forEach(elm -> {
                elm.setCount(64);
                nonNullList.add(elm);
            });
            var o = Optional.of(new ShulkerBoxTooltip(nonNullList, 1));
            cir.setReturnValue(o);
        }
    }

}
