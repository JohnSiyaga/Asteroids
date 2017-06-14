package net.exodiusmc.asteroids.common.abstraction;

import net.exodiusmc.asteroids.common.Position;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 5/27/2017
 */
public interface AbstractBullet {

    AbstractSpaceship getSource();

    Position getPosition();

    double getAngle();

}
