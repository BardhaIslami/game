package com.assignment.app.maingame.gamedata.validation;

import java.util.Arrays;
import org.junit.Test;

import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.gamedata.activity.PlayerInfo;
import com.assignment.app.maingame.model.PlayerID;
import com.assignment.app.maingame.model.RealPlayer;
import com.assignment.app.maingame.validation.CheckCurrentPlayer;
import com.assignment.app.maingame.exceptions.NotYourTurnException;

import org.junit.Before;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;


public class CheckCurrentPlayerTest {
	

    private CheckCurrentPlayer checkCurrentPlayer;
    private PlayerID playerId;
    private Game game;

    @Before
    public void setUp() throws Exception {
    	checkCurrentPlayer = mock(CheckCurrentPlayer.class);
        playerId = new RealPlayer("firstId", "name");
        game = mock(Game.class);

        checkCurrentPlayer = new CheckCurrentPlayer(playerId);
    }

    @Test
    public void validSameId() {
        PlayerInfo playerInfo = new PlayerInfo(
                Arrays.asList(new RealPlayer("id1", "p1"), new RealPlayer("other idB", "pB")), 0);
        when(game.getPlayerInfo()).thenReturn(playerInfo);

        boolean result = checkCurrentPlayer.validate(game);

        assertTrue("Valid player when has same id.", result);
        assertThat("Do not set msgs-s when same id",
        		checkCurrentPlayer.getValidationMsg(), empty());
    }
    
    @Test
    public void invalidDifferentId() {
        PlayerInfo playerInfo = new PlayerInfo(Arrays.asList(new RealPlayer("other idA", "pA"), new RealPlayer("other idB", "pB")), 0);
        when(game.getPlayerInfo()).thenReturn(playerInfo);

        boolean result = checkCurrentPlayer.validate(game);

        assertFalse("Invalid player has different id with player next in turn.", result);
        assertThat("Set msg-s when player has different id with player next in turn.",
        		checkCurrentPlayer.getValidationMsg(),
                containsInAnyOrder(CheckCurrentPlayer.INVALID));
    }

    @Test(expected = NotYourTurnException.class)
    public void throwWhenSameIdNotInTurn() {
        PlayerInfo playerInfo = new PlayerInfo(
                Arrays.asList(new RealPlayer("other idB", "pB"), new RealPlayer("firstId", "pA")), 0);
        when(game.getPlayerInfo()).thenReturn(playerInfo);

        checkCurrentPlayer.validOrNot(game);

        fail("Throws ConfirmationException.");
    }

}
