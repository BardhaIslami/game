package com.assignment.app.appcontroller.handlingexceptions;

import com.assignment.app.maingame.model.PlayerID;

public interface HandlingExceptions <E extends RuntimeException> {
	
	// Handle current player runtime exception
	 
	    void handle(E ex, PlayerID player);
	}


