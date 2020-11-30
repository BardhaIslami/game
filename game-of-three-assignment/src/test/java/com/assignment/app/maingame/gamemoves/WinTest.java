package com.assignment.app.maingame.gamemoves;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.assignment.app.maingame.gamedata.activity.Output;
import com.assignment.app.maingame.gamemoves.playinglogic.Win;
import com.assignment.app.maingame.gamemoves.playinglogic.WinningLogicInterface;

public class WinTest {

	private WinningLogicInterface win;

	@Before
	public void setUp() throws Exception {
		win = new Win();
	}

	@Test
	public void whenOneWinTrue() {
		assertTrue("Should Win when value is 1.", win.apply(new Output(1)));
	}

	@Test
	    public void doesNotWinWhenNotOne() {
	        assertFalse("Should not Win when value is NOT 1.", win.apply(new Output(5)));
    }
}
