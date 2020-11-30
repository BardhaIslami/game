package com.assignment.app.maingame.gamedata;

import java.util.concurrent.atomic.AtomicReference;

import com.assignment.app.maingame.gamedata.activity.Input;
import com.assignment.app.maingame.model.PlayerID;
import com.assignment.app.maingame.validation.CheckCanPlay;
import com.assignment.app.maingame.validation.CheckCurrentPlayer;
import com.assignment.app.maingame.validation.CheckInputRange;
import com.assignment.app.maingame.validation.CheckNewGame;
import com.assignment.app.maingame.validation.CheckOpenPlayer;
import com.assignment.app.maingame.validation.CheckUniquePlayer;
import com.assignment.app.maingame.exceptions.GameException;

public class Service implements ServiceInterface {

	private final AtomicReference<Game> game;

	public Service(Game game) {
		this.game = new AtomicReference<>(game);
	}

	// Add new player
	
	public void add(final PlayerID p) {
		new CheckUniquePlayer(p).validOrNot(game.get());
		new CheckOpenPlayer().validOrNot(game.get());
		game.set(game.get().addPlayer(p));
	}

	// Remove player
	
	public void remove(PlayerID p) {
		game.set(game.get().removePlayer(p));
	}

	// Start the game with already added players
	
	public void start() {
		game.get().validOrNot(new CheckNewGame());
		game.set(game.get().start());
	}
	
	// Restarts the game while saving the players
	
	public void stop() {
		game.set(game.get().stop());
	}

	// Input a number, validate it and start playing.
	
	public void play(final Input input, final PlayerID player) {
		game.get().validOrNot(new CheckCanPlay());
		game.get().validOrNot(new CheckCurrentPlayer(player));
		game.get().validOrNot(new CheckInputRange(input));

		Game newGame = game.get().play(input);
		game.set(newGame);
	}

	// Get game with its current state

	public Game getGameCurrentState() {
		return game.get();
	}

}
