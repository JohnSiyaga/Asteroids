package net.exodiusmc.asteroids.client.impl;

import javafx.scene.image.Image;
import net.exodiusmc.asteroids.client.SpriteAnimation;
import net.exodiusmc.asteroids.common.Position;
import net.exodiusmc.asteroids.common.util.Loader;
import net.exodiusmc.asteroids.common.util.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 6/11/2017
 */
public class Asteroid {

    public static final int SIZE = 100;

	private static final List<Image> textures;

	static {
		textures = new ArrayList<>();

		// Add all asteroid textures into an array
		textures.add(Loader.image("img/asteroid/1.png"));
		textures.add(Loader.image("img/asteroid/2.png"));
		textures.add(Loader.image("img/asteroid/3.png"));
		textures.add(Loader.image("img/asteroid/4.png"));
	}

    private Image texture;
    private Position position;
    private double speed;

    public boolean spinRight;
    public double angle;
    public double spin;

    private boolean destroyed;
    private SpriteAnimation explosion;

    // Locked constructor
    private Asteroid(Position pos) {
    	this.position = pos;
    }

	public Rectangle getBounds() {
		return new Rectangle(
			new Position(position.x - 50, position.y - 50),
			new Position(position.x + 50, position.y + 50)
		);
	}

	public Position getPosition() {
		return position;
	}

	public double getSpeed() {
		return speed;
	}

    public boolean isDestroyed() {
        return destroyed;
    }

    public Image getTexture() {
        return texture;
    }

    public SpriteAnimation getExplosion() {
        return explosion;
    }

    /**
	 * Destroy the asteroid
	 */
	public void destroy() {
	    if(destroyed) return;

    	Loader.audioSmall("sound/destruction.mp3").play();
    	Image explosionTex = Loader.image("img/asteroid/explosion.png");

    	this.destroyed = true;
    	this.explosion = new SpriteAnimation(explosionTex,26, true);
	}

	/**
	 * Returns a randomly selected texture for the next asteroid
	 *
	 * @return Image
	 */
	public static Asteroid nextAsteroid(Position pos) {
		Random rand = new Random();
		Asteroid ast = new Asteroid(pos);

		// Pick a random texture
		int idx = rand.nextInt(4);
		ast.texture = textures.get(idx);

		// Random speed
		ast.speed = 1.5 + rand.nextDouble() * 5;

		// Random rotation speed
		ast.spinRight = rand.nextBoolean();
		ast.spin = 0.5 + rand.nextDouble() * 2;

		return ast;
    }
}
