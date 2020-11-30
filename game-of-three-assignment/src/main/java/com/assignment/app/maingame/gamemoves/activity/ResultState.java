package com.assignment.app.maingame.gamemoves.activity;

import java.util.Objects;

import com.assignment.app.maingame.gamedata.activity.Output;

public class ResultState {
	
	public static final ResultState NULL = new ResultState(null, false);

    private final Output outputValue;
    private final boolean winTheGame;

    // Get first round game results.
   
    public static ResultState getFirstResult() {
        return new ResultState(Output.startingNumber(), false);
    }
    
    // Create a new round result.

    public ResultState(final Output outputValue, final boolean winTheGame) {
        this.outputValue = outputValue;
        this.winTheGame = winTheGame;
    }

    public Output getOutputValue() {
        return outputValue;
    }

    public boolean isWinner() {
        return winTheGame;
    }

    // Check if another round can be played.
   
    public boolean playAgain() {
        return !isWinner();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ResultState that = (ResultState) obj;
        return winTheGame == that.winTheGame &&
                Objects.equals(outputValue, that.outputValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputValue, winTheGame);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Round result: ");
        sb.append(" outputValue ").append(outputValue);
        sb.append(", winTheGame ").append(winTheGame);
        sb.append('.');
        return sb.toString();
    }

}
