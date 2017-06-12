package net.exodiusmc.asteroids.client.layers;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import net.exodiusmc.asteroids.client.GameRuntime;
import net.exodiusmc.asteroids.client.Layer;
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
    private Image shipTexture;
    private EventHandler<KeyEvent> keyUpEvent;
    private EventHandler<KeyEvent> keyDownEvent;

    private SpriteAnimation shipAnimation;
    private int shipAnimationTick = 0;

    private boolean leftPressed;
    private boolean rightPressed;

    @Override
    public void update(GameRuntime runtime) {
        if(rightPressed) {
            ship.motion += .9;
        } else if(leftPressed) {
            ship.motion -= .9;
        }

        ship.position += ship.motion;
        ship.motion *= 0.92;

        if(ship.position < 200) {
            ship.position = 200;
            ship.motion = 0;
        } else if(ship.position + ship.getTexture().getWidth() / 4 > runtime.getCanvas().getWidth() - 200) {
            ship.position = runtime.getCanvas().getWidth() - 200 - ship.getTexture().getWidth() / 4 ;
            ship.motion = 0;
        }
    }

    @Override
    public void render(GameRuntime runtime, GraphicsContext gfx) {

        //Ship Animation
        gfx.drawImage(shipAnimation.nextFrame(shipAnimationTick % 7 == 0), ship.getPosition(), gfx.getCanvas().getHeight() - 200);

        shipAnimationTick++;
    }

    @Override
    public void register(GameRuntime runtime) {
        this.ship = new Spaceship(runtime, ShipType.DEFAULT, ShipDirection.UP);
        this.shipTexture = ship.getTexture();
        this.shipAnimation = new SpriteAnimation(shipTexture, 4, true);

        this.keyDownEvent = e -> {
            if(e.getCode() == KeyCode.LEFT) {
                leftPressed = true;
            } else if(e.getCode() == KeyCode.RIGHT) {
                rightPressed = true;
            }
        };

        this.keyUpEvent = e -> {
            if(e.getCode() == KeyCode.LEFT) {
                leftPressed = false;
            } else if(e.getCode() == KeyCode.RIGHT) {
                rightPressed = false;
            }
        };

        runtime.getScene().addEventHandler(KeyEvent.KEY_RELEASED, keyUpEvent);
        runtime.getScene().addEventHandler(KeyEvent.KEY_PRESSED, keyDownEvent);
    }

    @Override
    public void unregister(GameRuntime runtime) {
        runtime.getScene().removeEventHandler(KeyEvent.KEY_RELEASED, keyUpEvent);
        runtime.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, keyDownEvent);
    }

}
