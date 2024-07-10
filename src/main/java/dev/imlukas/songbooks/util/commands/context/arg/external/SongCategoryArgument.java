package dev.imlukas.songbooks.util.commands.context.arg.external;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.songs.category.SongCategory;
import dev.imlukas.songbooks.util.commands.context.CommandContext;
import dev.imlukas.songbooks.util.commands.context.arg.AbstractArgument;
import dev.imlukas.songbooks.util.registry.GenericIdRegistry;

import java.util.List;

public class SongCategoryArgument extends AbstractArgument<SongCategory> {

    private final GenericIdRegistry<SongCategory> categoryRegistry;

    protected SongCategoryArgument(SongBooksPlugin plugin, String name) {
        super(name);
        this.categoryRegistry = plugin.getSongCategoryRegistry();
    }

    public static SongCategoryArgument create(SongBooksPlugin plugin, String name) {
        return new SongCategoryArgument(plugin, name);
    }

    @Override
    public SongCategory parse(CommandContext context) {
        return categoryRegistry.get(context.getLastInput());
    }

    @Override
    public List<String> tabComplete(CommandContext context) {
        return categoryRegistry.getIds();
    }
}
