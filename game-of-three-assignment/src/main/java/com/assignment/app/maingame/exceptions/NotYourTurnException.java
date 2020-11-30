package com.assignment.app.maingame.exceptions;

public class NotYourTurnException extends GameException {

	// Runtime exception for the player that is not their turn to play
	
	public NotYourTurnException(String msg) {
		super(msg);
	}
}
