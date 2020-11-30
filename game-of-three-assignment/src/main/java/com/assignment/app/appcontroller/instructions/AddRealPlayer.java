package com.assignment.app.appcontroller.instructions;

import com.assignment.app.appcontroller.handlingexceptions.HandlingGameExceptions;
import com.assignment.app.maingame.exceptions.GameException;
import com.assignment.app.maingame.gamedata.ServiceInterface;
import com.assignment.app.maingame.model.RealPlayer;
import com.assignment.server.SocketInterface;

public class AddRealPlayer extends ChainingInstructions<String> {

    private ServiceInterface service;
    private SocketInterface socket;

    // Add a real (human) player 
   
    public AddRealPlayer(ServiceInterface service, SocketInterface socket) {
        this.service = service;
        this.socket = socket;
    }

    @Override
    public void execute(String playersName) {
    	//add authorized player
        RealPlayer player = new RealPlayer(Thread.currentThread().getName(), playersName);  

        try {
            service.add(player);
        } catch (GameException ex) {
            new HandlingGameExceptions(socket).handle(ex, player);
            return;
        }

        socket.broadcast("Added " + player.getPlayersName() + " player to the game.");

        this.next(playersName);
    }
}
