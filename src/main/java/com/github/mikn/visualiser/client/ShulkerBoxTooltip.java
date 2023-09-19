package com.github.mikn.visualiser.client;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class ShulkerBoxTooltip implements TooltipComponent {
    private final NonNullList<ItemStack> items;
    private final int weight;

    public ShulkerBoxTooltip(NonNullList<ItemStack> items, int weight) {
        this.items = items;
        this.weight = weight;
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    public int getWeight() {
        return this.weight;
    }
}
