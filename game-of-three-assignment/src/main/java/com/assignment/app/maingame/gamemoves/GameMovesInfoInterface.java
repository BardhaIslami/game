package com.assignment.app.maingame.gamemoves;

import com.assignment.app.maingame.gamemoves.activity.ResultState;
import com.assignment.app.maingame.gamemoves.activity.InputState;
import com.assignment.app.maingame.exceptions.GameMovesException;

	// Interact with game rounds

public interface GameMovesInfoInterface  {
	
	// Play turn according to input value
	
    ResultState play(final InputState inputState);
}


