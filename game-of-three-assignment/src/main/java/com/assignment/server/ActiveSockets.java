package com.assignment.server;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class ActiveSockets {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveSockets.class);

	private final Map<String, Sockets> activeSockets;

	public ActiveSockets() {
		this.activeSockets = new ConcurrentHashMap<>();
	}

	public void register(String id, Sockets listening) {
		LOGGER.debug("Socket channel id: {}, listening: {}.", id, listening.hashCode());
		activeSockets.put(id, listening);
	}

	public Collection<Sockets> getActiveSockets() {
		return activeSockets.values();
	}
}
