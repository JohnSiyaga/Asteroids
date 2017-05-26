package net.exodiusmc.asteroids.common.protocol;

/**
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 24/05/2017
 */
public class Util {

	// Locked constructor
	private Util() {
	}

	/**
	 * Throws a ProtocolException when object is null. The exception will
	 * be thrown with the specified error message.
	 *
	 * @param obj Object
	 * @param error Error message
	 */
	public static void isset(Object obj, String error) {
		if(obj == null)
			throw new ProtocolException(error);
	}
}
