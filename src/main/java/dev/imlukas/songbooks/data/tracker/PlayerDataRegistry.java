package dev.imlukas.songbooks.data.tracker;

import dev.imlukas.songbooks.data.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataRegistry {

    private final Map<UUID, PlayerData> playerData = new HashMap<>();

    public PlayerData get(Player player) {
        return playerData.get(player.getUniqueId());
    }

    public void register(PlayerData data) {
        playerData.put(data.getPlayerId(), data);
    }

    public void unregister(Player player) {
        playerData.remove(player.getUniqueId());
    }
}
