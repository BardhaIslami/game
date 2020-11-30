package com.assignment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.assignment.AppServer;
import com.assignment.app.lists.configuration.PropertiesConfiguration;
import com.assignment.server.Client;
import com.assignment.server.Sockets;

import static org.hamcrest.Matchers.matchesPattern;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.Assert.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

@FixMethodOrder(MethodSorters.JVM)
public class AcceptanceTest {
	
	 static {
	        PropertiesConfiguration.init("application.properties");
	    }
	    private static final String IP = PropertiesConfiguration.getProperties().getProperty("com.assignment.server.ip");
	    private static final String PORT = PropertiesConfiguration.getProperties().getProperty("com.assignment.server.port");
	    private static final int TIMEOUT = 3000;

	    private ExecutorService executor;

	    private Client firstClient;
	    private Client secondClient;
	    private Sockets firstSocket;
	    private Sockets secondSocket;
	    
	    @Before
	    public void startApp() throws Exception {
	        executor = Executors.newSingleThreadExecutor();
	        executor.execute(() ->
	           AppServer.main(new String[]{}));
	        
	        Thread.sleep(200);

	        // start first Client test

	        firstClient = new Client();
	        firstSocket = firstClient.start(IP, Integer.parseInt(PORT));

	        Thread.sleep(100);

	        //start second Client test
	      
	        secondClient = new Client();
	        secondSocket = secondClient.start(IP, Integer.parseInt(PORT));
	        Thread.sleep(100);
	    }

	    @After
	    public void tearDown() throws Exception {
	        firstSocket.delayed("LEAVE_GAME");
	        secondSocket.delayed("LEAVE_GAME");

	        executor.shutdown();
	        executor.awaitTermination(500, MILLISECONDS); // Time for server to shutdown
	        executor.shutdownNow();

	        firstClient.stop();
	        secondClient.stop();
	    }

	    @Test(timeout = TIMEOUT)
	    public void newConnection() {
	        assertEquals("First player connected first.", "connected", firstSocket.nextLine());
	        assertEquals("Second Player connected first.", "connected", secondSocket.nextLine());
	    }

	    @Test(timeout = TIMEOUT)
	    public void invalidInstruction() {
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("some invalid instructions");

	        assertTrue("First Player has invalid instruction.", firstSocket.nextLine().startsWith("invalid instruction"));
	        assertTrue("Second Player does not receive anything.", secondSocket.empty());
	    }

	    @Test(timeout = TIMEOUT)
	    public void acceptsRealPlayers() {
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("ADD_PLAYER: first player");
	  
	        assertEquals("First player: first player is added.", "First player is added to game.", firstSocket.nextLine());
	        assertEquals("Second player: first player is added.", "First player is added to game.", secondSocket.nextLine());

	        secondSocket.delayed("ADD_PLAYER: second player");
	        assertEquals("First player: second player is added.", "Second player is added to game.", firstSocket.nextLine());
	        assertEquals("Second player: second player is added.", "Second player is added to game.", secondSocket.nextLine());
	    }

	    @Test(timeout = TIMEOUT)
	    public void acceptsComputerPlayers() {
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("ADD_AUTOMATIC_PLAYER");

	        assertThat("First player: First automatic player is added.", firstSocket.nextLine(),
	                matchesPattern("Automatic player is added to game."));
	        assertThat("Second player: First automatic player is added.", secondSocket.nextLine(),
	                matchesPattern("Automatic player is added to game."));

	        secondSocket.delayed("ADD_AUTOMATIC_PLAYER");

	        assertThat("First player: First automatic player is added.", firstSocket.nextLine(),
	                matchesPattern("Automatic player is added to game."));
	        assertThat("Second player: First automatic player is added.", secondSocket.nextLine(),
	                matchesPattern("Automatic player is added to game."));
	    }

	    @Test(timeout = TIMEOUT)
	    public void onePlayerPerStream() {

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("ADD_PLAYER: first player");

	        assertEquals("First player: cannot add other players.",
	                "ERROR: cannot add other players.", firstSocket.nextLine());
	        assertTrue("Second player: does not receive anything.", secondSocket.empty());

	        secondSocket.delayed("ADD_PLAYER: second player");

	        assertEquals("First player: second player is added.", "Second player is added to game.", firstSocket.nextLine());
	        assertEquals("Second player: second player is added.", "Second player is added to game.", secondSocket.nextLine());
	    }

