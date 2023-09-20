package com.github.mikn.visualiser.asm.mixin;

import com.github.mikn.visualiser.client.ClientShulkerBoxTooltip;
import com.github.mikn.visualiser.client.ShulkerBoxTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientTooltipComponent.class)
public interface ClientTooltipComponentMixin {

    @Inject(method= "create(Lnet/minecraft/world/inventory/tooltip/TooltipComponent;)Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipComponent;", at=@At("HEAD"), cancellable = true)
    private static void visualiser$create(TooltipComponent visualTooltipComponent, CallbackInfoReturnable<ClientTooltipComponent> cir) {
        if(visualTooltipComponent instanceof ShulkerBoxTooltip) {
            cir.setReturnValue(new ClientShulkerBoxTooltip((ShulkerBoxTooltip)visualTooltipComponent));
        }
    }
}
