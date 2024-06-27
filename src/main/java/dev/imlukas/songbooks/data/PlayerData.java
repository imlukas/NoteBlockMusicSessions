package dev.imlukas.songbooks.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerData {

    private final UUID playerId;
    private boolean shouldSync = true; // if plugin should try to sync this player's session with other players

    public PlayerData(Player player) {
        this.playerId = player.getUniqueId();
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerId);
    }

    public boolean shouldSync() {
        return shouldSync;
    }
}
