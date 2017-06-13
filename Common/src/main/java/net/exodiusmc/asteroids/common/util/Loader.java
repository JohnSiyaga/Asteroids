package net.exodiusmc.asteroids.common.util;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Static utility class for loading various information from disk
 *
 * @author John Siyaga
 * @version 1.0.0
 * @since 5/27/2017
 */
public class Loader {

	private static Map<String, MediaPlayer> AUDIO = new HashMap<>();

    /**
     * Returns the URL to the resource on the specified path
     *
     * @param path Resouce path
     * @return URL
     */
    public static URL resource(String path) {
        return Loader.class.getClassLoader().getResource(path);
    }

    /**
     * Returns an InputStream leading to the specified resource
     *
     * @param path Resouce path
     * @return InputStream to resource
     */
    public static InputStream resourceStream(String path) {
        return Loader.class.getClassLoader().getResourceAsStream(path);
    }

    /**
     * Load the specified path as an Image
     *
     * @param path Resource path
     * @return Image
     */
    public static Image image(String path) {
        return new Image(resourceStream(path));
    }

    /**
     * Load the specified path as an Image
     *
     * @param path Resource path
     * @return Image
     */
    public static Media sound(String path) {
        try {
            return new Media(resource(path).toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * Load and buffer all MediaPlayers in memory
	 *
	 * @param paths Audio paths
	 * @return Promise
	 */
	public static Promise<List<Promise<Void>>> audioBuffer(String... paths) {
		List<Promise<Void>> promiseList = new ArrayList<>(paths.length);

		for(String path : paths) {
			promiseList.add(
				new Promise<>(prom -> {
					MediaPlayer player;

					try {
						player = new MediaPlayer(new Media(resource(path).toURI().toString()));

						AUDIO.put(path, player);
					} catch (Exception ex) {
						throw new IllegalStateException("Could not buffer audio '" + path + "'", ex);
					}

					player.setOnReady(prom::success);
				})
			);
		}

		return Promise.combine(promiseList);
    }

	/**
	 * Returns the Media for the specified path. Make sure the
	 * specified path is loaded by including it into a call to
	 * {@link #audioBuffer(String...)}.
	 *
	 * @param path Path
	 * @return Media
	 */
	public static MediaPlayer audio(String path) {
		return AUDIO.get(path);
    }

}
