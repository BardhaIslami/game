package com.assignment.app.appcontroller.instructions;

import com.assignment.app.appcontroller.handlingexceptions.HandlingGameExceptions;
import com.assignment.app.maingame.exceptions.GameException;
import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.gamedata.ServiceInterface;
import com.assignment.app.maingame.gamedata.activity.PlayerInfo;
import com.assignment.app.maingame.gamemoves.activity.ResultState;
import com.assignment.app.maingame.model.RealPlayer;
import com.assignment.server.SocketInterface;

public class StartGame extends ChainingInstructions<String> {

    private ServiceInterface service;
    private SocketInterface socket;
    
    // Start game command.
   
    public StartGame(ServiceInterface service, SocketInterface socket) {
        this.service = service;
        this.socket = socket;
    }
    
    // Execute start game command
    
    @Override
    public void execute(String info) {
        RealPlayer currentPlayer = new RealPlayer(Thread.currentThread().getName(), "");
        try {
            service.start();
        } catch (GameException ex) {
            new HandlingGameExceptions(socket).handle(ex, currentPlayer);
            return;
        }

        Game after = service.getGameCurrentState();
        String finalMsg = lastMsg(after);

        socket.broadcast(finalMsg);

        ResultState result = after.getResult();
        next(String.valueOf(result.getOutputValue().getValue()));
    }

    private String lastMsg(Game after) {
        PlayerInfo playerInfo = after.getPlayerInfo();
        ResultState resultState = after.getResult();

        return "Starting number is: " +
                resultState.getOutputValue() +
                "." +
                " Next player " + playerInfo.getRootPlayer() +
                ".";
    }
}
