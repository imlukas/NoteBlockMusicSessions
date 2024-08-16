package dev.imlukas.songbooks.session.impl;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MultiPlayerMusicSession extends SinglePlayerMusicSession {

    private final List<UUID> listeners = new ArrayList<>();

    public MultiPlayerMusicSession(SongBooksPlugin plugin, Player origin, ParsedSong song) {
        super(plugin, origin, song);
    }

    @Override
    public void startSession() {
        super.startSession();
        for (UUID listener : listeners) {
            getSongPlayer().addPlayer(listener);
        }
    }

    @Override
    public void endSession() {
        super.endSession();
        for (UUID listener : listeners) {
            getSongPlayer().removePlayer(listener);
        }
    }

    @Override
    public void addListener(Player player) {
        listeners.add(player.getUniqueId());
        getSongPlayer().addPlayer(player.getUniqueId());
    }

    @Override
    public void removeListener(Player player) {
        listeners.remove(player.getUniqueId());
        getSongPlayer().removePlayer(player.getUniqueId());
    }
}
