package net.exodiusmc.asteroids.common;

import javafx.scene.paint.Color;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 5/27/2017
 */
public enum ShipType {

    DEFAULT("X-Wing", Color.RED),
    AIR_WING("Air Wing", Color.LIGHTBLUE),
    BLUE_BIRD("Blue Bird", Color.LIGHTSEAGREEN),
    EXO_FIGHTER("Exo Fighter", Color.LIMEGREEN),
    ARMAGEDDON("Armageddon", Color.ROSYBROWN),
    GREEN_FLAME("Green Flame", Color.DARKOLIVEGREEN),
    FALCON("Falcon", Color.rgb(206, 190, 161));

    private String name;
    private Color bulletColor;

    ShipType(String name, Color bulletColor) {
        this.name = name;
        this.bulletColor = bulletColor;
    }

    public String getName() {
        return name;
    }

    public Color getBulletColor() {
        return bulletColor;
    }
}
