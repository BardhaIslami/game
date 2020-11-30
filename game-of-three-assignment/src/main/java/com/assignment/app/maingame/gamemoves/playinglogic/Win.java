package com.assignment.app.maingame.gamemoves.playinglogic;

import com.assignment.app.maingame.gamedata.activity.Output;

public class Win implements WinningLogicInterface {
	
    private static final Output WIN_VALUE = new Output(1);

    // Checks if the value is equal to the winning value
    
    public Boolean apply(final Output outputValue) {
        return outputValue.equals(WIN_VALUE);
    }

}
