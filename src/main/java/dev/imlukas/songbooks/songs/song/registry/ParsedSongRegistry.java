package dev.imlukas.songbooks.songs.song.registry;

import dev.imlukas.songbooks.songs.category.SongCategory;
import dev.imlukas.songbooks.songs.instrument.SongInstrument;
import dev.imlukas.songbooks.songs.song.ParsedSong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsedSongRegistry {

    private final Map<String, ParsedSong> songs = new HashMap<>();

    public void register(ParsedSong song) {
        songs.put(song.getIdentifier(), song);
    }

    public List<ParsedSong> getByInstrument(SongInstrument instrument) {
        return getByCategory(instrument.getAssociatedCategory());
    }

    public List<ParsedSong> getByCategory(SongCategory category) {
        List<ParsedSong> songs = new ArrayList<>();
        String categoryIdentifier = category.getIdentifier();

        for (ParsedSong song : this.songs.values()) {
            SongCategory songCategory = song.getCategory();
            String songCategoryIdentifier = songCategory.getIdentifier();

            if (!categoryIdentifier.equals(songCategoryIdentifier)) {
                continue;
            }

            songs.add(song);
        }
        return songs;
    }

    public ParsedSong get(String identifier) {
        return songs.get(identifier);
    }

    public void unregister(String identifier) {
        songs.remove(identifier);
    }

    public List<String> getSongNames() {
        return new ArrayList<>(songs.keySet());
    }
}
