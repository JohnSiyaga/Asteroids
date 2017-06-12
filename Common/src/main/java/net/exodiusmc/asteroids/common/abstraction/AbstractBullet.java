package net.exodiusmc.asteroids.common.abstraction;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 5/27/2017
 */
public interface AbstractBullet {

    AbstractSpaceship getSource();

    double getX();

    double getY();


}
