package dev.imlukas.songbooks.songbook.registry;

import dev.imlukas.songbooks.songbook.SongBook;

import java.util.HashMap;
import java.util.Map;

public class SongBookRegistry {

    private final Map<String, SongBook> songBooks = new HashMap<>();

    public void register(SongBook songBook) {
        songBooks.put(songBook.getIdentifier(), songBook);
    }

    public void unregister(SongBook songBook) {
        songBooks.remove(songBook.getIdentifier());
    }

    public SongBook getSongBook(String id) {
        return songBooks.get(id);
    }

    public Map<String, SongBook> getSongBooks() {
        return songBooks;
    }
}
