package dev.imlukas.songbooks.data;

import dev.imlukas.songbooks.util.registry.GenericRegistry;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerDataRegistry extends GenericRegistry<UUID, PlayerData> {

    public void register(PlayerData playerData) {
        register(playerData.getPlayerId(), playerData);
    }

    public void unregister(Player player) {
        unregister(player.getUniqueId());
    }

    public PlayerData get(Player player) {
        return get(player.getUniqueId());
    }
}
