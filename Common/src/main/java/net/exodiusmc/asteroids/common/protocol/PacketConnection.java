package net.exodiusmc.asteroids.common.protocol;

import io.netty.channel.Channel;
import net.exodiusmc.asteroids.common.protocol.packet.Packet;
import net.exodiusmc.asteroids.common.util.Promise;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) ExodiusMC, 2017. Property of ExodiusMC.
 *
 * @author Macjuul
 * @version 1.0.0
 * @since 24/05/2017
 */
public class PacketConnection {

	private long connected;
	private Channel channel;
	private Set<PacketListener> listeners;

	/**
	 * Create a new PacketConnection wrapping the given Channel.
	 *
	 * @param channel Network Channel
	 */
	public PacketConnection(Channel channel) {
		this.connected = System.currentTimeMillis();
		this.channel = channel;
		this.listeners = new HashSet<>();
	}

	/**
	 * Returns the time at which this connection was established
	 *
	 * @return long
	 */
	public long getConnectionTime() {
		return connected;
	}

	/**
	 * Send a new packet to this connection. If the packet is
	 * not registered an exception will be thrown.
	 *
	 * @param packet Packet to send
	 * @return Completion promise
	 * @throws ProtocolException when the packet could not be sent
	 */
	public Promise<Void> sendPacket(String packet) {
		Util.isset(packet, "Packet must not be null");

		// Check if the channel is valid and able
		// to process packets
		if(!channel.isActive() || !channel.isOpen() || !channel.isWritable()) {
			throw new ProtocolException("Failed to send " + packet.getClass().getName()
				+ " over closed channel");
		}

		Promise<Void> promise = new Promise<>();

		// Write and flush the packet onto the channel context
		channel.writeAndFlush(packet).addListener(future -> promise.success());

		return promise;
	}

	/**
	 * Close the channel and send a disconnect reason to the remote.
	 * The msg can be left null, in which no reason will be specified.
	 *
	 * @param msg Disconnect reason, may be null
	 * @return Promise
	 */
	public Promise<Void> disconnect(String msg) {
		return new Promise<>(prom -> {
			if(msg == null) {
				channel.close().addListener(future -> prom.success());
			} else {
				channel.writeAndFlush(msg)
					.addListener(future -> {
						channel.close();
						prom.success();
					});
			}
		});
	}

	/**
	 * Attach a PacketListener to this PacketConnection.
	 *
	 * @param listener PacketListener
	 */
	public void attachListener(PacketListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Detach a PacketListener from this PacketConnection.
	 * When the PacketListener was not attached before,
	 * nothing will happen and no error will be thrown.
	 *
	 * @param listener PacketListener
	 */
	public void detachListener(PacketListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * Emit a packet to all attached PacketListener classes
	 *
	 * @param packet Packet
	 * @throws ProtocolException when a PacketHandler could not be called
	 */
	protected void emitPacket(Packet packet) {
		for(PacketListener listener : listeners) {
			for(Method handler : listener.getClass().getDeclaredMethods()) {
				// Validate the method and return an integer depending on
				// if the method is valid or not.
				int result = validateHandler(handler, packet.getClass());

				try {
					if(result == 1) {
						handler.invoke(listener, packet);
					} else if(result == 2) {
						handler.invoke(listener, this, packet);
					}
				} catch(IllegalAccessException | InvocationTargetException ex) {
					throw new ProtocolException("Failed to execute packet handler", ex);
				}
			}
		}
	}

	/**
	 * <p>Validate a PacketHandler method step-by-step. The method must
	 * agree to the following statements:
	 *
	 * <ul>
	 *     <li>1. The method must be annotated with @PacketHandler</li>
	 *     <li>2. (Packet) or (PacketConnection, Packet) parameters</li>
	 * </ul>
	 *
	 * @param method The method to validate
	 * @param packetType The type of packet received
	 * @return Result type. 0 for invalid, 1 for packet-only parameter and 2 for
	 * PacketConnection and Packet parameters.
	 */
	private int validateHandler(Method method, Class<? extends Packet> packetType) {
		// All handlers must be annotated with @PacketHandler
		if(!method.isAnnotationPresent(PacketListener.PacketHandler.class))
			return 0;

		// Methods with only the packet as parameter
		if(method.getParameterCount() == 1
		&& method.getParameterTypes()[0].isAssignableFrom(packetType))
			return 1;

		// Methods with both a PacketConnection and packet as parameters
		if(method.getParameterCount() == 2
		&& method.getParameterTypes()[0].equals(PacketConnection.class)
		&& method.getParameterTypes()[1].isAssignableFrom(packetType))
			return 1;

		return 0;
	}

}
