package dev.imlukas.songbooks.song;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class ParsedSong {

    public String identifier;
    public String title;
    public Song song;

    public ParsedSong(String identifier, Song song) {
        this.identifier = identifier;
        this.title = song.getTitle();
        this.song = song;
    }
}
