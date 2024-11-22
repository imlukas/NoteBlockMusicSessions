package dev.imlukas.songbooks.session.task;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.session.MusicSession;
import dev.imlukas.songbooks.session.tracker.trackers.OwnMusicSessionTracker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class OriginParticleTask implements Runnable {

    private static final Particle NOTE_PARTICLE = Particle.NOTE;
    private final Player origin;
    private final BukkitTask task;
    private final MusicSession musicSession;
    private boolean paused = true;

    public OriginParticleTask(SongBooksPlugin plugin, Player origin, MusicSession musicSession) {
        this.origin = origin;
        task = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 10);
        this.musicSession = musicSession;
    }

    public void resume() {
        paused = false;
    }

    public void pause() {
        paused = true;
    }

    public void cancel() {
        pause();
        task.cancel();
    }

    @Override
    public void run() {
        if (!musicSession.getSongPlayer().isPlaying()){
            cancel();
        }

        if (paused) {
            return;
        }

        Location playerLocation = origin.getLocation();
        playerLocation.getWorld().spawnParticle(NOTE_PARTICLE, playerLocation.add(new Vector(0, 2, 0)), 1, 0, 0, 0, 0);
    }
}
