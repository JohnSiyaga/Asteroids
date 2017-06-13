package net.exodiusmc.asteroids.common.protocol.packet;

/**
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 24/05/2017
 */
public interface RespondablePacket extends Packet {

	void getRequest(SimplePacket packet);

	void getResponse(SimplePacket packet);

}
