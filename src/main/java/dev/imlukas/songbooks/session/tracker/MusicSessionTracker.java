package dev.imlukas.songbooks.session.tracker;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.data.PlayerData;
import dev.imlukas.songbooks.data.tracker.PlayerDataRegistry;
import dev.imlukas.songbooks.session.MusicSession;
import dev.imlukas.songbooks.session.impl.MultiPlayerMusicSession;
import dev.imlukas.songbooks.session.impl.SinglePlayerMusicSession;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import dev.imlukas.songbooks.util.ListUtils;
import org.bukkit.entity.Player;

import java.util.*;

public class MusicSessionTracker {

    private final PlayerDataRegistry playerDataRegistry;
    private final Map<UUID, MusicSession> sessions = new HashMap<>();

    public MusicSessionTracker(SongBooksPlugin plugin) {
        this.playerDataRegistry = plugin.getPlayerDataRegistry();
    }

    public void addSession(Player player, MusicSession session) {
        removeSession(player);
        sessions.put(player.getUniqueId(), session);
        session.startSession();
    }

    public MusicSession getSession(Player player) {
        return sessions.get(player.getUniqueId());
    }

    public void removeSession(Player player) {
        MusicSession session = sessions.remove(player.getUniqueId());

        if (session == null) {
            return;
        }

        UUID originId = session.getOrigin().getUniqueId();

        if (originId.equals(player.getUniqueId())) {
            session.endSession();
            return;
        }

        session.removeListener(player);
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
     * Returns nearby (10 blocks radius) sessions that are playing the same song.
     * @param player The origin, player that is trying to create a session.
     * @param song The song that the player is trying to play.
     * @return A list of nearby sessions that are playing the same song.
     */
    private List<MusicSession> getNearbySessions(Player player, ParsedSong song) {
        Collection<Player> players = player.getWorld().getNearbyPlayers(player.getLocation(), 10);

        List<MusicSession> nearbySessions = new ArrayList<>();

        for (Player nearbyPlayer : players) {
            if (nearbyPlayer.equals(player)) {
                continue;
            }

            MusicSession session = getSession(nearbyPlayer);

            if (session == null) {
                continue;
            }

            ParsedSong sessionSong = session.getSong();

            if (!sessionSong.getIdentifier().equals(song.getIdentifier())) {
                continue;
            }

            nearbySessions.add(session);
        }

        return nearbySessions;
    }

    /**
     * Tries to create a session for the player with the specified song, checks for synchronization and handles it if necessary.
     * @param player The player that is trying to create a session.
     * @param parsedSong The song that the player is trying to play.
     */
    public void createSession(Player player, ParsedSong parsedSong) {
        if (hasSession(player, parsedSong)) {
            return;
        }

        removeSession(player);
        PlayerData playerData = playerDataRegistry.get(player);

        if (playerData == null) {
            return; // :(
        }

        boolean shouldSync = playerData.shouldSync();

        if (shouldSync) {
            boolean handled = handleSyncSessionCreation(player, parsedSong);

            if (!handled) {
                createMultiPlayerSession(player, parsedSong);
            }
            return;
        }

        createSinglePlayerSession(player, parsedSong);
    }

    private boolean handleSyncSessionCreation(Player player, ParsedSong song) {
        List<MusicSession> nearbySessions = getNearbySessions(player, song);

        if (nearbySessions.isEmpty()) {
            return false;
        }

        MusicSession session = ListUtils.getRandom(nearbySessions);
        session.addListener(player);
        return true;
    }

    private MusicSession createMultiPlayerSession(Player player, ParsedSong parsedSong) {
        MusicSession session = new MultiPlayerMusicSession(player, parsedSong);
        addSession(player, session);
        return session;
    }

    private MusicSession createSinglePlayerSession(Player player, ParsedSong parsedSong) {
        MusicSession session = new SinglePlayerMusicSession(player, parsedSong);
        addSession(player, session);
        return session;
    }
}
