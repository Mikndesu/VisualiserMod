package com.github.mikn.visualiser;

import net.minecraft.world.item.ItemStack;

public interface IItemStackMixin {
    boolean visualiser$hasItemsInside(ItemStack itemStack);
    boolean visualiser$isShulkerBox(ItemStack itemStack);
}
