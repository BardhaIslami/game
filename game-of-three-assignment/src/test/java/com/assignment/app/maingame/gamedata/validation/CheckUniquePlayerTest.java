package com.assignment.app.maingame.gamedata.validation;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.gamedata.activity.PlayerInfo;
import com.assignment.app.maingame.model.PlayerID;
import com.assignment.app.maingame.model.RealPlayer;
import com.assignment.app.maingame.validation.CheckUniquePlayer;
import com.assignment.app.maingame.exceptions.ConfirmationException;

public class CheckUniquePlayerTest {
	
	private CheckUniquePlayer checkUniquePlayer;

    private Game game;
    private PlayerID player;
    private PlayerInfo playerInfo;

    @Before
    public void setUp() throws Exception {
        player = new RealPlayer("idA", "nameA");

        game = mock(Game.class);

        playerInfo = mock(PlayerInfo.class);
        when(game.getPlayerInfo()).thenReturn(playerInfo);

        checkUniquePlayer = new CheckUniquePlayer(player);
    }

    @Test
    public void invalidSamePlayers() {
        when(playerInfo.hasPlayer(player)).thenReturn(true);

        boolean result = checkUniquePlayer.validate(game);

        assertFalse("Invalid when game already has the new player.", result);
        assertThat("Set msg-s game already has the new player.",
        		checkUniquePlayer.getValidationMsg(),
                containsInAnyOrder(CheckUniquePlayer.INVALID));
    }

    @Test
    public void validUniquePlayers() {
        when(playerInfo.hasPlayer(player)).thenReturn(false);

        boolean result = checkUniquePlayer.validate(game);

        assertTrue("Valid when game player is unique.", result);
        assertThat("Do not set msg-s when player is unique.",
        		checkUniquePlayer.getValidationMsg(), empty());
    }

    @Test(expected = ConfirmationException.class)
    public void throwsWhenPlayerIsNotUnique() {
        when(playerInfo.hasPlayer(player)).thenReturn(true);

        checkUniquePlayer.validOrNot(game);

        fail("Throws ConfirmationException.");
    }

}
