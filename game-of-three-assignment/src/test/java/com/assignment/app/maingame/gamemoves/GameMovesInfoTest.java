package com.assignment.app.maingame.gamemoves;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.assignment.app.maingame.gamedata.activity.Input;
import com.assignment.app.maingame.gamedata.activity.Output;
import com.assignment.app.maingame.gamemoves.activity.InputState;
import com.assignment.app.maingame.gamemoves.activity.ResultState;
import com.assignment.app.maingame.gamemoves.playinglogic.DividerLogic;
import com.assignment.app.maingame.gamemoves.playinglogic.Win;
import com.assignment.app.maingame.gamemoves.playinglogic.validation.CheckDivider;
import com.assignment.app.maingame.exceptions.GameMovesException;

public class GameMovesInfoTest {

	private GameMovesInfo gameMovesInfo;

	@Before
	public void setUp() throws Exception {
		gameMovesInfo = new GameMovesInfo(new DividerLogic(), new Win());
	}

	@Test
	public void validInput() {
		InputState inputState = new InputState(new Input(-1), new Output(10));

		ResultState resultState = gameMovesInfo.play(inputState);

		assertEquals("GameMovesInfo with input -1 and 10 should return 3.", new Output(3),
				resultState.getOutputValue());
	}

	@Test
	public void validInput4() {
		InputState inputState = new InputState(new Input(2), new Output(7));

		ResultState resultState = gameMovesInfo.play(inputState);

		assertEquals("GameMovesInfo with input 2 and 7 should return 3.", new Output(3),
				resultState.getOutputValue());
	}

	@Test
	public void winGame() {
		InputState inputState = new InputState(new Input(1), new Output(2));

		ResultState resultState = gameMovesInfo.play(inputState);

		assertTrue("GameMovesInfo with input 1 and 2 should win.",
				resultState.isWinner());
		assertEquals("GameMovesInfo with winning input returns output 1", new Output(1),
				resultState.getOutputValue());
	}


	@Test(expected = GameMovesException.class)
	public void gameWithCustomDivideByThreeValidator_whenPlayGameWithANumberNotDividableBy3() {
		DividerLogic dividerLogic = new DividerLogic();
		dividerLogic.addValidator(new CheckDivider());
		gameMovesInfo = new GameMovesInfo(dividerLogic, new Win());
		InputState inputState = new InputState(new Input(-1), new Output(8));

		gameMovesInfo.play(inputState);

		fail("Throws exception when invalid input.");
	}

}
