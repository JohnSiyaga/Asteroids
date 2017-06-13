package net.exodiusmc.asteroids.client.layers;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import net.exodiusmc.asteroids.client.GameRuntime;
import net.exodiusmc.asteroids.client.Layer;
import net.exodiusmc.asteroids.common.util.Loader;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 6/11/2017
 */
public class MenuLayer implements Layer {

    private static final int BUTTONS = 3;

    private Image logo, play, play_selected, quit, quit_selected, settings, settings_selected;
    private int selectedButton = 0;
    private EventHandler<KeyEvent> keyEvent;

    public MenuLayer(){
        this.logo = Loader.image("img/asteroids_logo.png");
        this.play = Loader.image("img/play_1.png");
        this.play_selected = Loader.image("img/play_2.png");
        this.quit = Loader.image("img/quit_1.png");
        this.quit_selected = Loader.image("img/quit_2.png");
        this.settings = Loader.image("img/settings_1.png");
        this.settings_selected = Loader.image("img/settings_2.png");
    }

    @Override
    public void update(GameRuntime runtime) {

    }

    @Override
    public void render(GameRuntime runtime, GraphicsContext gfx) {
        gfx.drawImage(logo, (runtime.getCanvas().getWidth() / 2)- (logo.getWidth() / 2), 90);

        if(selectedButton == 0) {
            gfx.drawImage(play_selected, 283, 340);
        } else {
            gfx.drawImage(play, 283, 340);
        }

        if(selectedButton == 1) {
            gfx.drawImage(settings_selected, 283, 340 + 80);
        } else {
            gfx.drawImage(settings, 283, 340 + 80);
        }

        if(selectedButton == 2) {
            gfx.drawImage(quit_selected, 283, 340 + 160);
        } else {
            gfx.drawImage(quit, 283, 340 + 160);
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
                if(selectedButton == 0) {           // Play
                    runtime.getLayers().replace(new PlayMenuLayer());

	                buttonBeep();
                } else  if(selectedButton == 1) {   // Settings
	                buttonBeep();
                } else  if(selectedButton == 2) {   // Quit
                    runtime.getWindow().close();
                }
            }
        };

        runtime.getScene().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent);
    }

    @Override
    public void unregister(GameRuntime runtime) {
        runtime.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, keyEvent);
    }

    public static void buttonBeep() {
	    MediaPlayer player = Loader.audio("sound/buzz.mp3");
	    player.stop();
	    player.play();
    }

}
