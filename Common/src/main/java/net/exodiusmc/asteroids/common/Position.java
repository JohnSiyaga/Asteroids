package net.exodiusmc.asteroids.common;

/**
 * Represents an x and y position
 *
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 14-6-2017
 */
public class Position implements Cloneable {

    public double x;
    public double y;

    public Position() {
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Add the specified x and y units to this position
     *
     * @param x X addition
     * @param y Y addition
     * @return self
     */
    public Position add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Add the specified position to this position
     *
     * @param pos Other position
     * @return self
     */
    public Position add(Position pos) {
        add(pos.x, pos.y);
        return this;
    }

    @Override
    public Position clone() {
        try {
            return (Position) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new AssertionError(ex);
        }
    }
}
