package dev.imlukas.songbooks.songs.song.parser;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import dev.imlukas.songbooks.util.file.io.IOUtils;
import dev.imlukas.songbooks.util.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SongParser {

    private final SongBooksPlugin plugin;

    public SongParser(SongBooksPlugin plugin) {
        this.plugin = plugin;
    }

    public List<ParsedSong> parseAll() {
        List<ParsedSong> parsedSongs = new ArrayList<>();

        IOUtils.traverseAndLoad(new File(plugin.getDataFolder(), "songs"), file -> {
            String fileName = TextUtils.removeFileExtension(file.getName());
            Song song = NBSDecoder.parse(file);

            if (song == null) {
                System.out.println("Failed to parse song: " + fileName);
                return;
            }

            ParsedSong parsedSong = new ParsedSong(fileName, song);
            parsedSongs.add(parsedSong);
            System.out.println("Parsed song: " + parsedSong.getIdentifier());
        });

        return parsedSongs;
    }
}
