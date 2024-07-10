package dev.imlukas.songbooks.menu.containers.impl;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.menu.containers.SongContainerMenu;
import dev.imlukas.songbooks.songs.category.SongCategory;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import dev.imlukas.songbooks.songs.song.registry.ParsedSongRegistry;
import org.bukkit.entity.Player;

import java.util.List;

public class CategorySongsMenu extends SongContainerMenu {

    private final ParsedSongRegistry parsedSongRegistry;
    private final SongCategory category;

    public CategorySongsMenu(SongBooksPlugin plugin, Player viewer, SongCategory category) {
        super(plugin, viewer);
        this.parsedSongRegistry = plugin.getParsedSongRegistry();
        this.category = category;
        setup();
    }

    @Override
    public String getIdentifier() {
        return "category-songs";
    }

    @Override
    public List<ParsedSong> parseSongs() {
        return parsedSongRegistry.getByCategory(category);
    }
}
