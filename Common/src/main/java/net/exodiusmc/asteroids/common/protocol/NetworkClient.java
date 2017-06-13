package net.exodiusmc.asteroids.common.protocol;

import com.sun.deploy.config.ClientConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import net.exodiusmc.asteroids.common.util.Promise;

import java.io.Closeable;

/**
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 24/05/2017
 */
public class NetworkClient implements Closeable {

	public EventLoopGroup group;

	protected ClientConfig builder;
	protected boolean identified;

	private Bootstrap bootstrap;


	/**
	 * Connect to the server a
	 *
	 * @return
	 */
	public Promise<Void> connect() {
		return null;
	}

	@Override
	public void close() {

	}

}
