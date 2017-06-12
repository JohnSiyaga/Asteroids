package net.exodiusmc.asteroids.client;

import javafx.scene.image.Image;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author John Siyaga
 * @version 1.0.0
 * @since 5/18/2017
 */
public class ImageLoader {

    private Set<Image> images;

    public ImageLoader(File images) {
        this.images = new HashSet<>();

        File[] imageFiles = images.listFiles((dir, name) -> FilenameUtils.getExtension(name).equals(".png"));

        if(imageFiles == null)
            throw new IllegalStateException("Something big has kankered during the loading of the image resources");

        try {
            for(File img : imageFiles) {
                this.images.add(new Image(img.toURI().toURL().toString()));
            }
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
