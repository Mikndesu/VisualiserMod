package com.github.mikn.visualiser;

import com.github.mikn.visualiser.config.VisualiserModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;

public class VisualiserMod implements ModInitializer {

    public static final String MODID = "visualiser";
    public static final Logger LOGGER = LogManager.getLogger("Visualiser/Main");
    public static final VisualiserModConfig HOLDER;

    @Override
    public void onInitialize() {
    }

    static {
        HOLDER = AutoConfig.register(VisualiserModConfig.class, JanksonConfigSerializer::new).getConfig();
    }
}