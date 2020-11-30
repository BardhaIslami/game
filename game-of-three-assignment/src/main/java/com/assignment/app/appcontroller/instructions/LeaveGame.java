package com.assignment.app.appcontroller.instructions;

import com.assignment.app.maingame.gamedata.ServiceInterface;
import com.assignment.app.maingame.model.PlayerID;
import com.assignment.app.maingame.model.RealPlayer;
import com.assignment.server.SocketInterface;

public class LeaveGame extends ChainingInstructions<String> {
	
	private ServiceInterface service;
    private SocketInterface socket;

    // Leave game command.
 
    public LeaveGame(ServiceInterface service, SocketInterface socket) {
        this.service = service;
        this.socket = socket;
    }

    // Execute leave game command.

    @Override
    public void execute(String info) {
    	//add authorized player
        PlayerID player = new RealPlayer(Thread.currentThread().getName(), "");  
        service.remove(player);
        service.stop();

        socket.send("Left game.");
        next(info);
    }

}
