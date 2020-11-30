package com.assignment.app.maingame.validation;

import java.util.ArrayList;
import java.util.List;

import com.assignment.app.maingame.exceptions.ConfirmationException;
import com.assignment.app.maingame.gamedata.Game;

public class CheckNewGame implements Validator<Game> {

    public static final String INVALID_PLAYER = "Invalid players";

    private List<String> msgList = new ArrayList<>();

    /* Check if there are enough players
     * If not, error messages will be sent
     */
  
    @Override
    public boolean validate(Game game) {
        return isValidPlayer(game) ||
                setInvalidStatus(INVALID_PLAYER);
    }

    /* Check if the new game has enough players or
     * throw a runtime exception
     */
    
    @Override
    public void validOrNot(Game game) {
        if (!isValidPlayer(game)) {
            throw new ConfirmationException(INVALID_PLAYER);
        }
    }

    @Override
    public List<String> getValidationMsg() {
        return msgList;
    }

    private boolean isValidPlayer(Game game) {
        return game.getPlayerInfo().isValid();
    }

    private boolean setInvalidStatus(String msg) {
        msgList.add(msg);
        return false;
    }
}

