package net.exodiusmc.asteroids.client.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import net.exodiusmc.asteroids.client.GameRuntime;
import net.exodiusmc.asteroids.client.Layer;
import net.exodiusmc.asteroids.client.RenderUtils;
import net.exodiusmc.asteroids.client.SpriteAnimation;
import net.exodiusmc.asteroids.client.impl.Asteroid;
import net.exodiusmc.asteroids.client.impl.Bullet;
import net.exodiusmc.asteroids.client.impl.Spaceship;
import net.exodiusmc.asteroids.common.Position;
import net.exodiusmc.asteroids.common.ShipDirection;
import net.exodiusmc.asteroids.common.ShipType;
import net.exodiusmc.asteroids.common.abstraction.AbstractBullet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;


/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 6/11/2017
 */
public class SinglePlayerLayer implements Layer {

    private Spaceship ship;

    private SpriteAnimation shipAnimation;
    private Set<Asteroid> asteroids;
    private int shipAnimationTick = 0;

    private double spawnRate = 75;

    @Override
    public void update(GameRuntime runtime) {
        // Movement
    	if(ship.position.y <= runtime.getCanvas().getHeight() - 200) {
    		if(!runtime.getSoundtrack().isActive())
    			runtime.getSoundtrack().play();

		    if(runtime.getInput().isKeyPressed(KeyCode.RIGHT)) {
			    ship.motion += .9;
		    } else if(runtime.getInput().isKeyPressed(KeyCode.LEFT)) {
			    ship.motion -= .9;
		    }

		    ship.position.x += ship.motion;
		    ship.motion *= 0.93;

		    if (ship.position.x < 200) {
			    ship.position.x = 200;
			    ship.motion = 0;
		    } else if (ship.position.x + ship.getTexture().getWidth() / 4 > runtime.getCanvas().getWidth() - 200) {
			    ship.position.x = runtime.getCanvas().getWidth() - 200 - ship.getTexture().getWidth() / 4;
			    ship.motion = 0;
		    }
	    } else {
    		ship.position.y -= 4;

    		if(ship.position.y < runtime.getCanvas().getHeight() - 200) {
			    ship.position.y = runtime.getCanvas().getHeight() - 200;
		    }
	    }

	    // Shooting
        if(ship.position.y <= runtime.getCanvas().getHeight() - 200 && runtime.getInput().isKeyPressed(KeyCode.SPACE)) {
            ship.shoot();
        }

	    // Bullets
	    for(AbstractBullet aBullet : ship.getBullets()) {
		    ((Bullet) aBullet).move();
	    }

	    // Spawn asteroid
	    if(ship.position.y <= runtime.getCanvas().getHeight() - 200
		    && runtime.currentTick() % (int) spawnRate == 0) {
		    Position pos = new Position(
			    320 + new Random().nextInt((int) runtime.getCanvas().getWidth() - 470),
			    -50
		    );

    		asteroids.add(Asteroid.nextAsteroid(pos));

    		// Increase spawn rate
		    spawnRate -= 0.35;
	    }

	    Iterator<Asteroid> astIt = asteroids.iterator();

    	while(astIt.hasNext()) {
    		Asteroid ast = astIt.next();

    		// Remove off-screen asteroids
    		if(ast.getPosition().y > runtime.getCanvas().getHeight() + 200) {
			    astIt.remove();
			    continue;
		    }

		    // Move
		    ast.getPosition().y += ast.getSpeed();

		    // Rotate
		    if(ast.spinRight) {
			    ast.angle += ast.spin;
		    } else {
		    	ast.angle -= ast.spin;
		    }
	    }
    }

    @Override
    public void render(GameRuntime runtime, GraphicsContext gfx) {
	    // Bullets
	    for(AbstractBullet aBullet : ship.getBullets()) {
		    ((Bullet) aBullet).draw(gfx);
	    }

        // Draw the spaceship
	    RenderUtils.drawRotatedImage(
	    	gfx,
		    shipAnimation.nextFrame(shipAnimationTick % 7 == 0),
		    ship.motion,
		    ship.getPosition().x,
		    ship.getPosition().y
	    );

	    // Asteroids
	    for(Asteroid ast : asteroids) {
	    	ast.draw(gfx);
	    }

        shipAnimationTick++;
    }

    @Override
    public void register(GameRuntime runtime) {
        this.ship = new Spaceship(runtime, ShipType.DEFAULT, ShipDirection.UP);
        this.shipAnimation = new SpriteAnimation(ship.getTexture(), 4, true);
        this.asteroids = new HashSet<>();
    }

    @Override
    public void unregister(GameRuntime runtime) {
    }

}
