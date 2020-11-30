package com.assignment.app.maingame.gamedata;

import com.assignment.app.maingame.gamedata.activity.Input;
import com.assignment.app.maingame.model.PlayerID;
import com.assignment.app.maingame.exceptions.GameException;

public interface ServiceInterface {

	/*
	 * Adds new player to the game throws runtime exception if the player is not
	 * unique
	 */

	void add(final PlayerID p);

	// Removes player from the game

	void remove(PlayerID p);

	// Starts playing the game and throws runtime exception if there are not enough
	// players

	void start();

	// Restarts the game with the same players

	void stop();

	/*
	 * Checks the input number and plays the number throws runtime exception if
	 * there are not two players and if the input is invalid
	 */

	void play(final Input inputNumber, final PlayerID authorizedPlayer);

	// Gets game current state

	Game getGameCurrentState();

}
