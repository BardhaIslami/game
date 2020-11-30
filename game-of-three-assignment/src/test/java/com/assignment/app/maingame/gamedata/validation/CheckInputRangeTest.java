package com.assignment.app.maingame.gamedata.validation;

import org.junit.Before;
import org.junit.Test;

import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.gamedata.activity.Input;
import com.assignment.app.maingame.validation.CheckInputRange;
import com.assignment.app.maingame.exceptions.ConfirmationException;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CheckInputRangeTest {

	private CheckInputRange checkInputRange;
	private Input input;
	private Game game;

	@Before
	public void setUp() throws Exception {
		input = mock(Input.class);
		game = mock(Game.class);
	}

	@Test
	public void invalidWhenInputOutsideRange() {
		checkInputRange = new CheckInputRange(new Input(-536));
		boolean result = checkInputRange.validate(game);

		assertFalse("Cannot play with number outside range", result);
		assertThat("Set msg-s when number is outside range.",
				checkInputRange.getValidationMsg(),
				containsInAnyOrder("Cannot play because -536 is not within [-1, 0, 1]"));
	}
	
	@Test(expected = ConfirmationException.class)
	public void throwWhenInputOutsideRange() {
	checkInputRange = new CheckInputRange(new Input(504));

		checkInputRange.validOrNot(game);

		fail("Throws ConfirmationException.");
	}

	@Test
	public void validWhenInputIsWithinRange() {
		Stream.of(-1, 0, 1).forEach(i -> {
			checkInputRange = new CheckInputRange(new Input(i));
			boolean result = checkInputRange.validate(game);

			assertTrue("Valid when number is within range.", result);
			assertThat("Set msg-s when number is within range.",
					checkInputRange.getValidationMsg(), empty());
		});

	}

}
