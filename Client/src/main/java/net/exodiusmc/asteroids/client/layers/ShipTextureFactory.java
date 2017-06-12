package net.exodiusmc.asteroids.client.layers;

import javafx.scene.image.Image;
import net.exodiusmc.asteroids.common.ShipType;
import net.exodiusmc.asteroids.common.util.Loader;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 6/11/2017
 */
public class ShipTextureFactory {

    /**
     * Returns the texture used for the specified ship type
     *
     * @param type ShipType
     * @return Image
     */
    public static Image getTexture(ShipType type) {
        switch(type) {
            case DEFAULT:
                return Loader.image("img/ship/default.png");
            case AIR_WING:
                break;
            case BLUE_BIRD:
                break;
            case EXO_FIGHTER:
                break;
            case ARMAGEDDON:
                break;
            case GREEN_FLAME:
                break;
        }

        throw new IllegalArgumentException();
    }

}
