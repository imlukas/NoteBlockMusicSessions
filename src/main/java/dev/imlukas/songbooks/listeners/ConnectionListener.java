package dev.imlukas.songbooks.listeners;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.data.PlayerData;
import dev.imlukas.songbooks.data.PlayerDataRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    private final PlayerDataRegistry playerDataRegistry;

    public ConnectionListener(SongBooksPlugin plugin) {
        this.playerDataRegistry = plugin.getPlayerDataRegistry();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerDataRegistry.register(new PlayerData(player));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerDataRegistry.unregister(player);
    }
}
