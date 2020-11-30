package com.assignment.app.maingame.validation;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.assignment.app.maingame.exceptions.ConfirmationException;
import com.assignment.app.maingame.exceptions.GameException;
import com.assignment.app.maingame.gamedata.Game;

public class CheckCanPlay implements Validator<Game> {

	static final String GAME_STATUS_MSG = "cannot play because ";
	static final String PLAYER_INFO_MSG = "cannot play because ";

	private List<String> msgList = new ArrayList<>();
	
	// Check game validity, if it is invalid error messages will appear

	@Override
	public boolean validate(Game game) {
		return Stream
				.of(isValidResult(game)
						|| setInvalidStatus(GAME_STATUS_MSG + game.getResult()),
						isValidPlayerInfo(game)
								|| setInvalidStatus(PLAYER_INFO_MSG + game.getPlayerInfo()))
				.allMatch(Boolean::booleanValue);
	}

	// Check if game is valid

	@Override
	public void validOrNot(Game game) {
		if (!isValidResult(game)) {
			throw new ConfirmationException(GAME_STATUS_MSG + game.getResult());
		}

		if (!isValidPlayerInfo(game)) {
			throw new GameException(PLAYER_INFO_MSG + game.getPlayerInfo());
		}
	}

	@Override
	public List<String> getValidationMsg() {
		return msgList;
	}
	private boolean isValidPlayerInfo(Game game) {
		return game.getPlayerInfo().isValid();
	}

	private boolean isValidResult(Game game) {
		return game.getResult().playAgain();
	}

	private boolean setInvalidStatus(String msg) {
		msgList.add(msg);
		return false;
	}

}
