package com.assignment.app.maingame.gamedata;

import com.assignment.app.maingame.gamedata.activity.PlayerInfo;
import com.assignment.app.maingame.gamemoves.GameMovesInfo;
import com.assignment.app.maingame.gamemoves.GameMovesInfoInterface;
import com.assignment.app.maingame.gamemoves.activity.ResultState;
import com.assignment.app.maingame.gamemoves.playinglogic.GameMovesLogic;
import com.assignment.app.maingame.gamemoves.playinglogic.WinningLogicInterface;

public class GameBuilder {

	private GameMovesLogic moves;
	private WinningLogicInterface win;

	// Builds the game and defines play and win logic
	
	public GameBuilder(GameMovesLogic moves, WinningLogicInterface win) {
		this.moves = moves;
		this.win = win;
	}

	// Build the game
	
	public Game newGame() {
		
		GameMovesInfoInterface gameMoves = new GameMovesInfo(moves, win);

		return new Game(gameMoves, PlayerInfo.NULL, ResultState.NULL);
	}

}
