package com.assignment.app.maingame.gamedata.activity;

import java.util.Objects;

public class Input {
	
	private final int inputNumber;

   //  Initialize an Input object for the game input number.

     
    public Input(final int inputNumber) {
        this.inputNumber = inputNumber;
    }

    public int getValue() {
        return inputNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Input that = (Input) obj;
        return inputNumber == that.inputNumber;
    }

    @Override
    public String toString() {
        return String.valueOf(inputNumber);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(inputNumber);
    }

}