package net.exodiusmc.asteroids.client.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import net.exodiusmc.asteroids.client.*;
import net.exodiusmc.asteroids.client.impl.Asteroid;
import net.exodiusmc.asteroids.client.impl.Bullet;
import net.exodiusmc.asteroids.client.impl.Spaceship;
import net.exodiusmc.asteroids.common.Position;
import net.exodiusmc.asteroids.common.ShipDirection;
import net.exodiusmc.asteroids.common.ShipType;
import net.exodiusmc.asteroids.common.abstraction.AbstractBullet;
import net.exodiusmc.asteroids.common.util.Collision;

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

    protected Spaceship ship;

    private SpriteAnimation shipAnimation;
    private Set<Asteroid> asteroids;
    private GameOverLayer go;
    protected boolean gameOver;

    private double spawnRate = 75;

    public int asteroidsDestroyed;

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

	    // Damage overlay
        if(ship.getHealth().damageFade > 0) {
    	    ship.getHealth().damageFade -= 0.05;

    	    if(ship.getHealth().damageFade < 0) {
    	        ship.getHealth().damageFade = 0;
            }
        }

	    // Shooting
        if(!ship.isDestroyed() && ship.position.y <= runtime.getCanvas().getHeight() - 200 && runtime.getInput().isKeyPressed(KeyCode.SPACE)) {
            ship.shoot();
        }

	    // Bullets
	    for(AbstractBullet aBullet : ship.getBullets()) {
		    ((Bullet) aBullet).move();
	    }

	    // Spawn asteroid
	    if(ship.position.y <= runtime.getCanvas().getHeight() - 200
            && runtime.currentTick() % (int) spawnRate == 0) {
    	    int width = (int) runtime.getCanvas().getWidth();
    	    int min = 200 + (Asteroid.SIZE / 2);
    	    int max = width - 200 - (Asteroid.SIZE / 2);

		    Position pos = new Position(
			    min + new Random().nextInt(max - min),
			    -50
		    );

    		asteroids.add(Asteroid.nextAsteroid(pos));

    		// Increase spawn rate
		    spawnRate -= 0.2;

		    if(spawnRate <= 0)
		        spawnRate = 0.2;
	    }

	    Iterator<Asteroid> astIt = asteroids.iterator();

    	while(astIt.hasNext()) {
    	    boolean astHit = false;
    		Asteroid ast = astIt.next();

    		// Remove when out of screen or broken
            if((ast.getPosition().y > runtime.getCanvas().getHeight() + 150)
            || (ast.isDestroyed() && ast.getExplosion().getCurrentFrame() == 24)) {
                astIt.remove();
                continue;
            }

		    // Detect collision
		    Iterator<AbstractBullet> bulIt = ship.getBullets().iterator();

    		while(bulIt.hasNext()) {
    		    boolean bulHit = false;
    			Bullet bullet = (Bullet) bulIt.next();

    			if(Collision.squareCheck(ast.getBounds(), bullet.getPosition())
			    && Collision.distanceCheck(ast.getPosition(), bullet.getPosition()) < 40) {
    			    bulHit = true;
    				astHit = true;
			    }

			    if(bulHit) bulIt.remove();
		    }

		    if(astHit) {
                ast.destroy();
                asteroidsDestroyed++;
            }

            Position shipCenter = new Position(
                ship.getPosition().x + (ship.getTexture().getWidth() / 8),
                ship.getPosition().y + (ship.getTexture().getHeight() / 2)
            );

            if(!ast.isDestroyed() && !ship.isDestroyed() && Collision.distanceCheck(ast.getPosition(), shipCenter) < 90) {
                ast.destroy();

                ship.getHealth().damage(ast);

            }

            if(!ast.isDestroyed() && ast.getPosition().y >= 820 && !gameOver){
                ast.destroy();
                ship.getHealth().damage(ast);
            }

            // Destroy the ship / End game
            if(ship.getHealth().getAmount() <= 0) {
                ship.destroy();
                gameOver = true;

                Util.setTimeout(1000, () -> {
                    this.go.setGameOver();
                });
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
		    Bullet bullet = ((Bullet) aBullet);

            gfx.setStroke(bullet.getSource().getType().getBulletColor());
            gfx.setLineWidth(4);

            Position next = bullet.nextPosition();

            gfx.beginPath();
            gfx.moveTo(bullet.getPosition().x, bullet.getPosition().y);
            gfx.lineTo(next.x, next.y);
            gfx.stroke();
            gfx.closePath();
	    }

	    // Asteroids
	    for(Asteroid ast : asteroids) {
            RenderUtils.drawRotatedImage(
                gfx,
                ast.isDestroyed() ? ast.getExplosion().nextFrame(runtime.currentTick() % 2 == 0) : ast.getTexture(),
                ast.angle,
                ast.getPosition().x - Asteroid.SIZE / 2,
                ast.getPosition().y - Asteroid.SIZE / 2,
                Asteroid.SIZE,
                Asteroid.SIZE
            );
	    }

	    Image shipTexture = shipAnimation.nextFrame(runtime.currentTick() % 7 == 0);

        // Draw the spaceship
        if(!ship.isDestroyed()) {
            RenderUtils.drawRotatedImage(
                gfx,
                RenderUtils.colorizeImage(shipTexture, Color.RED, ship.getHealth().damageFade),
                ship.motion,
                ship.getPosition().x,
                ship.getPosition().y
            );
        }

        // Health bar
        double healthBarY = 50;
        double healthBarOffset = runtime.getCanvas().getWidth() - ShipHealth.BAR_WIDTH;

        if(ship.getHealth().getAmount() > 0) {

            gfx.setStroke(Color.RED);
            gfx.setLineWidth(5);

            gfx.beginPath();
            gfx.moveTo(healthBarOffset / 2, healthBarY);
            gfx.lineTo((healthBarOffset / 2) + (ShipHealth.BAR_WIDTH * ((float) ship.getHealth().getAmount() / 100)), healthBarY);
            gfx.stroke();
            gfx.closePath();
        }
    }

    @Override
    public void register(GameRuntime runtime) {
        this.ship = new Spaceship(runtime, ShipType.DEFAULT, ShipDirection.UP);
        this.shipAnimation = new SpriteAnimation(ship.getTexture(), 4, true);
        this.asteroids = new HashSet<>();
        this.go = new GameOverLayer(this);

        runtime.getLayers().push(new SideWallLayer());
        runtime.getLayers().push(go);
    }

    @Override
    public void unregister(GameRuntime runtime) {
    }

}
