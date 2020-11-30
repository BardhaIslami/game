package com.assignment.app.maingame.gamemoves.activity;

import java.util.Objects;

import com.assignment.app.maingame.gamedata.activity.Input;
import com.assignment.app.maingame.gamedata.activity.Output;

public class InputState {

	private final Input inputValue;
	private final Output outputValue;

	// Represents the last added input

	public InputState(Input inputValue, Output outputValue) {
		this.inputValue = inputValue;
		this.outputValue = outputValue;
	}

	// Get value of the game round

	public int getValue() {
		return inputValue.getValue() + outputValue.getValue();
	}

	// Check the value of the input is higher or equal than boundary

	public boolean isHigherOrEqualThanBoundary(int boundary) {
		return getValue() >= boundary;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		InputState that = (InputState) obj;
		return Objects.equals(inputValue, that.inputValue) && Objects.equals(outputValue, that.outputValue);
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputValue, outputValue);
	}

	@Override
	public String toString() {
		return "last output was " + outputValue + " and your input was " + inputValue;
	}

}
