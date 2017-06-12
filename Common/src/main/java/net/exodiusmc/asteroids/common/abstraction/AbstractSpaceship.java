package net.exodiusmc.asteroids.common.abstraction;

import net.exodiusmc.asteroids.common.ShipDirection;
import net.exodiusmc.asteroids.common.ShipType;

import java.util.Set;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 5/27/2017
 */
public interface AbstractSpaceship {

    ShipType getType();

    double getMotion();

    double getPosition();

    int getHealth();

    ShipDirection getDirection();

    Set<AbstractBullet> getBullets();

}
