package dev.imlukas.songbooks.util.commands.context.arg.external;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import dev.imlukas.songbooks.songs.song.registry.ParsedSongRegistry;
import dev.imlukas.songbooks.util.commands.context.CommandContext;
import dev.imlukas.songbooks.util.commands.context.arg.AbstractArgument;

import java.util.List;

public class SongArgument extends AbstractArgument<ParsedSong> {

    private final ParsedSongRegistry parsedSongRegistry;

    protected SongArgument(SongBooksPlugin plugin, String name) {
        super(name);
        this.parsedSongRegistry = plugin.getParsedSongRegistry();
    }

    public static SongArgument create(SongBooksPlugin plugin, String name) {
        return new SongArgument(plugin, name);
    }

    @Override
    public ParsedSong parse(CommandContext context) {
        return parsedSongRegistry.get(context.getLastInput());
    }

    @Override
    public List<String> tabComplete(CommandContext context) {
        return parsedSongRegistry.getSongNames();
    }
}
