package com.github.mikn.visualiser.client;

import com.github.mikn.visualiser.VisualiserMod;
import com.github.mikn.visualiser.config.VisualiserModConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static java.lang.Math.floor;

public class ClientShulkerBoxTooltip implements ClientTooltipComponent {
    public static final ResourceLocation TEXTURE_FOR_OUTSIDE = new ResourceLocation(VisualiserMod.MODID, "textures/gui/container/overlay_outside_gui.png");
    private static final int MARGIN_X = 4;
    private static final int MARGIN_Y = 8;
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
        return 0;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        y = y + (VisualiserMod.HOLDER.position == VisualiserModConfig.OverlayPositionEnum.ON_BELOW ? 2 : -66);
        this.blit(guiGraphics, x - MARGIN_X, y, Texture.IMAGE);
        this.renderSlotItems(guiGraphics, font, x, y);
    }

    private void blit(GuiGraphics guiGraphics, int x, int y, ClientShulkerBoxTooltip.Texture texture) {
        guiGraphics.blit(TEXTURE_FOR_OUTSIDE, x, y, 0, (float)texture.x, texture.y, texture.w, texture.h, 256, 256);
    }

    private void renderSlotItems(GuiGraphics guiGraphics, Font font, int x, int y) {
        final int SLOT_SIZE = 18;
        final int MAX_AMOUNT_OF_ITEMS_TO_BE_RENDERED = 12;
        final int MAX_COLUMN = 6;
        int i = 0;
        for(ItemStack itemStack: this.items) {
            if(i >= MAX_AMOUNT_OF_ITEMS_TO_BE_RENDERED) {
                // break loop because rows more than two do not exist.
                break;
            }
            // this specifies the column of the slot to be rendered.
            int column = i % MAX_COLUMN;
            // this specifies the row of the slot to be rendered
            int row = (int) floor((double) i / MAX_COLUMN);
            guiGraphics.renderItem(itemStack, x + MARGIN_X + SLOT_SIZE * column, y + MARGIN_Y + SLOT_SIZE * row, 0);
            guiGraphics.renderItemDecorations(font, itemStack, x + MARGIN_X + SLOT_SIZE * column, y + MARGIN_Y + SLOT_SIZE * row);
            ++i;
        }
    }

    @Environment(value= EnvType.CLIENT)
    enum Texture {
        IMAGE(0, 0, 255, 255);

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
