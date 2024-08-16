package dev.imlukas.songbooks.session.impl;

import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.session.MusicSession;
import dev.imlukas.songbooks.session.task.OriginParticleTask;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SinglePlayerMusicSession implements MusicSession {

    private final UUID origin;
    private final ParsedSong song;
    private final EntitySongPlayer songPlayer;

    private final OriginParticleTask originParticleTask;

    public SinglePlayerMusicSession(SongBooksPlugin plugin, Player origin, ParsedSong song) {
        this.origin = origin.getUniqueId();
        this.song = song;
        songPlayer = new EntitySongPlayer(song.getSong());
        songPlayer.setEntity(origin);
        songPlayer.setAutoDestroy(true);
        songPlayer.setDistance(32);
        songPlayer.setVolume((byte) 100);

        originParticleTask = new OriginParticleTask(plugin, origin);
    }


    @Override
    public Player getOrigin() {
        return Bukkit.getPlayer(origin);
    }

    @Override
    public ParsedSong getSong() {
        return song;
    }

    @Override
    public EntitySongPlayer getSongPlayer() {
        return songPlayer;
    }

    @Override
    public void startSession() {
        originParticleTask.resume();
        songPlayer.addPlayer(origin);
        resumeSession();
    }

    @Override
    public void resumeSession() {
        songPlayer.setPlaying(true);
        originParticleTask.resume();
    }

    @Override
    public void pauseSession() {
        songPlayer.setPlaying(false);
        originParticleTask.pause();
    }

    @Override
    public void endSession() {
        songPlayer.destroy();
        originParticleTask.cancel();
    }

}
