package com.github.mikn.visualiser.client;

import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class ShulkerBoxTooltip implements TooltipComponent {
    private final NonNullList<ItemStack> items;
    private final ArrayList<Component> component;

    public ShulkerBoxTooltip(NonNullList<ItemStack> items, ArrayList<Component> component) {
        this.items = items;
        this.component = component;
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    public ArrayList<Component> getText() {
        return this.component;
    }
}
