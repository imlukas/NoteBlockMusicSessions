package dev.imlukas.songbooks.song.registry;

import dev.imlukas.songbooks.song.ParsedSong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsedSongRegistry {

    private final Map<String, ParsedSong> songs = new HashMap<>();

    public void register(ParsedSong song) {
        songs.put(song.getIdentifier(), song);
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
