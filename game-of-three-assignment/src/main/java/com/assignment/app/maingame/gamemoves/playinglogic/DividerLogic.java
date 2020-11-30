package com.assignment.app.maingame.gamemoves.playinglogic;


import java.util.ArrayList;
import java.util.List;

import com.assignment.app.lists.configuration.PropertiesConfiguration;
import com.assignment.app.maingame.gamedata.activity.Output;
import com.assignment.app.maingame.gamemoves.activity.InputState;
import com.assignment.app.maingame.validation.Validator;

	// Game logic that divides input by 3

public class DividerLogic implements GameMovesLogic {
	
     // Divider value.
    
    private static final int DIVIDER = Integer.parseInt(PropertiesConfiguration.getProperties()
            .getProperty("com.assignment.app.game.divider"));

    private final List<Validator<InputState>> validatorList;

    public DividerLogic() {
        this.validatorList = new ArrayList<>();
    }
    
    // Apply the game logic to game moves and get the output

    @Override
    public Output apply(final InputState inputState) {
        validOrNot(inputState);
        return applyTheLogic(inputState);
    }

    // Add validator for a customized algorithm

    public void addValidator(Validator<InputState> validator) {
        validatorList.add(validator);
    }

    // Validate game moves input
  
    private void validOrNot(InputState inputState) {
        validatorList.forEach(validator -> validator.validOrNot(inputState));
    }

    private Output applyTheLogic(InputState inputState) {
        return new Output(inputState.getValue() / DIVIDER);
    }

}
