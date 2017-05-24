package net.exodiusmc.net.exodiusmc.asteroids.common.protocol.packet;

/**
 * The most basic implementation of a packet, containing data
 * that is encoded during transmission, and decoded when received.
 * 
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 24/05/2017
 */
public interface SimplePacket extends Packet {

	void encodePayload();

	void decodePayload();

}
