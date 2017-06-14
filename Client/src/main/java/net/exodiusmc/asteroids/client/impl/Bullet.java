package net.exodiusmc.asteroids.client.impl;

import javafx.scene.canvas.GraphicsContext;
import net.exodiusmc.asteroids.client.Drawable;
import net.exodiusmc.asteroids.common.Position;
import net.exodiusmc.asteroids.common.ShipDirection;
import net.exodiusmc.asteroids.common.abstraction.AbstractBullet;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 5/27/2017
 */
public class Bullet implements AbstractBullet, Drawable {

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

    @Override
    public void draw(GraphicsContext gfx) {
        gfx.setStroke(source.getType().getBulletColor());
        gfx.setLineWidth(4);

        Position next = nextPosition();

        gfx.beginPath();
        gfx.moveTo(position.x, position.y);
        gfx.lineTo(next.x, next.y);
        gfx.stroke();
        gfx.closePath();
    }

    public void move() {
        this.position = nextPosition();
    }

    private Position nextPosition() {
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
