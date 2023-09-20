package com.github.mikn.visualiser.config;

import com.github.mikn.visualiser.VisualiserMod;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = VisualiserMod.MODID)
public class VisualiserModConfig implements ConfigData {
    public enum OverlayPositionEnum {
        ON_TOP, ON_BELOW
    }
    public enum OverlayStyleEnum {
        INSIDE_TOOLTIP, OUTSIDE_TOOLTIP
    }
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public OverlayPositionEnum position = OverlayPositionEnum.ON_BELOW;
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public OverlayStyleEnum style = OverlayStyleEnum.OUTSIDE_TOOLTIP;
}