	    @Test(timeout = TIMEOUT)
	    public void acceptsPlayersMoreThanConfig() {

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        firstSocket.delayed("ADD_AUTOMATIC_PLAYER");
	        firstSocket.clear();
	        secondSocket.clear();

	        secondSocket.delayed("ADD_PLAYER: first player");

	        assertThat("Second player: cannot add other players.", secondSocket.nextLine(),
	                matchesPattern("ERROR: cannot add other players"));
	        assertTrue("First player does not receive anything.", firstSocket.empty());
	    }

	    @Test(timeout = TIMEOUT)
	    public void canStartGameWithRealPlayers() {

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("START_GAME");

	        assertThat("First player: started game with number and next player.",
	                firstSocket.nextLine(),
	                matchesPattern("Starting number is 54. First player plays next."));
	        assertThat("Second Player: game started and first players' output number.",
	                secondSocket.nextLine(),
	                matchesPattern("Starting number is 54. First player plays next."));
	    }


	    @Test(timeout = TIMEOUT)
	    public void canNotStartsWithInvalidPlayers() throws InterruptedException {
 
	    	firstSocket.delayed("ADD_PLAYER: first player");
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("START_GAME");

	        assertEquals("First player should not start with invalid players.",
	            "ERROR: Starting game failed due to invalid players.", firstSocket.nextLine());
	        assertTrue("Second player does not receive anything.", secondSocket.empty());
	    }

	    @Test(timeout = TIMEOUT)
	    public void canNotPlaysWithInvalidPlayers() {

	    	firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("PLAY_GAME: 1");

	        assertEquals("Shouldn't play game that has invalid players.",
	                "ERROR: cannot play when players: [] and first player is next in turn", firstSocket.nextLine());
	        assertTrue("Second player does not receive anything.", secondSocket.empty());
	    }

	    @Test(timeout = TIMEOUT)
	    public void canNotPlayIfNotItsTurn() throws InterruptedException {

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.clear();
	        secondSocket.clear();

	        secondSocket.delayed("PLAY_GAME: 0");

	        assertTrue("First player does not receive anything.", firstSocket.empty());
	        assertThat("Second player cannot play when it's not in turn.",
	                secondSocket.nextLine(),
	                matchesPattern("ERROR: It's not your turn"));

	    }


	    @Test(timeout = TIMEOUT)
	    public void cannotPlayIfNotItsTurn() throws InterruptedException {

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.clear();
	        secondSocket.clear();

	        secondSocket.delayed("PLAY_GAME: 973");

	        assertTrue("First player does not receive anything.", firstSocket.empty());
	        assertThat("Second player should not play when it's not in turn.",
	                secondSocket.nextLine(),
	                matchesPattern("ERROR: It is not your turn to play."));

	    }

	    @Test(timeout = TIMEOUT)
	    public void canNotPlayIncorrectNumberIfInTurn(){

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("PLAY_GAME: 973");

	        assertEquals("First player cannot play with incorrect number.",
	                firstSocket.nextLine(), "ERROR: invalid number, you can only choose [-1, 0, 1]");
	        assertTrue("Second player does not receive anything.", secondSocket.empty());

	    }

