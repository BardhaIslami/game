package com.assignment.app.maingame.actions;

import com.assignment.app.maingame.gamedata.activity.Output;
import com.assignment.app.maingame.gamedata.activity.Input;

public interface GameMoves {
	
	// Calculate the input of next round according to the last output.

    Input calculateNextRoundInput(Output outputNumber);

}
