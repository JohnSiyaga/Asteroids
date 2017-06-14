package net.exodiusmc.asteroids.common.util;

import net.exodiusmc.asteroids.common.Position;

/**
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 14/06/2017
 */
public class Collision {

	/**
	 * Returns true when the specified rectangle contains the specified position
	 *
	 * @param rect Rectangle
	 * @param pos Position
	 * @return boolean
	 */
	public static boolean squareCheck(Rectangle rect, Position pos) {
		return rect.contains(pos);
	}

	/**
	 * Returns the distance between the given positions
	 *
	 * @param origin Origin
	 * @param target Target
	 * @return Distance
	 */
	public static double distanceCheck(Position origin, Position target) {
		return Math.sqrt(Math.pow((origin.x - target.x), 2) + Math.pow((origin.y - target.y), 2));
	}

}
