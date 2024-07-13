package dev.imlukas.songbooks.session.tracker;

import dev.imlukas.songbooks.session.MusicSession;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import org.bukkit.entity.Player;

public interface MusicSessionTracker {

    void addSession(Player player, MusicSession session);

    void removeSession(Player player);

    boolean hasSession(Player player);

    boolean hasSession(Player player, ParsedSong song);
}
