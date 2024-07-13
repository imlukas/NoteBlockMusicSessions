package dev.imlukas.songbooks.session.tracker;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.session.tracker.trackers.GuestMusicSessionTracker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class GuestTrackingTask implements Runnable {

    private final SongBooksPlugin plugin;
    private final GuestMusicSessionTracker tracker;
    private BukkitTask task;

    public GuestTrackingTask(SongBooksPlugin plugin) {
        this.plugin = plugin;
        this.tracker = plugin.getGuestMusicSessionTracker();
    }

    @Override
    public void run() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            tracker.updateSessions(onlinePlayer);
        }
    }

    public void start() {
        this.task = Bukkit.getScheduler().runTaskTimer(plugin, this, 3, 7);
    }

    public void stop() {
        task.cancel();
    }
}
