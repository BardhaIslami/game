package com.assignment.app.maingame.model;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ComputerPlayer implements PlayerID {
	
	public static final ComputerPlayer NOT_DEFINED = new ComputerPlayer("-", "not defined");
    private static final String AUTOMATIC_PLAYERS_NAME = "Machine";
    private static final AtomicInteger count = new AtomicInteger(1);

    private final String id;
    private final String name;

    public ComputerPlayer(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    // Generates new automatic player
    
    public static ComputerPlayer generate() {
        return new ComputerPlayer(UUID.randomUUID().toString(), AUTOMATIC_PLAYERS_NAME+count.incrementAndGet());
    }

    public String getPlayersId() {
        return id;
    }
    
    public String getPlayersName() {
        return name;
    }

    public boolean isSamePlayer(PlayerID playerId) {
        return this.getPlayersId().equals(playerId.getPlayersId());
    }

    @Override
    public boolean isMachinePlayer() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ComputerPlayer player = (ComputerPlayer) obj;
        return Objects.equals(id, player.id) &&
                Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Machine: ")
        .append(name);
        return sb.toString();
    }

}
