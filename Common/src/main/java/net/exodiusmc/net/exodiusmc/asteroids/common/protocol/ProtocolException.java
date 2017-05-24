package net.exodiusmc.net.exodiusmc.asteroids.common.protocol;

/**
 * Thrown when  an issue arises during the processing of an
 * inbound or outbound packet. These exceptions should be
 * taken seriously as it means data is being lost.
 *
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 24/05/2017
 */
public class ProtocolException extends RuntimeException {

	public ProtocolException(String message) {
		super(message);
	}

	public ProtocolException(String message, Throwable cause) {
		super(message, cause);
	}
}
