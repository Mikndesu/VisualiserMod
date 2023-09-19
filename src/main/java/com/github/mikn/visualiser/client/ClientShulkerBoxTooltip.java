package com.github.mikn.visualiser.client;

import com.github.mikn.visualiser.VisualiserMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ClientShulkerBoxTooltip implements ClientTooltipComponent {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(VisualiserMod.MODID, "textures/gui/container/overlay_gui.png");
    private static final int MARGIN_X = 4;
    private final NonNullList<ItemStack> items;
    private final int weight;

    public ClientShulkerBoxTooltip(ShulkerBoxTooltip shulkerBoxTooltip) {
        this.items = shulkerBoxTooltip.getItems();
        this.weight = shulkerBoxTooltip.getWeight();
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth(Font font) {
        return this.gridSizeX() * 18 + 2;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        this.blit(guiGraphics, x - MARGIN_X, y, Texture.IMAGE);
        this.renderSlotItems(guiGraphics, font, x, y);
    }

    private void blit(GuiGraphics guiGraphics, int x, int y, ClientShulkerBoxTooltip.Texture texture) {
        guiGraphics.blit(TEXTURE_LOCATION, x, y, 0, (float)texture.x, texture.y, texture.w, texture.h, 256, 256);
    }

    private void renderSlotItems(GuiGraphics guiGraphics, Font font, int x, int y) {
        ItemStack itemStack = this.items.get(0);
        guiGraphics.renderItem(itemStack, x + 4, y + 18, 0);
        guiGraphics.renderItemDecorations(font, itemStack, x + 4, y + 18);
    }

    private int gridSizeX() {
        return Math.max(2, (int)Math.ceil(Math.sqrt(this.items.size() + 1)));
    }

    private int gridSizeY() {
        return (int)Math.ceil((this.items.size() + 1) / (double)this.gridSizeX());
    }

    @Environment(value= EnvType.CLIENT)
    enum Texture {
        IMAGE(0, 0, 255, 255),
        SLOT1(8, 11, 23, 26);

        public final int x;
        public final int y;
        public final int w;
        public final int h;

        Texture(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }
}
