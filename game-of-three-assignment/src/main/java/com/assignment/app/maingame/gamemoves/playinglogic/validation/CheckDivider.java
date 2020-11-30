package com.assignment.app.maingame.gamemoves.playinglogic.validation;

import com.assignment.app.lists.configuration.PropertiesConfiguration;
import com.assignment.app.maingame.exceptions.GameMovesException;
import com.assignment.app.maingame.gamemoves.activity.InputState;
import com.assignment.app.maingame.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class CheckDivider implements Validator<InputState> {
	
	private static final String INVALID_INPUT = " invalid input ";
    private static final int MIN_NUMBER = 2;
    private static final int DIVIDER = Integer.parseInt(PropertiesConfiguration.getProperties()
            .getProperty("com.assignment.app.game.divider"));

    private List<String> msg = new ArrayList<>();
    
    /* Check if input number can be divided by 3.
     * If not, will show invalid messages.
     */
    
    @Override
    public boolean validate(InputState inputState) {
        return isValid(inputState) ||
                setToInvalid(INVALID_INPUT + inputState);
    }
    
    @Override
    public void validOrNot(InputState inputState) {
        if (!isValid(inputState)) {
            throw new GameMovesException(INVALID_INPUT + inputState);
        }
    }

    @Override
    public List<String> getValidationMsg() {
        return msg;
    }

    private boolean isValid(InputState inputState) {
        return isHigherOrEqualThanBoundary(inputState) &&
               isDividable(inputState);
    }

    private boolean isHigherOrEqualThanBoundary(InputState inputState) {
        return inputState.isHigherOrEqualThanBoundary(MIN_NUMBER);
    }

    private boolean isDividable(InputState inputState) {
        return inputState.getValue() % DIVIDER == 0;
    }

    private boolean setToInvalid(String message) {
        msg.add(message);
        return false;
    }

}
