package com.assignment.app.appcontroller.handlingexceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assignment.app.maingame.exceptions.GameException;
import com.assignment.app.maingame.model.PlayerID;
import com.assignment.server.SocketInterface;

public class HandlingGameExceptions implements HandlingExceptions<GameException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(HandlingGameExceptions.class);
	private SocketInterface socket;

	public HandlingGameExceptions(SocketInterface socket) {
        this.socket = socket;
    }
	
	// Handle Exception with error message

	@Override
	public void handle(GameException ex, PlayerID player) {
		LOGGER.debug("Exception for player {}. message: {}.", player, ex.getMessage());
		String errorMsg = errorMessage(ex);
		socket.send(errorMsg);
	}

	private String errorMessage(GameException ex) {
		return "ERROR: " +
				ex.getMessage();
	}

}
