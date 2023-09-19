package com.github.mikn.visualiser.asm.mixin;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "getTooltipLines(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;", at = @At("HEAD"), cancellable = true)
    private void visualiser$getTooltipLines(@Nullable Player player, TooltipFlag isAdvanced, CallbackInfoReturnable<List<Component>> cir) {
        ItemStack itemStack = (ItemStack) (Object) this;
        if(itemStack.is(Items.SHULKER_BOX)) {
            ArrayList<Component> list = Lists.newArrayList();
            MutableComponent mutableComponent = Component.empty().append(itemStack.getHoverName()).withStyle(itemStack.getRarity().color);
            if (itemStack.hasCustomHoverName()) {
                mutableComponent.withStyle(ChatFormatting.ITALIC);
            }
            list.add(mutableComponent);
            cir.setReturnValue(list);
        }
    }
}
