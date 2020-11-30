package com.assignment.app.maingame.validation;

import com.assignment.app.maingame.exceptions.ConfirmationException;
import com.assignment.app.maingame.gamedata.Game;

import java.util.List;
import java.util.ArrayList;

public class CheckOpenPlayer implements Validator<Game> {

	static final String OPEN_FOR_MORE_PLAYERS = "Cannot add more players";

	private List<String> msgList = new ArrayList<>();

	/* Check if you can add more players
	 * If not, error messages will be sent
	 */
	
	@Override
	public boolean validate(Game game) {
		return isOpenForPlayers(game) || setInvalidStatus(OPEN_FOR_MORE_PLAYERS + game.getPlayerInfo());
	}

	// Check if game is valid to play
	
	@Override
	public void validOrNot(Game game) {
		if (!isOpenForPlayers(game)) {
			throw new ConfirmationException(OPEN_FOR_MORE_PLAYERS + game.getPlayerInfo());
		}
	}

	@Override
	public List<String> getValidationMsg() {
		return msgList;
	}

	private boolean isOpenForPlayers(Game game) {
		return game.getPlayerInfo().acceptsOtherPlayers();
	}

	private boolean setInvalidStatus(String msg) {
		msgList.add(msg);
		return false;
	}

}
