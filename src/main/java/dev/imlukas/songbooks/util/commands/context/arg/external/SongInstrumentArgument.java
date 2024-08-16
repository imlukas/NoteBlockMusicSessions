package dev.imlukas.songbooks.util.commands.context.arg.external;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.songs.instrument.SongInstrument;
import dev.imlukas.songbooks.songs.instrument.registry.SongInstrumentRegistry;
import dev.imlukas.songbooks.util.commands.context.CommandContext;
import dev.imlukas.songbooks.util.commands.context.arg.AbstractArgument;
import dev.imlukas.songbooks.util.registry.GenericIdRegistry;

import java.util.List;

public class SongInstrumentArgument extends AbstractArgument<SongInstrument> {

    private final SongInstrumentRegistry instrumentRegistry;

    protected SongInstrumentArgument(SongBooksPlugin plugin, String name) {
        super(name);
        this.instrumentRegistry = plugin.getSongInstrumentRegistry();
    }

    public static SongInstrumentArgument create(SongBooksPlugin plugin, String name) {
        return new SongInstrumentArgument(plugin, name);
    }

    @Override
    public SongInstrument parse(CommandContext context) {
        return instrumentRegistry.getInstrument(context.getLastInput());
    }

    @Override
    public List<String> tabComplete(CommandContext context) {
        return instrumentRegistry.getItemIds();
    }
}
