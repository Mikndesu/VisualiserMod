package com.github.mikn.visualiser.asm.mixin;

import com.github.mikn.visualiser.client.ClientShulkerBoxTooltip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Debug(export = true)
@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {

    @Shadow
    protected abstract void renderTooltipInternal(Font font, List<ClientTooltipComponent> components, int mouseX, int mouseY, ClientTooltipPositioner tooltipPositioner);

    @Redirect(method = "renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltipInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V"))
    private void visualiser$renderTooltip(GuiGraphics instance, Font font, List<ClientTooltipComponent> components, int mouseX, int mouseY, ClientTooltipPositioner tooltipPositioner, Font __, List<Component> tooltipLines, Optional<TooltipComponent> visualTooltipComponent, int ___, int ____) {
        List<ClientTooltipComponent> list;
        if(visualTooltipComponent.isPresent() && visualTooltipComponent.get() instanceof ClientShulkerBoxTooltip) {
            list = List.of(ClientTooltipComponent.create(visualTooltipComponent.get()));
        } else {
            list = tooltipLines.stream().map(Component::getVisualOrderText).map(ClientTooltipComponent::create).collect(Collectors.toList());
            visualTooltipComponent.ifPresent(tooltipComponent -> list.add(1, ClientTooltipComponent.create(tooltipComponent)));
        }
        this.renderTooltipInternal(font, list, mouseX, mouseY, DefaultTooltipPositioner.INSTANCE);
    }

    @Redirect(method = "renderTooltipInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawManaged(Ljava/lang/Runnable;)V"))
    private void visualiser$renderTooltipInternal$drawManaged(GuiGraphics instance, Runnable runnable, Font font, List<ClientTooltipComponent> components, int mouseX, int mouseY, ClientTooltipPositioner tooltipPositioner) {
        if(components.stream().noneMatch(i -> i instanceof ClientShulkerBoxTooltip)) {
            runnable.run();
        }
    }

    @Redirect(method = "renderTooltipInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipComponent;renderText(Lnet/minecraft/client/gui/Font;IILorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)V"))
    private void visualiser$renderTooltipInternal$renderText(ClientTooltipComponent instance, Font font, int mouseX, int mouseY, Matrix4f matrix, MultiBufferSource.BufferSource bufferSource, Font __, List<ClientTooltipComponent> components) {
        if(components.stream().noneMatch(i -> i instanceof ClientShulkerBoxTooltip)) {
            instance.renderText(font, mouseX, mouseY, matrix, bufferSource);
        }
    }

}
