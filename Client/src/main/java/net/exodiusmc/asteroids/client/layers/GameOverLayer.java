package net.exodiusmc.asteroids.client.layers;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import net.exodiusmc.asteroids.client.GameRuntime;
import net.exodiusmc.asteroids.client.Layer;
import net.exodiusmc.asteroids.common.util.Loader;

/**
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 14/06/2017
 */
public class GameOverLayer implements Layer {

    private SinglePlayerLayer game;

    private boolean gameOver;

    protected EventHandler<KeyEvent> keyEvent;

    public GameOverLayer() {
    }

    protected GameOverLayer(SinglePlayerLayer game) {
        this.game = game;
    }

    public void setGameOver() {
        this.gameOver = true;
    }

    @Override
    public void update(GameRuntime runtime) {

    }

    @Override
    public void render(GameRuntime runtime, GraphicsContext gfx) {

        if(gameOver) {
            Font go = Font.font("Press Start 2P", 50);

            gfx.setFill(Color.RED);
            gfx.setFont(go);
            gfx.fillText("GAME OVER", (runtime.getCanvas().getWidth() / 2) - 235, 300);

            Font score = Font.font("Press Start 2P", 25);

            gfx.setFill(Color.RED);
            gfx.setFont(score);
            gfx.fillText("Score: " + game.asteroidsDestroyed, (runtime.getCanvas().getWidth() / 2) - 125, 375);

            gfx.drawImage(Loader.image("img/back_2.png"), 283, 340 + 160);
        }
    }

    @Override
    public void register(GameRuntime runtime) {
        this.keyEvent = e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER) {
                runtime.getLayers().pop();
                runtime.getLayers().replace(new MenuLayer());
            }
        };

        runtime.getScene().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent);
    }

    @Override
    public void unregister(GameRuntime runtime) {
        runtime.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, keyEvent);
    }

}
