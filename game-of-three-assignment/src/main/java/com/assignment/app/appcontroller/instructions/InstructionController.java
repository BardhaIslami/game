package com.assignment.app.appcontroller.instructions;

import java.util.function.Consumer;
import java.util.stream.Stream;

import com.assignment.app.appcontroller.entities.UserInputData;
import com.assignment.app.appcontroller.entities.UserInputDetails;
import com.assignment.app.maingame.actions.GameDivider;
import com.assignment.app.maingame.gamedata.ServiceInterface;
import com.assignment.server.SocketInterface;

public class InstructionController implements Consumer<String> {

	private ServiceInterface service;
	private SocketInterface socket;
	private UserInputDetails userInputDetails;

	// Handles user input instructions or commands

	public InstructionController(ServiceInterface service, SocketInterface socket, UserInputDetails userInputDetails) {
		this.service = service;
		this.socket = socket;
		this.userInputDetails = userInputDetails;
	}

	// Execute user instructions

	@Override
	public void accept(String input) {
		Stream.of(input).map(userInputDetails).filter(UserInputData::isCorrect).forEach(this::executeInstruction);
	}

	private void executeInstruction(UserInputData userInputData) {
		InstructionType instructionType = InstructionType.valueOf(userInputData.getInstruction());

		switch (instructionType) {
		case ADD_PLAYER:
			new AddRealPlayer(service, socket).execute(userInputData.getInstructionData());
			break;
		case ADD_AUTOMATIC_PLAYER:
			new AddComputerPlayer(service, socket).execute(userInputData.getInstructionData());
			break;
		case START_GAME:
			StartGame startGame = new StartGame(service, socket);
			PlayWithComputer playWithComputer = new PlayWithComputer(service, socket, new GameDivider());
			startGame.setNext(playWithComputer);
			startGame.execute(userInputData.getInstructionData());
			break;
		case PLAY_GAME:
			PlayGame play = new PlayGame(service, socket);
			play.setNext(new PlayWithComputer(service, socket, new GameDivider()));
			play.execute(userInputData.getInstructionData());
			break;
		case GAME_STATUS:
			new GameStatus(service, socket).execute(userInputData.getInstructionData());
			break;
		case LEAVE_GAME:
			new LeaveGame(service, socket).execute(userInputData.getInstructionData());
			break;
		default:
			new InvalidInstructions(service, socket).execute(userInputData.getInstructionData());
		}
	}
}
