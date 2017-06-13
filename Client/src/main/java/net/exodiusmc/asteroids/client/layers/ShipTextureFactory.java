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
	            return Loader.image("img/ship/air_wing.png");
            case BLUE_BIRD:
	            return Loader.image("img/ship/blue_bird.png");
            case EXO_FIGHTER:
	            return Loader.image("img/ship/exo_fighter.png");
            case ARMAGEDDON:
	            return Loader.image("img/ship/armageddon.png");
            case GREEN_FLAME:
	            return Loader.image("img/ship/green_flame.png");
        }

        throw new IllegalArgumentException();
    }

}
