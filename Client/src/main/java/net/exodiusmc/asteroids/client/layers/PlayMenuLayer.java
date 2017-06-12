package net.exodiusmc.asteroids.client.layers;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import net.exodiusmc.asteroids.client.GameRuntime;
import net.exodiusmc.asteroids.client.Layer;
import net.exodiusmc.asteroids.common.util.Loader;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 6/11/2017
 */
public class PlayMenuLayer implements Layer {

    private static final int BUTTONS = 3;

    private Image logo, singleplayer, singleplayer_selected, multiplayer, multiplayer_selected, back, back_selected;
    private int selectedButton = 0;
    private EventHandler<KeyEvent> keyEvent;

    public PlayMenuLayer(){
        this.logo = Loader.image("img/asteroids_logo.png");
        this.singleplayer = Loader.image("img/singleplayer_1.png");
        this.singleplayer_selected = Loader.image("img/singleplayer_2.png");
        this.multiplayer = Loader.image("img/multiplayer_1.png");
        this.multiplayer_selected = Loader.image("img/multiplayer_2.png");
        this.back = Loader.image("img/back_1.png");
        this.back_selected = Loader.image("img/back_2.png");
    }

    @Override
    public void update(GameRuntime runtime) {

    }

    @Override
    public void render(GameRuntime runtime, GraphicsContext gfx) {
        gfx.drawImage(logo, (runtime.getCanvas().getWidth() / 2)- (logo.getWidth() / 2), 90);

        if(selectedButton == 0) {
            gfx.drawImage(singleplayer_selected, 283, 340);
        } else {
            gfx.drawImage(singleplayer, 283, 340);
        }

        if(selectedButton == 1) {
            gfx.drawImage(multiplayer_selected, 283, 340 + 80);
        } else {
            gfx.drawImage(multiplayer, 283, 340 + 80);
        }

        if(selectedButton == 2) {
            gfx.drawImage(back_selected, 283, 340 + 160);
        } else {
            gfx.drawImage(back, 283, 340 + 160);
        }
    }

    @Override
    public void register(GameRuntime runtime) {
        this.keyEvent = e -> {
            System.out.println("Pressed");

            if(e.getCode() == KeyCode.UP) {
                this.selectedButton--;

                if(this.selectedButton < 0)
                    this.selectedButton = BUTTONS - 1;
            } else if(e.getCode() == KeyCode.DOWN) {
                this.selectedButton++;

                if(this.selectedButton == BUTTONS)
                    this.selectedButton = 0;
            } else if(e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER) {
                if(selectedButton == 0) {           // Play Singleplayer
                    runtime.getLayers().replace(new SPLayer());
                } else  if(selectedButton == 1) {   // Play Multiplayer

                } else  if(selectedButton == 2) {   // Back to main menu
                    runtime.getLayers().replace(new MenuLayer());
                }
            }
        };

        runtime.getScene().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent);
    }

    @Override
    public void unregister(GameRuntime runtime) {
        runtime.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, keyEvent);
    }

}
