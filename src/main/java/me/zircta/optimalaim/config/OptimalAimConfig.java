package me.zircta.optimalaim.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Color;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

public class OptimalAimConfig extends Config {

    @Slider(
            name = "Distance",
            description = "Allows you to select the distance of the box.",
            min = 1.0f,
            max = 32.0f,
            subcategory = "Options"
    )
    public float distance = 10.0f;

    @Slider(
            name = "Radius",
            description = "Allows you to select the size of the box.",
            min = 0.05f,
            max = 0.3f,
            subcategory = "Options"
    )
    public float radius = 0.2f;

    @Color(
            name = "Cube color",
            description = "Allows you to select color of the box.",
            subcategory = "Colors"
    )
    public OneColor cubeColor = new OneColor(255, 0, 0);

    @Color(
            name = "Outline color",
            description = "Allows you to select color of the outline.",
            subcategory = "Colors"
    )
    public OneColor outlineColor = new OneColor(0, 255, 0);

    public OptimalAimConfig() {
        super(new Mod("Optimal Aim", ModType.PVP), "optimal-aim.json");
        this.initialize();
    }
}
