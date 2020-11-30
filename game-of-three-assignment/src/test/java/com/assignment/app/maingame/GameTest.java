package com.assignment.app.maingame;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.assignment.app.maingame.exceptions.GameException;
import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.gamedata.activity.Input;
import com.assignment.app.maingame.gamedata.activity.Output;
import com.assignment.app.maingame.gamedata.activity.PlayerInfo;
import com.assignment.app.maingame.gamemoves.GameMovesInfo;
import com.assignment.app.maingame.gamemoves.activity.InputState;
import com.assignment.app.maingame.gamemoves.activity.ResultState;
import com.assignment.app.maingame.model.RealPlayer;
import com.assignment.app.maingame.validation.CheckNewGame;

import java.util.stream.Stream;

public class GameTest {
	
	 private Game game;
	    private GameMovesInfo gameMovesInfoMock;

	    @Before
	    public void setUp() throws Exception {
	    	gameMovesInfoMock = mock(GameMovesInfo.class);
	    }


	    @Test
	    public void addPlayers() {
	        RealPlayer playerA = new RealPlayer("1", "playerA");

	        Game game = new Game(gameMovesInfoMock);

	        Game newGame = game.addPlayer(playerA);

	        assertEquals("Players should be added.", playerA, newGame.getPlayerInfo().getRootPlayer());
	    }


	    @Test
	    public void addMultiplePlayers() {
	        RealPlayer playerA = new RealPlayer("1", "playerA");
	        RealPlayer playerB = new RealPlayer("2", "playerB");
	        RealPlayer playerC = new RealPlayer("3", "playerC");

	        Game last = Stream.of(playerA, playerB, playerC)
	                .reduce(new Game(gameMovesInfoMock), Game::addPlayer, (a, b) -> null);

	        assertEquals("playerA added.", playerA, last.getPlayerInfo().getRootPlayer());
	        assertEquals("playerB added", playerB, last.getPlayerInfo().next().getRootPlayer());
	        assertEquals("playerC added", playerC, last.getPlayerInfo().next().next().getRootPlayer());
	    }

	    @Test
	    public void invalidateWhenGameStartsWithInvalidInfo() {
	        RealPlayer playerA = new RealPlayer("1", "playerA");

	        Game game = new Game(gameMovesInfoMock)
	                .addPlayer(playerA);

	        assertFalse("Game invalidates with CheckNewGame when game initialized with invalid playerInfo",
	                game.validate(new CheckNewGame()));
	    }

	    @Test
	    public void gameShouldValidate_withNewGameValidator_whenGameInitializedWithValidAggregate() {
	    	RealPlayer playerA = new RealPlayer("1", "playerA");
	    	RealPlayer playerB = new RealPlayer("2", "playerB");

	        Game game = new Game(gameMovesInfoMock)
	                .addPlayer(playerA)
	                .addPlayer(playerB);

	        assertTrue("Game should validate with NewGameValidator when game initialized with valid playerAggregate",
	                game.validate(new CheckNewGame()));
	    }

	    @Test
	    public void startGameAndGetOutput() {
	    	RealPlayer playerA = new RealPlayer("1", "playerA");
	    	RealPlayer playerB = new RealPlayer("1", "playerB");

	        Game game = new Game(gameMovesInfoMock)
	                .addPlayer(playerA)
	                .addPlayer(playerB)
	                .start();

	        assertEquals("New game should have a new round result with output.", new Output(64), game.getResult().getOutputValue());
	        assertEquals("New game should hold new player aggregate with next player.", playerA, game.getPlayerInfo().getRootPlayer());
	    }

	    @Test
	    public void playWithRealPlayers() {
	    	RealPlayer playerA = new RealPlayer("1", "playerA");
	    	RealPlayer playerB = new RealPlayer("1", "playerB");

	        Game game = new Game(gameMovesInfoMock)
	                .addPlayer(playerA)
	                .addPlayer(playerB);

	        PlayerInfo playerInfo = game.getPlayerInfo();
	        Output output = game.getResult().getOutputValue();
	        InputState inputState = new InputState(new Input(1), output);
	        ResultState resultState = mock(ResultState.class);
	        when(gameMovesInfoMock.play(inputState)).thenReturn(resultState);

	        Input inputNumber = new Input(1);
	        Game next = game.play(inputNumber);

	        assertEquals("New game has a new results.", next.getResult(), resultState);
	        assertEquals("New game holds new player info with next player.", playerInfo.next(), next.getPlayerInfo());
	    }

	    @Test(expected = GameException.class)
	    public void throwWhenStartPlayingWithInvalidValue() {
	        RealPlayer playerA = new RealPlayer("1", "playerA");
	        RealPlayer playerB = new RealPlayer("2", "playerB");
	        Input inputNumber = new Input(0);

	        when(gameMovesInfoMock.play(any())).thenThrow(GameException.class);

	        new Game(gameMovesInfoMock)
	                .addPlayer(playerA)
	                .addPlayer(playerB)
	                .play(inputNumber);

	        fail("Throws exception when playing initial invalid input.");
	    }

}
