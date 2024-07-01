package dev.imlukas.songbooks.session.impl;

import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import dev.imlukas.songbooks.session.MusicSession;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SinglePlayerMusicSession implements MusicSession {

    private final UUID origin;
    private final ParsedSong song;
    private final RadioSongPlayer songPlayer;

    public SinglePlayerMusicSession(Player origin, ParsedSong song) {
        this.origin = origin.getUniqueId();
        this.song = song;
        songPlayer = new RadioSongPlayer(song.getSong());
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
    public RadioSongPlayer getSongPlayer() {
        return songPlayer;
    }

    @Override
    public void startSession() {
        songPlayer.addPlayer(origin);
        resumeSession();
    }

    @Override
    public void resumeSession() {
        songPlayer.setPlaying(true);
    }

    @Override
    public void pauseSession() {
        songPlayer.setPlaying(false);
    }

    @Override
    public void endSession() {
        songPlayer.setPlaying(false);
    }

}
