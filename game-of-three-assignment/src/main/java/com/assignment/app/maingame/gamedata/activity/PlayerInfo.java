package com.assignment.app.maingame.gamedata.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assignment.app.lists.configuration.PropertiesConfiguration;
import com.assignment.app.maingame.model.PlayerID;
import com.assignment.app.maingame.model.RealPlayer;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerInfo implements Iterator<PlayerInfo> {
	
    private static final int ROOT = Integer.parseInt(PropertiesConfiguration.getProperties()
            .getProperty("com.assignment.app.game.first_player_index", "0"));

    private static final int COUNT = Integer.parseInt(PropertiesConfiguration.getProperties()
            .getProperty("com.assignment.app.game.player_count"));

    private static final Logger THE_LOGGER = LoggerFactory.getLogger(PlayerInfo.class);

    public static final PlayerInfo NULL = new PlayerInfo(Collections.emptyList(), ROOT);

    private final List<PlayerID> playersList;
    private final int root;
    
    /* Works like an iterator by providing the same players list 
     * and sets the next root player in a circular manner 
     */

    public PlayerInfo(List<PlayerID> playersList, int root) {
        this.playersList = Collections.unmodifiableList(playersList);
        this.root = root;
    }

    
    // Get root player
     
    public PlayerID getRootPlayer() {
        return Optional.of(root)
                .filter(this::isValidRoot)
                .map(playersList::get)
                .orElse(RealPlayer.NOT_DEFINED);
    }

    /* Adds a new player.
     * Returns a copy of current player information and adds it to the new player.
     */

    public PlayerInfo add(final PlayerID player) {
        List<PlayerID> List = new ArrayList<>(playersList);
        List.add(player);
        return new PlayerInfo(Collections.unmodifiableList(List), root);
    }

    public PlayerInfo remove(PlayerID player) {
        List<PlayerID> List = playersList.stream()
                .filter(p -> !p.isSamePlayer(player))
                .collect(Collectors.toList());
        return new PlayerInfo (Collections.unmodifiableList(List), root);
    }

    // Get the root of the next player
 
    public PlayerInfo next() {
        return new PlayerInfo(playersList, getNextRoot());
    }

    // Iterate in a circular manner 
    
    @Override
    public boolean hasNext() {
        return true;
    }

    // Check if root is valid
 
    public boolean isValid() {
        return playersList.size() == COUNT &&
                isValidRoot(root);
    }

    // Check if there is place for other player
   
    public boolean acceptsOtherPlayers() {
        return playersList.size() < COUNT;
    }

    // Check if the player already exists 

    public boolean hasPlayer(PlayerID player) {
        return playersList.stream()
                .anyMatch(p -> p.isSamePlayer(player));
    }

    // Count the index of the next root 

    private int getNextRoot() {
        try {
            return (root + 1) % playersList.size();
        } catch(ArithmeticException e) {
            THE_LOGGER.error("Can't be divided by zero.");
        }
        return -1;
    }

    private boolean isValidRoot(int indexNumber) {
        return indexNumber >= 0 &&
               indexNumber < playersList.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PlayerInfo that = (PlayerInfo) obj;
        return root == that.root &&
                Objects.equals(playersList, that.playersList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playersList, root);
    }

    @Override
    public String toString() {
        return new StringBuilder("Players: ")
                .append(playersList)
                .append(" and ")
                .append(root+1)
                .append(" has the next turn.")
                .toString();
    }

}
