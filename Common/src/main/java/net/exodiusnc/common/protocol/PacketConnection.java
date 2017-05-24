package net.exodiusnc.common.protocol;

import java.nio.channels.Channel;

/**
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 24/05/2017
 */
public class PacketConnection {

	private Channel channel;

	public PacketConnection(Channel channel) {
		this.channel = channel;
	}

}
