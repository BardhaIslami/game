package com.assignment.app.maingame.gamedata.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.assignment.app.maingame.gamedata.activity.PlayerInfo;
import com.assignment.app.maingame.model.PlayerID;
import com.assignment.app.maingame.model.RealPlayer;

import java.util.Arrays;


public class PlayerInfoTest {
	
	 private PlayerInfo playerInfo;

	    @Before
	    public void setUp() throws Exception {
	    }

	    @Test
	    public void rootPlayer() {
	        PlayerID playerA = new RealPlayer("1", "pA");
	        playerInfo = new PlayerInfo(Arrays.asList(playerA, new RealPlayer("2", "pB")), 0);

	        assertEquals("Root player is player 1.", playerA, playerInfo.getRootPlayer());
	    }

	    @Test
	    public void getNext() {
	        PlayerID playerB = new RealPlayer("2", "pB");
	        playerInfo = new PlayerInfo(Arrays.asList(new RealPlayer("1", "pA"), playerB), 0);

	        assertEquals("When you get next playerInfo, root player is player B.", playerB, playerInfo.next().getRootPlayer());
	    }

	    @Test
	    public void notValidWithMorePlayers() {
	        playerInfo = new PlayerInfo(Arrays.asList(new RealPlayer("1", "pA"), new RealPlayer("1", "pB"), new RealPlayer("1", "pC")), 0);

	        assertFalse("Not valid with more players than allowed.", playerInfo.isValid());

	    }

	    @Test
	    public void notValidWithLessPlayers() {
	        playerInfo= new PlayerInfo(Arrays.asList(new RealPlayer("1", "pA")), 0);

	        assertFalse("Not valid with less players than allowed.", playerInfo.isValid());
	    }
}
