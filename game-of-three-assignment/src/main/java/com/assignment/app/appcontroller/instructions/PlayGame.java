package com.assignment.app.appcontroller.instructions;

import com.assignment.app.appcontroller.handlingexceptions.HandlingGameExceptions;
import com.assignment.app.maingame.exceptions.GameException;
import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.gamedata.ServiceInterface;
import com.assignment.app.maingame.gamedata.activity.Input;
import com.assignment.app.maingame.gamedata.activity.PlayerInfo;
import com.assignment.app.maingame.gamemoves.activity.ResultState;
import com.assignment.app.maingame.model.PlayerID;
import com.assignment.app.maingame.model.RealPlayer;
import com.assignment.server.SocketInterface;

public class PlayGame extends ChainingInstructions<String> {

    private ServiceInterface service;
    private SocketInterface socket;

    // Play game command
  
    public PlayGame(ServiceInterface service, SocketInterface socket) {
        this.service = service;
        this.socket = socket;
    }

    // Execute play game command
    
    @Override
    public void execute(String input) {
    	//add authorized player
        PlayerID player = new RealPlayer(Thread.currentThread().getName(), ""); 
        Input parsedInput = parseInput(input);

        Game before = service.getGameCurrentState();
        PlayerInfo playersInfo = before.getPlayerInfo();

        try {
            service.play(parsedInput, player);
        } catch (GameException ex) {
            new HandlingGameExceptions(socket).handle(ex, player);
            return;
        }

        Game after = service.getGameCurrentState();
        String msg = lastMsg(playersInfo.getRootPlayer(), after, input);

        socket.broadcast(msg);

        ResultState results = after.getResult();
        next(String.valueOf(results.getOutputValue().getValue()));
    }

    private String lastMsg(PlayerID currentPlayer, Game after, String inputNumber) {
        ResultState roundResult = after.getResult();

        return currentPlayer +
                " played number " +
                inputNumber +
                " Result is " +
                roundResult;
    }

    private Input parseInput(String input) {
        try {
            return new Input(Integer.parseInt(input));
        } catch (NumberFormatException ex) {
            socket.send("ERROR: "+ex.getMessage());
        }
        return null;
    }
}