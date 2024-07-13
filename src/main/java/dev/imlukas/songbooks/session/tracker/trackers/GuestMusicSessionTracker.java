package dev.imlukas.songbooks.session.tracker.trackers;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.data.PlayerData;
import dev.imlukas.songbooks.data.PlayerDataRegistry;
import dev.imlukas.songbooks.session.MusicSession;
import dev.imlukas.songbooks.session.tracker.MusicSessionTracker;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import org.bukkit.entity.Player;

import java.util.*;

public class GuestMusicSessionTracker implements MusicSessionTracker {

    private final PlayerDataRegistry playerDataRegistry;
    private final OwnMusicSessionTracker ownMusicSessionTracker;
    private final Map<UUID, List<MusicSession>> guestSessions = new HashMap<>();

    public GuestMusicSessionTracker(SongBooksPlugin plugin) {
        this.playerDataRegistry = plugin.getPlayerDataRegistry();
        this.ownMusicSessionTracker = plugin.getOwnMusicSessionTracker();
    }

    public void addSession(Player player, MusicSession session) {
        List<MusicSession> sessions = guestSessions.computeIfAbsent(player.getUniqueId(), key -> new ArrayList<>());
        sessions.add(session);
        session.addListener(player);
    }

    public List<MusicSession> getSessions(Player player) {
        return guestSessions.getOrDefault(player.getUniqueId(), new ArrayList<>());
    }


    public void removeSession(Player player) {
        List<MusicSession> guestSessions = this.guestSessions.remove(player.getUniqueId());

        if (guestSessions != null && !guestSessions.isEmpty()) {
            guestSessions.forEach(guestSession -> guestSession.removeListener(player));
        }
    }

    public boolean hasSession(Player player, ParsedSong song) {
        if (ownMusicSessionTracker.hasSession(player, song)) {
            return true;
        }

        List<MusicSession> guestSessions = getSessions(player);

        if (guestSessions.isEmpty()) {
            return false;
        }

        for (MusicSession guestSession : guestSessions) {
            ParsedSong guestSessionSong = guestSession.getSong();

            if (guestSessionSong.getIdentifier().equals(song.getIdentifier())) {
                return true;
            }
        }

        return false;
    }

    public boolean hasSession(Player player) {
        return !getSessions(player).isEmpty();
    }

    /**
     * Returns nearby (10 blocks radius) sessions that are playing the same song.
     *
     * @param player The origin, player that is trying to create a session.
     * @return A list of nearby sessions that are playing the same song.
     */
    private List<MusicSession> getNearbySessions(Player player) {
        Collection<Player> players = player.getWorld().getNearbyPlayers(player.getLocation(), 10);

        List<MusicSession> nearbySessions = new ArrayList<>();

        for (Player nearbyPlayer : players) {
            if (nearbyPlayer.equals(player)) {
                continue;
            }

            MusicSession session = ownMusicSessionTracker.getSession(nearbyPlayer);

            if (session == null) {
                continue;
            }

            nearbySessions.add(session);
        }

        return nearbySessions;
    }

    /**
     * Tries to create a session for the player with the specified song, checks for synchronization and handles it if necessary.
     *
     * @param player The player that is trying to create a session.
     */
    public void updateSessions(Player player) {
        PlayerData playerData = playerDataRegistry.get(player);

        if (playerData == null) {
            return;
        }

        boolean shouldSync = playerData.shouldSync();

        if (!shouldSync) {
            removeSession(player);
            return;
        }

        handleSyncSessionCreation(player);
    }

    private void handleSyncSessionCreation(Player player) {
        List<MusicSession> nearbySessions = getNearbySessions(player);

        if (nearbySessions.isEmpty()) {
            System.out.println("[Handle Sync] No nearby sessions found.");
            return;
        }

        nearbySessions.forEach(nearbySession -> {
            if (hasSession(player, nearbySession.getSong())) {
                return;
            }

            addSession(player, nearbySession);
        });
    }
}
