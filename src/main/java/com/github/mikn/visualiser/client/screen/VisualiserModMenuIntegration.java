package com.github.mikn.visualiser.client.screen;

import com.github.mikn.visualiser.config.VisualiserModConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class VisualiserModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(VisualiserModConfig.class, parent).get();
    }
}
