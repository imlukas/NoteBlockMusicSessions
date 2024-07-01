package dev.imlukas.songbooks.song;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import lombok.Getter;

@Getter
public class ParsedSong {

    public final String identifier;
    public final String title;
    public final Song song;

    public ParsedSong(String identifier, Song song) {
        this.identifier = identifier;
        this.title = song.getTitle();
        this.song = song;
    }
}
