package dev.imlukas.songbooks.session.task;

import dev.imlukas.songbooks.SongBooksPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class OriginParticleTask implements Runnable {

    private static final Particle NOTE_PARTICLE = Particle.NOTE;
    private final Player origin;
    private final BukkitTask task;
    private boolean paused = true;

    public OriginParticleTask(SongBooksPlugin plugin, Player origin) {
        this.origin = origin;
        task = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 3);


    }

    public void resume() {
        paused = false;
    }

    public void pause() {
        paused = true;
    }

    public void cancel() {
        task.cancel();
    }

    @Override
    public void run() {
        if (paused) {
            return;
        }

        Location playerLocation = origin.getLocation();
        playerLocation.getWorld().spawnParticle(NOTE_PARTICLE, playerLocation.add(1, 1, 1), 3, 0, 0, 0, 0);
    }
}
