package com.assignment.app.maingame.model;

public interface PlayerID {
	
	// Identify the player
	
    String getPlayersId();

    // Get Players name
    
    String getPlayersName();

    // Check if players has same ID 
    
    boolean isSamePlayer(PlayerID playerId);

    // Check if player is automatic player
    
    boolean isMachinePlayer();

}
