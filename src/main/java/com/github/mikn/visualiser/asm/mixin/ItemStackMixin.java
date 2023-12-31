package com.github.mikn.visualiser.asm.mixin;

import com.github.mikn.visualiser.IItemStackMixin;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin implements IItemStackMixin {
    @Inject(method = "getTooltipLines(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/TooltipFlag;)Ljava/util/List;", at = @At("HEAD"), cancellable = true)
    private void visualiser$getTooltipLines(@Nullable Player player, TooltipFlag isAdvanced, CallbackInfoReturnable<List<Component>> cir) {
        ItemStack itemStack = (ItemStack) (Object) this;
        if(this.visualiser$isShulkerBox(itemStack) && this.visualiser$hasItemsInside(itemStack)) {
            ArrayList<Component> list = Lists.newArrayList();
            MutableComponent mutableComponent = Component.empty().append(itemStack.getHoverName()).withStyle(itemStack.getRarity().color);
            if (itemStack.hasCustomHoverName()) {
                mutableComponent.withStyle(ChatFormatting.ITALIC);
            }
            list.add(mutableComponent);
            cir.setReturnValue(list);
        }
    }

    @Unique
    public boolean visualiser$hasItemsInside(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        if(compoundTag == null) {
            return false;
        }
        // we can't get whether ShulkerBox has items inside by ListTag.isEmpty()
        return compoundTag.getCompound("BlockEntityTag").getList("Items", 10).size() != 0;
    }

    @Unique
    public boolean visualiser$isShulkerBox(ItemStack itemStack) {
        if(itemStack.getItem() instanceof BlockItem blockItem) {
            return blockItem.getBlock().defaultBlockState().is(BlockTags.SHULKER_BOXES);
        }
        return false;
    }
}
