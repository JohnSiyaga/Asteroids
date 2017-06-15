package net.exodiusmc.asteroids.client;

import net.exodiusmc.asteroids.client.impl.Asteroid;
import net.exodiusmc.asteroids.common.abstraction.AbstractShipHealth;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 6/14/2017
 */
public class ShipHealth implements AbstractShipHealth {

    public static final int BAR_WIDTH = 650;

    private int health = 100;

    public double damageFade;

    /**
     * Damage the ship
     *
     * @param source The asteroid causing harm
     */
    public void damage(Asteroid source) {
        health -= 20 + source.getSpeed();
        damageFade = 1;
    }

    @Override
    public int getAmount() {
        return health;
    }
}
