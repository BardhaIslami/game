package com.assignment.app.appcontroller.instructions;

import com.assignment.app.maingame.actions.GameMoves;
import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.gamedata.ServiceInterface;
import com.assignment.app.maingame.gamedata.activity.Input;
import com.assignment.app.maingame.gamedata.activity.Output;
import com.assignment.app.maingame.gamedata.activity.PlayerInfo;
import com.assignment.app.maingame.gamemoves.activity.ResultState;
import com.assignment.app.maingame.model.PlayerID;
import com.assignment.server.SocketInterface;

public class PlayWithComputer extends ChainingInstructions<String> {

    private ServiceInterface service;
    private SocketInterface socket;
    private GameMoves gameMoves;

     // Play command for automatic player if it's their turn.
    
    public PlayWithComputer(ServiceInterface service, SocketInterface socket, GameMoves gameMoves) {
        this.service = service;
        this.socket = socket;
        this.gameMoves = gameMoves;
    }

    // Execute play command 
 
    @Override
    public void execute(String input) {
        playMachineRecursive();

        Game after = service.getGameCurrentState();
        ResultState roundResult = after.getResult();
        next(String.valueOf(roundResult.getOutputValue().getValue()));
    }

    private void playMachineRecursive() {
        Game before = service.getGameCurrentState();
        PlayerInfo playersInfo = before.getPlayerInfo();
        PlayerID nextPlayer = playersInfo.getRootPlayer();
        Output outputNumber = before.getResult().getOutputValue();

        if (before.getResult().isWinner()) return;

        if (nextPlayer.isMachinePlayer()) {
            Input calculate = gameMoves.calculateNextRoundInput(outputNumber);

            service.play(calculate, nextPlayer);

            Game after = service.getGameCurrentState();
            String secondMsg = lastMsg(nextPlayer, after, calculate.toString());

            socket.broadcast(secondMsg);

            playMachineRecursive();
        }
    }

    private String lastMsg(PlayerID currentPlayer, Game after, String inputNumber) {
        ResultState results = after.getResult();

        return "\n"+currentPlayer +
                " played number " +
                inputNumber +
                " Result is " +
                results;
    }
    
}