package com.assignment.app.appcontroller.instructions;

import com.assignment.app.maingame.gamedata.ServiceInterface;
import com.assignment.server.SocketInterface;

public class InvalidInstructions extends ChainingInstructions<String> {

	private ServiceInterface service;
	private SocketInterface socket;

	// Invalid instructions commands (commands that are not known).
	
    public InvalidInstructions(ServiceInterface service, SocketInterface socket) {
		this.service = service;
		this.socket = socket;
	}

    // Execute Invalid instructions commands

    @Override
    public void execute(String info) {
        socket.send("invalid command. Available commands are: ADD_PLAYER:player_name, ADD_AUTOMATIC_PLAYER, START_GAME, PLAY_GAME:number, GAME_STATUS, LEAVE_GAME.");
        next(info);
    }
}
