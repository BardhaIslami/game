package com.assignment.app.maingame.validation;

import java.util.ArrayList;
import java.util.List;

import com.assignment.app.maingame.exceptions.NotYourTurnException;
import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.model.PlayerID;

public class CheckCurrentPlayer implements Validator<Game> {

	public static final String INVALID = "Not your turn";

	private List<String> msgList = new ArrayList<>();

	private PlayerID player;

	// Check it is the turn of current player to play

	public CheckCurrentPlayer(PlayerID player) {
		this.player = player;
	}

	// Check if game is valid to play with this player

	@Override
	public boolean validate(Game game) {
		return isPlayersTurn(game, player) || setInvalidStatus(INVALID);
	}

	// Check if game is valid to play with this player

	@Override
	public void validOrNot(Game game) {
		if (!isPlayersTurn(game, player)) {
			throw new NotYourTurnException(INVALID);
		}
	}

	@Override
	public List<String> getValidationMsg() {
		return msgList;
	}

	private boolean isPlayersTurn(Game game, PlayerID nextPlayer) {
		PlayerID currentPlayer = game.getPlayerInfo().getRootPlayer();
		return currentPlayer.isSamePlayer(nextPlayer);
	}

	private boolean setInvalidStatus(String msg) {
		msgList.add(msg);
		return false;
	}
}
