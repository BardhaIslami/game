package com.assignment;

import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;

import com.assignment.app.lists.configuration.PropertiesConfiguration;
import com.assignment.app.maingame.gamedata.Game;
import com.assignment.app.maingame.gamedata.GameBuilder;
import com.assignment.app.maingame.gamedata.Service;
import com.assignment.app.maingame.gamemoves.playinglogic.DividerLogic;
import com.assignment.app.maingame.gamemoves.playinglogic.GameMovesLogic;
import com.assignment.app.maingame.gamemoves.playinglogic.Win;
import com.assignment.app.maingame.gamemoves.playinglogic.WinningLogicInterface;
import com.assignment.app.maingame.gamemoves.playinglogic.validation.CheckDivider;
import com.assignment.server.ActiveSockets;
import com.assignment.server.Server;

import org.slf4j.Logger;

public class AppServer {
	static {
		initConfig();
	}

	private static final String PORT = PropertiesConfiguration.getProperties()
			.getProperty("com.assignment.server.port");
	private static final String LISTENER = PropertiesConfiguration.getProperties()
			.getProperty("com.assignment.server.listener");

	private static final Logger LOGGER = LoggerFactory.getLogger(AppServer.class);

	public static void main(String[] args) {
		
		LOGGER.info("Start the application");

		try (Server server = new Server(Integer.parseInt(PORT))) {
			ServerSocket socket = server.start();

			listeningThreads(socket);
		}

		LOGGER.info("App closed.");
	}

	// Runs application in different threads

	private static void listeningThreads(ServerSocket socket) {
		int count = Integer.parseInt(LISTENER);
		ExecutorService executor = new ThreadPoolExecutor(count, count, 0, TimeUnit.SECONDS,
				new LinkedBlockingDeque<>());

		// Creates the game

		Service service = new Service(createGame());

		ActiveSockets activeSockets = new ActiveSockets();

		ThreadLocal<Service> game = InheritableThreadLocal.withInitial(() -> service);
		ThreadLocal<ActiveSockets> otherActiveSockets = InheritableThreadLocal.withInitial(() -> activeSockets);

		for (int i = 0; i < count; i++) {
			executor.execute(new ListeningServer(socket, otherActiveSockets, game));
		}
		executor.shutdown();

		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException ex) {
			throw new RuntimeException("Exception in main thread.", ex);
		}
	}

	// Builds a new game that is shared in all threads.

	private static Game createGame() {
		GameMovesLogic movesLogic = new DividerLogic();
		movesLogic.addValidator(new CheckDivider());

		WinningLogicInterface wins = new Win();

		return new GameBuilder(movesLogic, wins).newGame();
	}

	// Initializes configurations

	private static void initConfig() {
		PropertyConfigurator
				.configure(PropertyConfigurator.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertiesConfiguration.init("application.properties");
	}
}