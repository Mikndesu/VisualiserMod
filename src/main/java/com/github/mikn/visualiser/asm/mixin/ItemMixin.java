package com.github.mikn.visualiser.asm.mixin;

import com.github.mikn.visualiser.IItemStackMixin;
import com.github.mikn.visualiser.client.ShulkerBoxTooltip;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "getTooltipImage(Lnet/minecraft/world/item/ItemStack;)Ljava/util/Optional;", at = @At("RETURN"), cancellable = true)
    private void visualiser$getTooltipImage(ItemStack stack, CallbackInfoReturnable<Optional<ShulkerBoxTooltip>> cir) {
        if(((IItemStackMixin)(Object)stack).visualiser$isShulkerBox(stack) && ((IItemStackMixin)(Object)stack).visualiser$hasItemsInside(stack)) {
            NonNullList<ItemStack> nonNullList = NonNullList.create();
            this.visualiser$getContents(stack).forEach(nonNullList::add);
            ArrayList<Component> list = Lists.newArrayList();
            MutableComponent mutableComponent = Component.empty().append(stack.getHoverName()).withStyle(stack.getRarity().color);
            if (stack.hasCustomHoverName()) {
                mutableComponent.withStyle(ChatFormatting.ITALIC);
            }
            list.add(mutableComponent);
            var o = Optional.of(new ShulkerBoxTooltip(nonNullList, list));
            cir.setReturnValue(o);
        }
    }

    @Unique
    private Stream<ItemStack> visualiser$getContents(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag == null) {
            return Stream.empty();
        }
        ListTag listTag = compoundTag.getCompound("BlockEntityTag").getList("Items", 10);
        return listTag.stream().map(CompoundTag.class::cast).map(ItemStack::of);
    }

}
