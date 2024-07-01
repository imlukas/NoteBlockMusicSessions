package dev.imlukas.songbooks.songs.song;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import dev.imlukas.songbooks.songs.category.SongCategory;
import lombok.Getter;

@Getter
public class ParsedSong {

    private final String identifier;
    private final String title;
    private final SongCategory category;
    private final Song song;


    public ParsedSong(String identifier, SongCategory category, Song song) {
        this.identifier = identifier;
        this.title = song.getTitle();
        this.category = category;
        this.song = song;
    }
}
