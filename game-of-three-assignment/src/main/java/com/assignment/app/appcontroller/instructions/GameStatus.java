package com.assignment.app.appcontroller.instructions;

import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.gamedata.ServiceInterface;
import com.assignment.app.maingame.gamedata.activity.PlayerInfo;
import com.assignment.app.maingame.gamemoves.activity.ResultState;
import com.assignment.server.SocketInterface;

public class GameStatus extends ChainingInstructions<String> {

	private ServiceInterface service;
	private SocketInterface socket;

	// Game status command
	
	public GameStatus(ServiceInterface service, SocketInterface socket) {
		this.service = service;
		this.socket = socket;
	}
	
	// Execute game status command
	
	@Override
	public void execute(String info) {
		Game current = service.getGameCurrentState();

		PlayerInfo players = current.getPlayerInfo();
		ResultState currentResult = current.getResult();

		String msg = lastMsg(players, currentResult);
		socket.send(msg);

		next(info);
	}

	private String lastMsg(PlayerInfo players, ResultState currentResult) {
		return "Current players " + players + " Current result " + currentResult;
	}
}
