package com.assignment.server;

import java.io.BufferedReader;
import java.util.stream.Stream;
import java.io.PrintWriter;
import java.util.Objects;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Sockets {

	private static final Logger LOGGER = LoggerFactory.getLogger(Sockets.class);
	private static final int DELAYED_VALUE = 55;

	private BufferedReader input;
	private PrintWriter output;
	private Collection<Sockets> activeSocketChannels = new CopyOnWriteArrayList<>();

	// Socket that handles communation
	
	    public Sockets(BufferedReader input, PrintWriter output) {
	        this.input = input;
	        this.output = output;
	    }
	
	// Send output message
	
	public void send(String msg) {
		LOGGER.debug("Message sent: ", msg);
		System.out.println(msg);
	}

	public void delayed(String msg) {
		send(msg);
		try {
			Thread.sleep(DELAYED_VALUE);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	// Broadcast messages to active socket listeners.
	
	public void broadcast(String msg) {
		activeSocketChannels.forEach((socket -> socket.send(msg)));
	}

	// Listen to incoming messages
	
	public Stream<String> getStreamInput() {
		return getValidStreamInput().peek(msg -> LOGGER.info("Message received: ", msg));
	}

    /* Listens to the first message and blocks the thread 
     * until the message is received
     */
	
	public String nextLine() {
		return getValidStreamInput().peek(msg -> LOGGER.info("Message received: ", msg)).findFirst()
				.orElse("closing the stream");
	}

	// Check if there is data in the next stream
	
	public boolean empty() {
		try {
			return !input.ready();
		} catch (IOException e) {
			throw new RuntimeException("Buffer not ready", e);
		}
	}

	// Consume available data in the stream
	
	public void clear() {
		try {
			Thread.sleep(100);
			while (!empty()) {
				input.read();
			}
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException("Character not valid", e);
		}
	}

	public void activeSocketChannels(Collection<Sockets> activeSocketChannels) {
		this.activeSocketChannels = activeSocketChannels;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Sockets that = (Sockets) obj;
		return Objects.equals(input, that.input) && Objects.equals(output, that.output)
				&& Objects.equals(activeSocketChannels, that.activeSocketChannels);
	}

	@Override
	public int hashCode() {
		return Objects.hash(input, output, activeSocketChannels);
	}

	private Stream<String> getValidStreamInput() {
		return input.lines().filter(Objects::nonNull).filter(stream -> !stream.isEmpty());
	}

}
