package com.github.mikn.visualiser.config;

import com.github.mikn.visualiser.VisualiserMod;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = VisualiserMod.MODID)
public class VisualiserModConfig implements ConfigData {
    public enum OverlayStyleEnum {
        ON_TOP, ON_BELOW, INSIDE_TOOLTIP
    }
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public OverlayStyleEnum style = OverlayStyleEnum.ON_BELOW;
}