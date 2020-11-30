package com.assignment.app.maingame.gamemoves;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.assignment.app.maingame.gamemoves.activity.InputState;
import com.assignment.app.maingame.gamemoves.playinglogic.validation.CheckDivider;
import com.assignment.app.maingame.exceptions.GameMovesException;

public class CheckDividerTest {

	private CheckDivider checkDivider;
	private InputState inputState;

	@Before
	public void setUp() throws Exception {
		checkDivider = new CheckDivider();
		inputState = mock(InputState.class);
	}
	
	@Test
	public void invalidNotDivisibleByThree() {
		when(inputState.getValue()).thenReturn(7);
		when(inputState.isHigherOrEqualThanBoundary(2)).thenReturn(true);
		when(inputState.toString()).thenReturn("7");

		boolean result = checkDivider.validate(inputState);

		assertFalse("Invalid not divisible by 3 input.", result);
		assertThat("Set msg-s when invalid input.",
				checkDivider.getValidationMsg(),
				Matchers.containsInAnyOrder("could not play, invalid input 7"));
	}

	@Test
	public void invalidLessThanBoundaryInput() {
		when(inputState.getValue()).thenReturn(1);
		when(inputState.isHigherOrEqualThanBoundary(2)).thenReturn(false);
		when(inputState.toString()).thenReturn("1");

		boolean result = checkDivider.validate(inputState);

		assertFalse("Invalid below low boundary input.", result);
		assertThat("Set msg-s when below low boundary input.",
				checkDivider.getValidationMsg(),
				Matchers.containsInAnyOrder("could not play, invalid input 1"));
	}

	@Test(expected = GameMovesException.class)
	public void throwsWhenInvalidNotDivisibleByThree() {
		when(inputState.getValue()).thenReturn(7);
		when(inputState.isHigherOrEqualThanBoundary(2)).thenReturn(true);

		checkDivider.validOrNot(inputState);

		fail("Throws ConfirmationException.");
	}
	
	@Test(expected = GameMovesException.class)
	public void throwsWhenInvalidLessThanBoundaryInput() {
		when(inputState.getValue()).thenReturn(9);
		when(inputState.isHigherOrEqualThanBoundary(2)).thenReturn(false);

		checkDivider.validOrNot(inputState);

		fail("Throws ConfirmationException.");
	}



}
