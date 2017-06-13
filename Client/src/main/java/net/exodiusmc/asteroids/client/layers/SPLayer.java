package net.exodiusmc.asteroids.client.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import net.exodiusmc.asteroids.client.GameRuntime;
import net.exodiusmc.asteroids.client.Layer;
import net.exodiusmc.asteroids.client.RenderUtils;
import net.exodiusmc.asteroids.client.SpriteAnimation;
import net.exodiusmc.asteroids.client.impl.Spaceship;
import net.exodiusmc.asteroids.common.ShipDirection;
import net.exodiusmc.asteroids.common.ShipType;


/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 6/11/2017
 */
public class SPLayer implements Layer {

    private Spaceship ship;

    private SpriteAnimation shipAnimation;
    private int shipAnimationTick = 0;

    @Override
    public void update(GameRuntime runtime) {
        // Movement
    	if(ship.offset == 0) {
    		if(!runtime.getSoundtrack().isActive())
    			runtime.getSoundtrack().play();

		    if(runtime.getInput().isKeyPressed(KeyCode.RIGHT)) {
			    ship.motion += .9;
		    } else if(runtime.getInput().isKeyPressed(KeyCode.LEFT)) {
			    ship.motion -= .9;
		    }

		    ship.position += ship.motion;
		    ship.motion *= 0.93;

		    if (ship.position < 200) {
			    ship.position = 200;
			    ship.motion = 0;
		    } else if (ship.position + ship.getTexture().getWidth() / 4 > runtime.getCanvas().getWidth() - 200) {
			    ship.position = runtime.getCanvas().getWidth() - 200 - ship.getTexture().getWidth() / 4;
			    ship.motion = 0;
		    }
	    } else {
    		ship.offset -= 4;

    		if(ship.offset < 0) {
			    ship.offset = 0;
		    }
	    }

	    // Shooting
        if(ship.offset == 0 && runtime.getInput().isKeyPressed(KeyCode.SPACE)) {
            ship.shoot();
        }
    }

    @Override
    public void render(GameRuntime runtime, GraphicsContext gfx) {
        // Draw the spaceship
	    RenderUtils.drawRotatedImage(
	    	gfx,
		    shipAnimation.nextFrame(shipAnimationTick % 7 == 0),
		    ship.motion,
		    ship.getPosition(),
		    gfx.getCanvas().getHeight() - 200 + this.ship.offset
	    );

        shipAnimationTick++;
    }

    @Override
    public void register(GameRuntime runtime) {
        this.ship = new Spaceship(runtime, ShipType.EXO_FIGHTER, ShipDirection.UP);
        this.shipAnimation = new SpriteAnimation(ship.getTexture(), 4, true);
        this.ship.offset = 280;
    }

    @Override
    public void unregister(GameRuntime runtime) {
    }

}
