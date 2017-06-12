package net.exodiusmc.asteroids.client;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/**
 * Runtime runner for running the game loop.
 *
 * @author John Siyaga
 * @version 1.0.0
 * @since 6/11/2017
 */
public class GameRuntime extends AnimationTimer {

    private LayerManager layers;
    private GraphicsContext ctx;
    private Scene scene;
    private Stage window;

    public GameRuntime(Scene scene, Stage window, GraphicsContext ctx) {
        this.scene = scene;
        this.window = window;
        this.layers = new LayerManager(this);
        this.ctx = ctx;
    }

    @Override
    public void handle(long now) {
        // Clear the canvas
        ctx.clearRect(0, 0, ctx.getCanvas().getWidth(), ctx.getCanvas().getHeight());

        // Render the game
        layers.tick(ctx);
    }

    public LayerManager getLayers() {
        return layers;
    }

    public GraphicsContext getContext() {
        return ctx;
    }

    public Canvas getCanvas() {
        return ctx.getCanvas();
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getWindow() {
        return window;
    }
}
