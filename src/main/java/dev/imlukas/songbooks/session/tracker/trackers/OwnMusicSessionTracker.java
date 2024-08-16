package dev.imlukas.songbooks.session.tracker.trackers;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.data.PlayerData;
import dev.imlukas.songbooks.data.PlayerDataRegistry;
import dev.imlukas.songbooks.session.MusicSession;
import dev.imlukas.songbooks.session.impl.MultiPlayerMusicSession;
import dev.imlukas.songbooks.session.impl.SinglePlayerMusicSession;
import dev.imlukas.songbooks.session.tracker.MusicSessionTracker;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OwnMusicSessionTracker implements MusicSessionTracker {

    private final SongBooksPlugin plugin;
    private final PlayerDataRegistry playerDataRegistry;
    private final Map<UUID, MusicSession> sessions = new HashMap<>();

    public OwnMusicSessionTracker(SongBooksPlugin plugin) {
        this.plugin = plugin;
        this.playerDataRegistry = plugin.getPlayerDataRegistry();
    }

    public void addSession(Player player, MusicSession session) {
        removeSession(player);
        this.sessions.put(player.getUniqueId(), session);
        session.startSession();
    }

    public MusicSession getSession(Player player) {
        return sessions.get(player.getUniqueId());
    }

    public void removeSession(Player player) {
        MusicSession session = this.sessions.remove(player.getUniqueId());

        if (session == null) {
            return;
        }

        session.endSession();
    }

    public boolean hasSession(Player player, ParsedSong song) {
        MusicSession session = getSession(player);

        if (session == null) {
            return false;
        }

        ParsedSong sessionSong = session.getSong();
        return sessionSong.getIdentifier().equals(song.getIdentifier());
    }

    public boolean hasSession(Player player) {
        return sessions.containsKey(player.getUniqueId());
    }

    /**
     * Tries to create a session for the player with the specified song, checks for synchronization and handles it if necessary.
     *
     * @param player     The player that is trying to create a session.
     * @param parsedSong The song that the player is trying to play.
     */
    public void createSession(Player player, ParsedSong parsedSong) {
        removeSession(player);
        PlayerData playerData = playerDataRegistry.get(player);

        if (playerData == null) {
            System.out.println("Player data not found.");
            return; // :(
        }

        boolean shouldSync = playerData.shouldSync();

        if (shouldSync) {
            System.out.println("Trying to sync session.");
            GuestMusicSessionTracker guestMusicSessionTracker = plugin.getGuestMusicSessionTracker();

            if (guestMusicSessionTracker.hasSession(player, parsedSong)) {
                return;
            }

            createMultiPlayerSession(player, parsedSong);
            return;
        }

        System.out.println("Creating single player session.");
        createSinglePlayerSession(player, parsedSong);
    }

    private void createMultiPlayerSession(Player player, ParsedSong parsedSong) {
        MusicSession session = new MultiPlayerMusicSession(plugin, player, parsedSong);
        addSession(player, session);
    }

    private void createSinglePlayerSession(Player player, ParsedSong parsedSong) {
        MusicSession session = new SinglePlayerMusicSession(plugin, player, parsedSong);
        addSession(player, session);
    }
}
