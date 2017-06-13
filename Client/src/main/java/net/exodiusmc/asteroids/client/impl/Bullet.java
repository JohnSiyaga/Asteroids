package net.exodiusmc.asteroids.client.impl;

import javafx.scene.canvas.GraphicsContext;
import net.exodiusmc.asteroids.client.Drawable;
import net.exodiusmc.asteroids.common.ShipDirection;
import net.exodiusmc.asteroids.common.abstraction.AbstractBullet;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 5/27/2017
 */
public class Bullet implements AbstractBullet, Drawable {

    private Spaceship source;
    private double x, y;

    protected Bullet(Spaceship source) {
        this.source = source;
    }

    @Override
    public Spaceship getSource() {
        return source;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public void draw(GraphicsContext gfx) {
        gfx.setStroke(source.getType().getBulletColor());
        gfx.setLineWidth(3);
        gfx.strokeLine(x, y - 2, x, y + 2);
    }

    public void move() {
        if(source.getDirection() == ShipDirection.UP) {
            y -= 3;
        } else {
            y += 3;
        }
    }
}
