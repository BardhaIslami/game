package com.assignment.app.appcontroller.entities;

import java.util.Optional;

public class UserInputData {

	// instructions are the commands the user gives to be executed
	// instructionData are the parameters of the command

	private String instruction;
	private String instructionData;

	public UserInputData(String instruction, String instructionData) {
		this.instruction = instruction;
		this.instructionData = instructionData;
	}

	public String getInstruction() {
		return instruction;
	}

	public String getInstructionData() {
		return instructionData;
	}

	public boolean isCorrect() {
		return Optional.ofNullable(instruction).filter(s -> !s.isEmpty()).isPresent();
	}

}