	    @Test(timeout = TIMEOUT)
	    public void canPlayWithCorrectNumber(){

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        secondSocket.delayed("ADD_PLAYER: first player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("PLAY_GAME: 1");

	        assertThat("First player should get success message.",
	                firstSocket.nextLine(),
	                matchesPattern("First player's played 1. The result is: output 18."));
	        assertThat("Second player gets first player played successfully.",
	                secondSocket.nextLine(),
	                matchesPattern("First player's played 1. The result is: output 18."));
	    }


	    @Test(timeout = TIMEOUT)
	    public void canNotPlayWithIncorrectAddedNumber(){

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("PLAY_GAME: 0");

	        assertEquals("First player cannot play with incorrect input.",
	                "ERROR: cannot play due to invalid input, last output was 18 and your input was 0",
	                firstSocket.nextLine());
	        assertTrue("Second player does not receive anything.", secondSocket.empty());
	    }

	    @Test(timeout = TIMEOUT)
	    public void canNotPlayWIthIncorrectType(){

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("PLAY_GAME: not_a_number");

	        assertEquals("Cannot play with incorrect types.",
	                "ERROR: Input was a string: \"not_a_number\"",
	                 firstSocket.nextLine());
	        assertTrue("Second player does not receive anything.", secondSocket.empty());
	    }


	    @Test(timeout = TIMEOUT)
	    public void canRestartAlreadyStartedGame(){

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.delayed("PLAY_GAME:0");
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("START_GAME");

	        assertThat("First player should be able to restart an already started game.",
	                firstSocket.nextLine(),
	                matchesPattern("Game started with number 54. First player is next in turn."));
	        assertThat("Second player should receive same message",
	                secondSocket.nextLine(),
	                matchesPattern("Game started with number 54. First player is next in turn."));
	    }

	    @Test(timeout = TIMEOUT)
	    public void canGetCurrentGameStatus(){

	    	firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("GAME_STATUS");

	        assertEquals("First player should be able to get current game status.",
	                "Currently playing: [] and first player is next in turn. Result is: output null.",
	                firstSocket.nextLine());
	        assertTrue("Second player does not receive anything.", secondSocket.empty());

	        secondSocket.delayed("GAME_STATUS");

	        assertTrue("First player does not receive anything.", firstSocket.empty());
	        assertEquals("Second player gets current game status.",
	                "Currently playing: [] and first player is next in turn. Result is: output null.", secondSocket.nextLine());
	    }

	    @Test(timeout = TIMEOUT)
	    public void canGetCurrentGameStatusOfAlreadyStartedGame() throws InterruptedException {

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("GAME_STATUS");

	        assertEquals("First player should get current game status.",
	                firstSocket.nextLine(),
	                "Currently playing: [player first player, player second player] and first player is next in turn. Result is: output 54.");
	        assertTrue("Second player does not receive anything.", secondSocket.empty());

	    }

	    @Test(timeout = TIMEOUT)
	    public void canGetCurrentStatusAfterSuccessfulAttempt(){

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.delayed("PLAY_GAME:-1");
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("GAME_STATUS");

	        assertEquals("First player should get current game status after successful attempt.",
	                firstSocket.nextLine(),
	                "Currently playing: [player first player, player second player] and second player is next in turn. Result is: output 18.");
	        assertTrue("Second player does not receive anything.", secondSocket.empty());
	    }


	    @Test(timeout = TIMEOUT)
	  
	    public void canGetCurrentStatusAfterFailedlAttempt(){

	    	firstSocket.delayed("ADD_PLAYER: first player");
	        secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.delayed("PLAY_GAME:-1");
	        firstSocket.delayed("PLAY_GAME:898");
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("GAME_STATUS");

	        assertEquals("First player should get current game status after failed attempt.",
	                firstSocket.nextLine(),
	                "Currently playing: [player first player, player second player] and second player is next in turn. Result is: output 18.");
	        assertTrue("Player2 should not receive anything.", secondSocket.empty());
	    }

	    @Test(timeout = TIMEOUT)
	    public void canPlayUntilWins() {

	    	firstSocket.delayed("ADD_PLAYER: first player");
	    	secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.delayed("PLAY_GAME:-1");
	        secondSocket.delayed("PLAY_GAME: 0");
	        firstSocket.delayed("PLAY_GAME:-1");
	        firstSocket.clear();
	        secondSocket.clear();

	        secondSocket.delayed("PLAY_GAME: 1");

	        assertThat("Second player should play until they win.",
	                secondSocket.nextLine(),
	                matchesPattern("First player played 1. Result is: output is 1, wins true."));
	        assertThat("First player gets same message",
	                firstSocket.nextLine(),
	                matchesPattern("Second player played 1. Result is: output is, wins true."));
	    }

	    @Test(timeout = TIMEOUT)
	    public void canNotPlayAfterWins() {

	    	firstSocket.delayed("ADD_PLAYER: first player");
	    	secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.delayed("PLAY_GAME:-1");
	        secondSocket.delayed("PLAY_GAME:0");
	        firstSocket.delayed("PLAY_GAME:-1");
	        secondSocket.delayed("PLAY_GAME:1");
	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("PLAY_GAME:1");

	        assertThat("First player cannot play after they win.",
	                firstSocket.nextLine(),
	                matchesPattern("ERROR: cannot play game when: output is 1, wins true."));
	        assertTrue("Second player does not receive anything.", secondSocket.empty());
	    }


	    @Test(timeout = TIMEOUT)
	    public void canStartNewGameAfterWins() {

	    	firstSocket.delayed("ADD_PLAYER: first player");
	    	secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.delayed("PLAY_GAME:-1");
	        secondSocket.delayed("PLAY_GAME:0");
	        firstSocket.delayed("PLAY_GAME:-1");
	        secondSocket.delayed("PLAY_GAME:1");
	        firstSocket.delayed("PLAY_GAME:1");
	        firstSocket.clear();
	        secondSocket.clear();

	        secondSocket.delayed("START_GAME");

	        assertThat("First player receives new game started.",
	        		firstSocket.nextLine(),
	                matchesPattern("Game started with number 54. First player is next in turn."));
	        assertThat("Second player receives new game started.",
	        		secondSocket.nextLine(),
	                matchesPattern("Game started with number 54. First player is next in turn."));
	    }


	    @Test(timeout = TIMEOUT)
	    public void canPlayWithComputerWhenPlayerIsFirst() {

	    	firstSocket.delayed("ADD_PLAYER: first player");
	    	firstSocket.delayed("ADD_AUTOMATIC_PLAYER");
	    	firstSocket.clear();
	    	secondSocket.clear();

	    	firstSocket.delayed("START_GAME");

	        assertThat("First player receive game started by first player with output number.",
	                firstSocket.nextLine(),
	                matchesPattern("Game started with number 54. First player is next in turn."));
	        assertThat("Second player receive game started by first player with output number.",
	                secondSocket.nextLine(),
	                matchesPattern("Game started with number 54. First player is next in turn."));

	        firstSocket.delayed("PLAY_GAME:1");

	        assertThat("First player receives that first player played correct number.",
	        		firstSocket.nextLine(),
	                matchesPattern("First player played 1. Result is: output 18."));
	        assertThat("Second player receives that first player played correct number.",
	                secondSocket.nextLine(),
	                matchesPattern("First player played 1. Result is: output 18."));


	        assertThat("First player receives that first automatic player played correct number.",
	        		firstSocket.nextLine(),
	                matchesPattern("Computer player played 0. Result is: output 6."));
	        assertThat("Second player receives that first automatic player played correct number.",
	        		secondSocket.nextLine(),
	                matchesPattern("Second player receives that first automatic player played correct number."));
	    }

	    @Test(timeout = TIMEOUT)
	    public void realPlayerCanPlayWithComputer() throws InterruptedException {

	    	firstSocket.delayed("ADD_AUTOMATIC_PLAYER");
	    	secondSocket.delayed("ADD_PLAYER: second player");
	    	firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("START_GAME");

	        assertThat("First player receives game started by first player with output number.",
	                firstSocket.nextLine(),
	                matchesPattern("Game started with number 54. Automatic player is next in turn."));
	        assertThat("Second player receives game started by first player with output number.",
	        		secondSocket.nextLine(),
	                matchesPattern("Game started with number 54. Automatic player is next in turn."));

	        assertThat("First player receives that first automatic player played correct number.",
	        		firstSocket.nextLine(),
	                matchesPattern("Automatic player played -1. Result is: output 18."));
	        assertThat("Second player receives that first automatic player played correct number.",
	        		secondSocket.nextLine(),
	                matchesPattern("Automatic player played -1. Result is: output 18."));
	    }

	    @Test(timeout = TIMEOUT)
	    public void playGameWhenAutomaticVsAutomatic() {

	    	firstSocket.delayed("ADD_AUTOMATIC_PLAYER");
	    	secondSocket.delayed("ADD_AUTOMATIC_PLAYER");
	    	firstSocket.clear();
	    	secondSocket.clear();

	    	firstSocket.delayed("START_GAME");

	        assertThat("First player receives game started with number and next player.",
	        		firstSocket.nextLine(),
	                matchesPattern("Game started with number 54. Automatic player is next in turn."));
	        assertThat("Second player receives game started game by first player with output.",
	        		secondSocket.nextLine(),
	                matchesPattern("Game started with number 54. Automatic player is next in turn."));

	        assertThat("First player receives that first automatic player played correct number.",
	        		firstSocket.nextLine(),
	                matchesPattern("Automatic player played -1. Result is: output 18."));
	        assertThat("Second player receives that first automatic player played correct number.",
	        		secondSocket.nextLine(),
	                matchesPattern("Automatic player played -1. Result is: output 18."));

	        assertThat("First player receives that first automatic player played correct number.",
	        		firstSocket.nextLine(),
	                matchesPattern("Automatic player played 0. Result is: output 6."));
	        assertThat("Second player receives that first automatic player played correct number.",
	        		secondSocket.nextLine(),
	                matchesPattern("Automatic player played 0. Result is: output 6."));

	        assertThat("First player receives that first automatic player played correct number.",
	        		firstSocket.nextLine(),
	                matchesPattern("Automatic player played -1. Result is: output 2."));
	        assertThat("Second player receives that first automatic player played correct number.",
	        		secondSocket.nextLine(),
	                matchesPattern("Automatic player played -1. Result is: output 2."));

	        assertThat("First player receives that first automatic player played correct number.",
	        		firstSocket.nextLine(),
	                matchesPattern("Automatic player played 1. Result is: output is 1, wins true."));
	        assertThat("Second player receives that first automatic player played correct number.",
	        		secondSocket.nextLine(),
	                matchesPattern("Automatic player played 1. Result is: output is 1, wins true."));
	    }


	    @Test(timeout = TIMEOUT)
	    public void playerLeftGame() {

	    	secondSocket.delayed("ADD_PLAYER: second player");
	    	firstSocket.clear();
	        secondSocket.clear();

	        secondSocket.delayed("LEAVE_GAME");

	        assertEquals("Second player receives: Left game.",
	                "Left Game", secondSocket.nextLine());
	        assertTrue("First player does not receive anything.", firstSocket.empty());
	    }

	    @Test(timeout = TIMEOUT)
	    public void whenPlayerLeavesIsRemovedFromPlayersInfo() {

	    	firstSocket.delayed("ADD_PLAYER: first player");
	    	secondSocket.delayed("ADD_PLAYER: second player");
	    	firstSocket.delayed("START_GAME");
	    	firstSocket.delayed("PLAY_GAME:-1");
	        secondSocket.delayed("LEAVE_GAME");
	        firstSocket.clear();

	        firstSocket.delayed("GAME_STATUS");

	        assertEquals("Second player is removed.",
	                "Currently playing: [player first player] and second player is next in turn. Result is: output null.",
	                firstSocket.nextLine());
	    }

	    @Test(timeout = TIMEOUT)
	    public void scanNotPlayWhenOtherPlayerLeft() {

	    	firstSocket.delayed("ADD_PLAYER: first player");
	    	secondSocket.delayed("ADD_PLAYER: second player");
	        firstSocket.delayed("START_GAME");
	        firstSocket.delayed("PLAY_GAME:-1");
	        secondSocket.delayed("PLAY_GAME:0");
	        secondSocket.delayed("LEAVE_GAME");

	        firstSocket.clear();
	        secondSocket.clear();

	        firstSocket.delayed("PLAY_GAME:-1");
	        firstSocket.delayed("PLAY_GAME:1");
	        firstSocket.delayed("PLAY_GAME:1");

	        assertEquals("First player cannot play alone.",
	        		firstSocket.nextLine(),
	                "ERROR: cannot play game when: [player first player] and first player is next in turn.");
	    }

}
