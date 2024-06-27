package dev.imlukas.songbooks.session;

import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import dev.imlukas.songbooks.session.impl.SinglePlayerMusicSession;
import dev.imlukas.songbooks.song.ParsedSong;
import org.bukkit.entity.Player;

public interface MusicSession {


    Player getOrigin();

    ParsedSong getSong();

    SongPlayer getSongPlayer();

    default void addListener(Player player) {}

    default boolean isListener(Player player) {
        return false;
    }

    default void removeListener(Player player) {}

    void startSession();

    void endSession();
}
