package net.exodiusmc.asteroids.common.protocol.packet;

import net.exodiusmc.asteroids.common.protocol.ProtocolException;

/**
 * Represents a single packet of information that can be sent over a
 * PacketConnection. A packet should only hold it's own state, allowing
 * each packet to be used multiple times. This also means packets can be
 * sent back to their origin.
 *
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 24/05/2017
 */
public interface Packet {

	/**
	 * Create a new SimplePacket of the specified class
	 *
	 * @param clazz Packet class
	 * @return Packet
	 */
	static Packet create(Class<? extends Packet> clazz) {
		try {
			return clazz == null ? null : clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException ex) {
			String name = clazz.getClass().getSimpleName();
			throw new ProtocolException("Unable to construct " + name + "packet. Are you sure "
				+ "the received packet is registered?");
		}
	}

}
