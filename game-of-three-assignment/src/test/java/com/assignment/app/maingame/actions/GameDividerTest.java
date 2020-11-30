package com.assignment.app.maingame.actions;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.assignment.app.maingame.gamedata.activity.Input;
import com.assignment.app.maingame.gamedata.activity.Output;

public class GameDividerTest {

	private GameDivider gameDivider;

	@Before
	public void setUp() throws Exception {
		gameDivider = new GameDivider();
	}
	
	@Test
	public void shouldCalculateClosestAdditionForDividableByThreeWhen0() {
		Output outputNumber = new Output(0);

		Input result = gameDivider.calculateNextRoundInput(outputNumber);

		assertEquals("Closest value dividable by three for 0 is 0.", new Input(0), result);
	}

	@Test
	public void calculateAdditionForDividedByThreeWhen10() {
		Output outputNumber = new Output(10);

		Input result = gameDivider.calculateNextRoundInput(outputNumber);

		assertEquals("Closest input dividable by three for 10 is -1.", new Input(-1), result);
	}

	@Test
	public void calculateAdditionForDividedByThreeWhen11() {
		Output outputNumber = new Output(11);

		Input result = gameDivider.calculateNextRoundInput(outputNumber);

		assertEquals("Closest value dividable by three for 10 is 1.", new Input(1), result);
	}

}
