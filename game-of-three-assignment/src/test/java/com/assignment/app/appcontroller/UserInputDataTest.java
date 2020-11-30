package com.assignment.app.appcontroller;

import org.junit.Test;

import com.assignment.app.appcontroller.entities.UserInputData;

import static org.junit.Assert.assertFalse;

public class UserInputDataTest {
	
    @Test
    public void validWithNull() {
        UserInputData userInputData = new UserInputData(null, null);

        assertFalse("Is not valid with null instructions.", userInputData.isCorrect());
    }

    @Test
    public void validWithEmptyString() {
        UserInputData userInputData = new UserInputData("", null);

        assertFalse("Is not valid with empty string.", userInputData.isCorrect());
    }

}
