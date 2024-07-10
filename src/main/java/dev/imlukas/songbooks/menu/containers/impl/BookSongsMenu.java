package dev.imlukas.songbooks.menu.containers.impl;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.menu.containers.SongContainerMenu;
import dev.imlukas.songbooks.songbook.SongBook;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import org.bukkit.entity.Player;

import java.util.List;

public class BookSongsMenu extends SongContainerMenu {

    private final SongBook book;

    public BookSongsMenu(SongBooksPlugin plugin, Player viewer, SongBook book) {
        super(plugin, viewer);
        this.book = book;
        setup();
    }

    @Override
    public String getIdentifier() {
        return "book-songs";
    }

    @Override
    public List<ParsedSong> parseSongs() {
        return book.getSongList();
    }
}
