package com.assignment.app.maingame.gamedata.activity;

import com.assignment.app.lists.configuration.PropertiesConfiguration;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class Output {
	
	 private final int outputValue;
	
	 private static final String NUMBER = PropertiesConfiguration.getProperties().
	            getProperty("com.assignment.app.game.random_number_to_start_game");
	    private static final String MIN_NUMBER = PropertiesConfiguration.getProperties().
	            getProperty("com.assignment.app.game.min_input_number", "10");
	    private static final String MAX_NUMBER = PropertiesConfiguration.getProperties().
	            getProperty("com.assignment.app.game.max_input_number", "100");

	    // Get the number to start the game with, it can be random or configured.
	    
	    public static Output startingNumber() {
	        int minNumber = Integer.parseInt(MIN_NUMBER);
	        int maxNumber = Integer.parseInt(MAX_NUMBER);

	        int inputValue = Optional.ofNullable(NUMBER)
	                .map(Integer::parseInt)
	                .orElse(randomNumber(minNumber, maxNumber));
	        return new Output(inputValue);
	    }

	    // Initialize the object for output number value and get the output value.
	    
	    public Output(final int outputValue) {
	        this.outputValue = outputValue;
	    }

	    public int getValue() {
	        return outputValue;
	    }

	    public Input inputNumber() {
	        return new Input(outputValue);
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        Output that = (Output) obj;
	        return outputValue == that.outputValue;
	    }
	    
	    @Override
	    public String toString() {
	        return String.valueOf(outputValue);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(outputValue);
	    }

	    private static int randomNumber(int minNumber, int maxNumber) {
	        return new Random().nextInt(maxNumber-minNumber) + minNumber;
	    }

}
