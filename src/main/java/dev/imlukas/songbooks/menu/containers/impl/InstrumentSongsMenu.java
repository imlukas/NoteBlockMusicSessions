package dev.imlukas.songbooks.menu.containers.impl;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.menu.containers.SongContainerMenu;
import dev.imlukas.songbooks.songs.instrument.SongInstrument;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import dev.imlukas.songbooks.songs.song.registry.ParsedSongRegistry;
import org.bukkit.entity.Player;

import java.util.List;

public class InstrumentSongsMenu extends SongContainerMenu {

    private final ParsedSongRegistry parsedSongRegistry;
    private final SongInstrument instrument;

    public InstrumentSongsMenu(SongBooksPlugin plugin, Player viewer, SongInstrument instrument) {
        super(plugin, viewer);
        this.parsedSongRegistry = plugin.getParsedSongRegistry();
        this.instrument = instrument;
        setup();
    }

    @Override
    public String getIdentifier() {
        return "instrument-songs";
    }

    @Override
    public List<ParsedSong> parseSongs() {
        return parsedSongRegistry.getByInstrument(instrument);
    }
}
