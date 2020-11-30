package com.assignment.app.appcontroller.instructions;

import com.assignment.app.maingame.gamedata.ServiceInterface;
import com.assignment.app.maingame.model.ComputerPlayer;
import com.assignment.server.SocketInterface;

public class AddComputerPlayer extends ChainingInstructions<String> {

	 private ServiceInterface service;
	 private SocketInterface socket;

	 // Add a automatic (machine) player 
	 
    public AddComputerPlayer(ServiceInterface service, SocketInterface socket) {
        this.service = service;
        this.socket = socket;
    }

    // Execute the 'add new automatic player' command.
     
    @Override
    public void execute(String info) {
        ComputerPlayer player = ComputerPlayer.generate();

        service.add(player);
        socket.broadcast("Added AI player " + player.getPlayersName() + " to game.");

        next(info);
    }
}