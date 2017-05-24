package net.exodiusmc.net.exodiusmc.asteroids.common.protocol;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * PacketListener classes contain PacketHandlers that get called when a packet
 * is received.
 *
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 24/05/2017
 */
public interface PacketListener {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface PacketHandler { /* Empty annotation */ }

}
