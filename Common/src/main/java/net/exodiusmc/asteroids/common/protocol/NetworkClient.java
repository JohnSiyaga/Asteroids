package net.exodiusmc.asteroids.common.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import net.exodiusmc.asteroids.common.util.Promise;

import java.io.Closeable;
import java.net.InetSocketAddress;

/**
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 24/05/2017
 */
public class NetworkClient implements Closeable {

	private InetSocketAddress server;

	private EventLoopGroup group;
	private Bootstrap bootstrap;
	private PacketConnection serverConnection;

	private boolean connected;

	/**
	 * <p>Create a new NetworkClient that will connect to the
	 * specified remote server address.
	 *
	 * <p>Once the NetworkClient is created, a connection attempt
	 * can be made by calling {@link #connect()}.
	 *
	 * <p>The address can always be
	 * changed by {@link #setServer(InetSocketAddress)}.
	 */
	public NetworkClient(InetSocketAddress server) {
		this.server = server;

		// Create the EventLoop groups
		group = new NioEventLoopGroup();

		// Create and configure the Client bootstrap
		bootstrap = new Bootstrap()
			.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ClientFactory());
	}


	/**
	 * Attempt to connect to the remote server. A promise is returned
	 * that will be completed when the client is either successfully
	 * connected or failed to connect.
	 *
	 * @return Promise
	 */
	public Promise<Void> connect() {
		// Exception when a connection is already currently active
		if(connected) throw new IllegalStateException("NetworkClient already connected");

		return new Promise<>(prom ->
			bootstrap.connect(server).addListener(future -> {
				// Fail the promise when a connection could not be made
				if(!future.isSuccess())
					prom.failure(new ProtocolException("Unknown host"));

				this.connected = true;

				prom.success();
			})
		);
	}

	@Override
	public void close() {
		this.connected = false;
		this.group.shutdownGracefully();
	}

	public InetSocketAddress getServer() {
		return server;
	}

	public void setServer(InetSocketAddress server) {
		this.server = server;
	}

	/**
	 * Factory for creating new client channels. Each new channel
	 * is initialized with a pipeline and wrapped in a PacketConnection.
	 */
	private class ClientFactory extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel channel) throws Exception {
			// Setup the pipeline
			ChannelPipeline pipe = channel.pipeline();

			pipe.addLast("LengthDecoder", new LengthFieldBasedFrameDecoder(10000, 0, 4, 0, 4));
			// TODO pipe.addLast("PacketDecoder", new InboundPacketDecoder(this));
			pipe.addLast("LengthEncoder", new LengthFieldPrepender(4));
			// TODO pipe.addLast("PacketEncoder", new OutboundPacketEncoder(this));

			// Create a PacketConnection
			serverConnection = new PacketConnection(channel);
		}

	}
}