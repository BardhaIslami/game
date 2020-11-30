package com.assignment.app.maingame.validation;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

import com.assignment.app.lists.configuration.PropertiesConfiguration;
import com.assignment.app.maingame.exceptions.ConfirmationException;
import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.gamedata.activity.Input;

import java.util.List;

public class CheckInputRange implements Validator<Game> {

	private static final List<Input> VALID_NUMBERS = new CopyOnWriteArrayList<>();
	static {
		String[] input = PropertiesConfiguration.getProperties()
				.getProperty("com.assignment.app.game.input_numbers").split("[/s,.]");
		Arrays.stream(input).forEach(i -> {
			int value = Integer.parseInt(i);
			VALID_NUMBERS.add(new Input(value));
		});
	}

	private static final Function<Input, String> INVALID_NUMBERS = number -> " Invalid number "
			+ number + " is not within " + VALID_NUMBERS;

	private List<String> msgList = new ArrayList<>();
	private final Input input;

	public CheckInputRange(Input input) {
	        this.input = input;
	    }

	/* Check if input number is within valid numbers' range.
	 * If not, error messages will be sent.
	 */
	
	@Override
	public boolean validate(Game game) {
		return isValidNumber(input) || setInvalidStatus(INVALID_NUMBERS.apply(input));
	}

	// Check if game is ready (valid) to play with input number.
	
	@Override
	public void validOrNot(Game game) {
		if (!isValidNumber(input)) {
			throw new ConfirmationException(INVALID_NUMBERS.apply(input));
		}
	}

	@Override
	public List<String> getValidationMsg() {
		return msgList;
	}

	private boolean isValidNumber(Input input) {
		return VALID_NUMBERS.contains(input);
	}

	private boolean setInvalidStatus(String msg) {
		msgList.add(msg);
		return false;
	}

}
