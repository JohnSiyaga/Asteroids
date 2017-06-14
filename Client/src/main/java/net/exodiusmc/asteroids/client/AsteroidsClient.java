package net.exodiusmc.asteroids.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.exodiusmc.asteroids.client.layers.HudLayer;
import net.exodiusmc.asteroids.client.layers.MenuLayer;
import net.exodiusmc.asteroids.client.layers.SpaceLayer;
import net.exodiusmc.asteroids.common.util.Loader;

/**
 * @author Servant of Exodius
 * @version 1.0.0
 * @since 5/18/2017
 */
public class AsteroidsClient extends Application {

    private static final String TITLE = "Asteroids - Built by these two homo sapiens sapiens";

    @FXML
    private Canvas cvs;

    private Stage window;
    private Scene view;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        FXMLLoader loader = new FXMLLoader(Loader.resource("window.fxml"));
        loader.setController(this);

        Parent content = loader.load();
        this.window = window;
        this.view = new Scene(content);
        window.setScene(view);

        window.setTitle(TITLE);

        window.initStyle(StageStyle.UNDECORATED);

        view.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if(e.getCode() == KeyCode.ESCAPE) {
                window.close();
            }
        });

        // Make sure to kanker the game when we close it
        window.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        // Load all audio files and open the game when done
	    Loader.audioBig("sound/soundtrack.mp3").then(player -> {
            window.show();

            startGame(player);
        });
    }

    public void startGame(MediaPlayer soundtrack) {
        // Start and run the game runtime
        GameRuntime runtime = new GameRuntime(view, window, soundtrack, cvs.getGraphicsContext2D());
        runtime.start();

        // Add essential layers to the stack
        runtime.getLayers().push(new SpaceLayer());
        runtime.getLayers().push(new MenuLayer());
        runtime.getLayers().push(new HudLayer(false));
    }
}
