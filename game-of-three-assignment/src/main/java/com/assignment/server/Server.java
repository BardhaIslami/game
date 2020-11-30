package com.assignment.server;

import java.io.IOException;
import java.io.Closeable;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server implements Closeable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

	private int serverPort;
	private ServerSocket server;

	// Main server socket on a port

	public Server(int serverPort) {
		this.serverPort = serverPort;
	}

	// Open main server socket

	public ServerSocket start() {
		try {
			server = new ServerSocket(serverPort);
			return server;
		} catch (IOException e) {
			throw new RuntimeException("Could not open server socket", e);
		}
	}

	// Close main server

	public void close() {
		try {
			server.close();
			LOGGER.debug("Closing server.");
			for (;;) {
				if (server.isClosed())
					return;
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not close the server", e);
		}

	}

}
