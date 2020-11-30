package com.assignment.app.maingame.gamemoves;

import com.assignment.app.maingame.gamedata.activity.Output;
import com.assignment.app.maingame.gamemoves.activity.InputState;
import com.assignment.app.maingame.gamemoves.activity.ResultState;
import com.assignment.app.maingame.gamemoves.playinglogic.GameMovesLogic;
import com.assignment.app.maingame.gamemoves.playinglogic.WinningLogicInterface;
import com.assignment.app.maingame.gamemoves.GameMovesInfoInterface;

import java.util.function.Function;

import com.assignment.app.maingame.exceptions.GameMovesException;


public class GameMovesInfo implements GameMovesInfoInterface {
	
		private final GameMovesLogic round;
	    private final WinningLogicInterface win;

	    /*
	     * Initialize game round.
 		 * Generates the game output of the played round.
	     * Decides if played round generates a winner.
	     */
	    
	    public GameMovesInfo(final GameMovesLogic round, final WinningLogicInterface win) {
	        this.round = round;
	        this.win = win;
	    }
	    
	    // Play turn according to input value

	    public ResultState play(final InputState inputState) {
	        Output outputValue = round.apply(inputState);
	        boolean wins = win.apply(outputValue);

	        return new ResultState(outputValue, wins);
	    }

}
