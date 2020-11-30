package com.assignment.app.appcontroller.instructions;

import java.util.Arrays;

public enum InstructionType {
	
	ADD_PLAYER, ADD_AUTOMATIC_PLAYER, START_GAME, PLAY_GAME, GAME_STATUS, LEAVE_GAME, INVALID;

	//  Get enum value for string input
    
    public static InstructionType value(final String instructionType) {
        return Arrays.stream(InstructionType.values())
                .filter(i -> i.toString().equals(instructionType))
                .findFirst()
                .orElse(INVALID);
    }

}
