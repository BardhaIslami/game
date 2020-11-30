package com.assignment.app.maingame.exceptions;

public class ConfirmationException extends GameException {

	// Runtime exception when game status is currently invalid

	public ConfirmationException(String msg) {
		super(msg);
	}

}
