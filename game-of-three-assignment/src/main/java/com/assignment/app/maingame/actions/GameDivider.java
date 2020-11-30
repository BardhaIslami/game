package com.assignment.app.maingame.actions;

import com.assignment.app.maingame.gamedata.activity.Output;
import com.assignment.app.maingame.gamedata.activity.Input;
import com.assignment.app.lists.configuration.PropertiesConfiguration;

import java.util.Map;
import java.util.HashMap;

public class GameDivider implements GameMoves {
	
	// Value of the divider
	
    private static final int DIVIDER_VALUE = Integer.parseInt(PropertiesConfiguration.getProperties()
            .getProperty("com.application.app.game.divider_value"));

    // The values that has to be added to input so then they will be divided by the divider

    private static final Map<Integer, Integer> VALUES;
    static {
        VALUES = new HashMap<>();
        VALUES.put(0, 0);
        VALUES.put(1, -1);
        VALUES.put(2, 1);
    }

    // Calculate the input of next round according to the last output.
  
    @Override
    public Input calculateNextRoundInput(final Output outputNumber) {
        return getAdditionNumber(outputNumber);
    }

    // Calculate the added number to get to the number that can be divided by 3.
    
    private Input getAdditionNumber(final Output outputNumber) {
        int remaining = outputNumber.getValue() % DIVIDER_VALUE;
        int sum = VALUES.get(remaining);
        return new Input(sum);
    }

}
