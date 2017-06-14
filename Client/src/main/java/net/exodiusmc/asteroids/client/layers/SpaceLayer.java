package net.exodiusmc.asteroids.client.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import net.exodiusmc.asteroids.client.GameRuntime;
import net.exodiusmc.asteroids.client.Layer;
import net.exodiusmc.asteroids.common.util.Loader;

/**
 * Layer for rendering the animated space background and side walls
 *
 * @author John Siyaga
 * @version 1.0.0
 * @since 6/11/2017
 */
public class SpaceLayer implements Layer {

    private Image space1;
    private Image space2;
    private Image space3;

    private int space1scroll = 0;
    private int space2scroll = 0;
    private int space3scroll = 0;

    @Override
    public void update(GameRuntime runtime) {
        int height = (int) runtime.getCanvas().getHeight();

        space1scroll = (space1scroll + 1) % height;
        space2scroll = (space2scroll + 3) % height;
        space3scroll = (space3scroll + 2) % height;
    }

    @Override
    public void render(GameRuntime runtime, GraphicsContext gfx) {
        int height = (int) runtime.getCanvas().getHeight();

        // Render the eternal void of space
        gfx.setFill(Color.BLACK);
        gfx.fillRect(0, 0, gfx.getCanvas().getWidth(), gfx.getCanvas().getHeight());

        gfx.drawImage(space1, 0, space1scroll);
        gfx.drawImage(space1, 0, space1scroll - height);

        gfx.drawImage(space2, 0, space2scroll);
        gfx.drawImage(space2, 0, space2scroll - height);

        gfx.drawImage(space3, 0, space3scroll);
        gfx.drawImage(space3, 0, space3scroll - height);
    }

    @Override
    public void register(GameRuntime runtime) {
	    this.space1 = Loader.image("img/space_1.png");
	    this.space2 = Loader.image("img/space_2.png");
	    this.space3 = Loader.image("img/space_3.png");
    }

    @Override
    public void unregister(GameRuntime runtime) {
    }

}
