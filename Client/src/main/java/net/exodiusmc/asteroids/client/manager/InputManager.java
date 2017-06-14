package net.exodiusmc.asteroids.client.manager;

import javafx.scene.input.KeyCode;
import net.exodiusmc.asteroids.client.GameRuntime;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 14-6-2017
 */
public class InputManager {

    private Set<KeyCode> activeKeys;

    public InputManager(GameRuntime runtime) {
        this.activeKeys = new HashSet<>();

        // Register handlers
        runtime.getScene().setOnKeyPressed(e -> activeKeys.add(e.getCode()));

        runtime.getScene().setOnKeyReleased(e -> activeKeys.remove(e.getCode()));
    }

    /**
     * Returns true when the specified KeyCode is currently pressed
     *
     * @param key KeyCode to check
     * @return boolean
     */
    public boolean isKeyPressed(KeyCode key) {
        return this.activeKeys.contains(key);
    }

}
