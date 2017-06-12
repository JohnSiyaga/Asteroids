package net.exodiusmc.asteroids.client.impl;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.exodiusmc.asteroids.client.Drawable;
import net.exodiusmc.asteroids.client.GameRuntime;
import net.exodiusmc.asteroids.client.layers.ShipTextureFactory;
import net.exodiusmc.asteroids.common.ShipDirection;
import net.exodiusmc.asteroids.common.ShipType;
import net.exodiusmc.asteroids.common.abstraction.AbstractBullet;
import net.exodiusmc.asteroids.common.abstraction.AbstractSpaceship;

import java.util.HashSet;
import java.util.Set;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 5/27/2017
 */
public class Spaceship implements AbstractSpaceship, Drawable {

    private ShipType type;
    public double motion;
    public double position;
    private int health;
    private ShipDirection direction;
    private Set<AbstractBullet> bullets;
    private Image texture;

    public Spaceship(GameRuntime runtime, ShipType type, ShipDirection direction) {
        this.type = type;
        this.direction = direction;
        this.bullets = new HashSet<>();
        this.texture = ShipTextureFactory.getTexture(type);

        // Standard movement values
        this.motion = 0;
        this.position = (runtime.getCanvas().getWidth() / 2) - (texture.getWidth() / 8);
    }

    @Override
    public ShipType getType() {
        return type;
    }

    @Override
    public double getMotion() {
        return motion;
    }

    @Override
    public double getPosition() {
        return position;
    }

    @Override
    public int getHealth() {
        return 5;
    }

    @Override
    public ShipDirection getDirection() {
        return direction;
    }

    @Override
    public Set<AbstractBullet> getBullets() {
        return bullets;
    }

    public Image getTexture() { return texture; }

    /**
     * Shoot two bullets from this ship
     */
    public void shoot() {
        this.bullets.add(new Bullet(this));

        // PLACE THIS IN GAMELAYER //
        //for(AbstractBullet bullet : this.getBullets()) {
        //    ((Bullet) bullet).move();
        //}

        // render()                //
        //for(AbstractBullet bullet : this.getBullets()) {
        //    ((Bullet) bullet).draw(gfx);
        //}

        // ^^^^^^^^^^^^^^^^^^^^^^^ //
    }

    @Override
    public void draw(GraphicsContext gfx) {

    }
}
