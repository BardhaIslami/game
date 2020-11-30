package com.assignment.app.maingame.gamedata.validation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.Before;
import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.gamedata.activity.PlayerInfo;
import com.assignment.app.maingame.validation.CheckNewGame;
import com.assignment.app.maingame.exceptions.ConfirmationException;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;

public class CheckNewGameTest {

	private CheckNewGame checkNewGame;

	private Game game;
	private PlayerInfo playerInfo;

	@Before
	public void setUp() throws Exception {
		checkNewGame = new CheckNewGame();

		game = mock(Game.class);
		playerInfo = mock(PlayerInfo.class);
	}

	@Test
	public void validGame() {
		when(game.getPlayerInfo()).thenReturn(playerInfo);
		when(playerInfo.isValid()).thenReturn(true);

		boolean result = checkNewGame.validate(game);

		assertTrue("Valid game when playerInfo is valid.", result);
		assertThat("Do not set msgs when playerInfo is not valid.", checkNewGame.getValidationMsg(), empty());
	}

	@Test
	public void invalidGame() {
		when(game.getPlayerInfo()).thenReturn(playerInfo);
		when(playerInfo.isValid()).thenReturn(false);

		boolean result = checkNewGame.validate(game);

		assertFalse("Invalid game when playerInfo is not valid.", result);
		assertThat("Set msgs when playerInfo is not valid.", checkNewGame.getValidationMsg(),
				Matchers.containsInAnyOrder(CheckNewGame.INVALID_PLAYER));
	}

	@Test(expected = ConfirmationException.class)
	public void throwsInvalidGame() {
		when(game.getPlayerInfo()).thenReturn(playerInfo);
		when(playerInfo.isValid()).thenReturn(false);

		checkNewGame.validOrNot(game);

		fail("Throws ConfirmationException.");
	}

}
