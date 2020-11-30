package com.assignment.app.maingame.validation;

import java.util.ArrayList;
import java.util.List;

import com.assignment.app.maingame.exceptions.ConfirmationException;
import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.gamedata.activity.PlayerInfo;
import com.assignment.app.maingame.model.PlayerID;

public class CheckUniquePlayer implements Validator<Game> {

    public static final String INVALID = "Player could not be added.";

    private List<String> msgList = new ArrayList<>();
    private PlayerID player;

    public CheckUniquePlayer(PlayerID player) {
        this.player = player;
    }
    
    /* Check if user can add a new player.
     * If not, error messages will be sent
     */

    @Override
    public boolean validate(Game game) {
		return !samePlayer(game.getPlayerInfo(), player)
                || setInvalidStatus(INVALID);
    }
    
    /* Check if user can add a new player or
     * throw a runtime exception.
     */
  
    @Override
    public void validOrNot(Game game) {
        if (samePlayer(game.getPlayerInfo(), player)) {
            throw new ConfirmationException(INVALID);
        }
    }

    @Override
    public List<String> getValidationMsg() {
        return msgList;
    }

    private boolean samePlayer (PlayerInfo playerInfo, PlayerID player) {
        return playerInfo.hasPlayer(player);
    }

    private boolean setInvalidStatus(String msg) {
        msgList.add(msg);
        return false;
    }
}

