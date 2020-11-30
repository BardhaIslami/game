package com.assignment.app.maingame.model;

import java.util.Objects;

public class RealPlayer implements PlayerID {
	
	 public static final RealPlayer NOT_DEFINED = new RealPlayer("-", "not defined");
	    private final String id;
	    private final String name;

	    public RealPlayer(final String id, final String name) {
	        this.id = id;
	        this.name = name;
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
	        return false;
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        RealPlayer player = (RealPlayer) obj;
	        return Objects.equals(id, player.id) &&
	                Objects.equals(name, player.name);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id, name);
	    }

	    @Override
	    public String toString() {
	        final StringBuilder sb = new StringBuilder("Player: ")
	        .append(name);
	        return sb.toString();
	    }
}
