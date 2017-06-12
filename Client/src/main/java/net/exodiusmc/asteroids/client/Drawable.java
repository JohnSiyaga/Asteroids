package net.exodiusmc.asteroids.client;

import javafx.scene.canvas.GraphicsContext;

/**
 * A class that can be drawn onto a graphics context
 *
 * @author John Siyaga
 * @version 1.0.0
 * @since 6/11/2017
 */
public interface Drawable {

    void draw(GraphicsContext gfx);

}
