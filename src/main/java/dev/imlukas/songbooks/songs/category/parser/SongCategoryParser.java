package dev.imlukas.songbooks.songs.category.parser;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.songbook.SongBook;
import dev.imlukas.songbooks.songs.category.SongCategory;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import dev.imlukas.songbooks.songs.song.registry.ParsedSongRegistry;
import dev.imlukas.songbooks.util.file.YMLBase;
import dev.imlukas.songbooks.util.file.io.IOUtils;
import dev.imlukas.songbooks.util.item.parser.ItemParser;
import dev.imlukas.songbooks.util.text.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SongCategoryParser {
    private final SongBooksPlugin plugin;

    public SongCategoryParser(SongBooksPlugin plugin) {
        this.plugin = plugin;
    }

    public List<SongCategory> parseAll() {
        List<SongCategory> songCategories = new ArrayList<>();

        IOUtils.traverseAndLoad(new File(plugin.getDataFolder() + File.separator + "categories"), file -> {
            String identifier = TextUtils.removeFileExtension(file.getName());

            YMLBase ymlBase = new YMLBase(plugin, file, false);
            FileConfiguration config = ymlBase.getConfiguration();

            ConfigurationSection itemSection = config.getConfigurationSection("item");
            ItemStack displayItem = ItemParser.from(itemSection);

            SongCategory category = new SongCategory(identifier, displayItem);
            songCategories.add(category);
            System.out.println("Loaded song category: " + identifier);
        });

        return songCategories;
    }
}
