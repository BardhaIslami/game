package com.assignment.app.maingame.gamedata;

import java.util.Objects;

import com.assignment.app.maingame.gamedata.activity.Input;
import com.assignment.app.maingame.gamedata.activity.PlayerInfo;
import com.assignment.app.maingame.gamemoves.GameMovesInfoInterface;
import com.assignment.app.maingame.gamemoves.activity.InputState;
import com.assignment.app.maingame.gamemoves.activity.ResultState;
import com.assignment.app.maingame.model.PlayerID;
import com.assignment.app.maingame.validation.CheckObjectValidation;
import com.assignment.app.maingame.validation.Validator;

public class Game implements CheckObjectValidation<Game> {

	private final GameMovesInfoInterface gameMovesInfo;
	private final PlayerInfo playerInfo;
	private final ResultState resultState;
	
	/* Create new game
	 * gameMovesInfo the service for each round
	 * throws GameException if invalid PlayerInfo
	 */
	
	public Game(final GameMovesInfoInterface gameMovesInfo) {
		this.gameMovesInfo = gameMovesInfo;
		this.playerInfo = PlayerInfo.NULL;
		this.resultState = ResultState.NULL;
	}

	public Game(final GameMovesInfoInterface gameMovesInfo, final PlayerInfo playerInfo,
			final ResultState resultState) {
		this.gameMovesInfo = gameMovesInfo;
		this.playerInfo = playerInfo;
		this.resultState = resultState;
	}

	
	// Start game with a random number 
	
	public Game start() {
		return new Game(gameMovesInfo, playerInfo, ResultState.getFirstResult());
	}

	// Restarts the game with the same players

	public Game stop() {
		return new Game(gameMovesInfo, playerInfo, ResultState.NULL);
	}

	/* Adds a new player
	 * Returns new game copy with the new player included. 
	 */
	
	public Game addPlayer(final PlayerID player) {
		return new Game(gameMovesInfo, playerInfo.add(player), resultState);
	}

	// Removes the player and returns game copy without this player
	
	public Game removePlayer(PlayerID player) {
		return new Game(gameMovesInfo, playerInfo.remove(player), resultState);
	}
	
	/* Play with new value. 
	 * Before playing, validate the game with CheckCanPlay 
	 * New object that will hold the new game's state 
	 * Throws Game Exception if input number invalid
	 */
	
	public Game play(final Input inputValue) {
		InputState inputState = new InputState(inputValue, resultState.getOutputValue());
		return new Game(gameMovesInfo, playerInfo.next(), gameMovesInfo.play(inputState));
	}

	// Get players info with player as root
	
	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	// Gets the results of the played turn
	
	public ResultState getResult() {
		return resultState;
	}

	 // Validates the game with a validator.
	
	@Override
	public boolean validate(Validator<Game> validator) {
		return validator.validate(this);
	}
	
	
	/* Validates current game.
	 * If the game is invalid it throws ValidationException.
	 */
	
	@Override
	public void validOrNot(Validator<Game> validator) {
		validator.validOrNot(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Game game = (Game) obj;
		return Objects.equals(gameMovesInfo, game.gameMovesInfo) && Objects.equals(playerInfo, game.playerInfo)
				&& Objects.equals(resultState, game.resultState);
	}

	@Override
	public int hashCode() {
		return Objects.hash(gameMovesInfo, playerInfo, resultState);
	}

	@Override
	public String toString() {
		return playerInfo + " and " + resultState;
	}

}
