package net.exodiusmc.asteroids.client.impl;

import javafx.application.Platform;
import javafx.scene.image.Image;
import net.exodiusmc.asteroids.client.GameRuntime;
import net.exodiusmc.asteroids.client.ShipHealth;
import net.exodiusmc.asteroids.client.SpriteAnimation;
import net.exodiusmc.asteroids.client.layers.ShipTextureFactory;
import net.exodiusmc.asteroids.common.Position;
import net.exodiusmc.asteroids.common.ShipDirection;
import net.exodiusmc.asteroids.common.ShipType;
import net.exodiusmc.asteroids.common.abstraction.AbstractBullet;
import net.exodiusmc.asteroids.common.abstraction.AbstractSpaceship;
import net.exodiusmc.asteroids.common.util.Loader;

import java.util.HashSet;
import java.util.Set;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 5/27/2017
 */
public class Spaceship implements AbstractSpaceship {

	private static final long SHOOT_THROTTLE = 600;

    private ShipType type;
    private ShipHealth health;

    protected SpriteAnimation explosion;
    private boolean destroyed;

    public double motion;
    public Position position;

    private ShipDirection direction;
    private Set<AbstractBullet> bullets;

    private long lastShot = System.currentTimeMillis();

    private Image texture;

    public Spaceship(GameRuntime runtime, ShipType type, ShipDirection direction) {
        this.type = type;
        this.direction = direction;
        this.bullets = new HashSet<>();
        this.health = new ShipHealth();
        this.texture = ShipTextureFactory.getTexture(type);

        // Standard movement values
        this.motion = 0;
        this.position = new Position(
        	(runtime.getCanvas().getWidth() / 2) - (texture.getWidth() / 8),
	        runtime.getCanvas().getHeight() + 100
        );
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
    public Position getPosition() {
        return position;
    }

    @Override
    public ShipHealth getHealth() {
        return health;
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
    	if(System.currentTimeMillis() - SHOOT_THROTTLE < lastShot) return;

    	this.lastShot = System.currentTimeMillis();

		this.bullets.add(new Bullet(this, position.clone().add(13, 28), motion * 0.27));
		this.bullets.add(new Bullet(this, position.clone().add(89, 28), motion * 0.27));

	    Platform.runLater(() -> Loader.audioSmall("sound/shoot.mp3").play());
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Destroy the ship
     */
    public void destroy() {
        if(destroyed) return;

        Loader.audioSmall("sound/explosion.mp3").play();
        Image explosionTex = Loader.image("img/asteroid/explosion.png");

        this.destroyed = true;
        this.explosion = new SpriteAnimation(explosionTex,26, true);
    }
}
