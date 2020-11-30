package com.assignment.app.appcontroller.entities;

import com.assignment.app.appcontroller.entities.UserInputData;

import java.util.function.Function;

public class UserInputDetails implements Function<String, UserInputData> {
	
	// Deserializes data and returns parsed user input data
	
    @Override
    public UserInputData apply(String userInput) {
        String[] inputArray = userInput.split(":", 2);
        String instruction = inputArray[0];
        String instructionData = inputArray.length > 1 ? inputArray[1] : "";

        return new UserInputData(instruction, instructionData);
    }

}
