package net.exodiusmc.asteroids.client.impl;

import net.exodiusmc.asteroids.common.Position;
import net.exodiusmc.asteroids.common.ShipDirection;
import net.exodiusmc.asteroids.common.abstraction.AbstractBullet;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 5/27/2017
 */
public class Bullet implements AbstractBullet {

    private Spaceship source;
    private Position position;
    private double angle;

    protected Bullet(Spaceship source, Position pos, double angle) {
        this.source = source;
        this.position = pos;
        this.angle = angle;
    }

    @Override
    public Spaceship getSource() {
        return source;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public double getAngle() {
        return this.angle;
    }

    public void move() {
        this.position = nextPosition();
    }

    public Position nextPosition() {
        Position next = position.clone();

        // Move along the y axis
        if(source.getDirection() == ShipDirection.UP) {
            next.y -= 7;
        } else {
            next.y += 7;
        }

        next.x += angle;

        return next;
    }
}
