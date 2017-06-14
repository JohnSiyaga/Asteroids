package net.exodiusmc.asteroids.client.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
public class HudLayer implements Layer {

	private boolean displayStats;
	private Image wall;

	public HudLayer(boolean displayStats) {
		this.displayStats = displayStats;
	}

	@Override
	public void update(GameRuntime runtime) {

	}

	@Override
	public void render(GameRuntime runtime, GraphicsContext gfx) {
		int width = (int) runtime.getCanvas().getWidth();

		gfx.drawImage(wall, 0, 0, wall.getWidth() / 2, wall.getHeight() / 2);
		gfx.drawImage(wall, width, 0, -(wall.getWidth() / 2), wall.getHeight() / 2);
	}

	@Override
	public void register(GameRuntime runtime) {
		this.wall = Loader.image("img/side_wall.png");
	}

	@Override
	public void unregister(GameRuntime runtime) {
	}

}
