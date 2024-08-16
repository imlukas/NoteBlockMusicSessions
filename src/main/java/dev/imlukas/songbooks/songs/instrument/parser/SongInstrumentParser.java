package dev.imlukas.songbooks.songs.instrument.parser;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.songs.category.SongCategory;
import dev.imlukas.songbooks.songs.instrument.SongInstrument;
import dev.imlukas.songbooks.util.file.YMLBase;
import dev.imlukas.songbooks.util.file.io.IOUtils;
import dev.imlukas.songbooks.util.item.parser.ItemParser;
import dev.imlukas.songbooks.util.pdc.ItemPDCWrapper;
import dev.imlukas.songbooks.util.registry.GenericIdRegistry;
import dev.imlukas.songbooks.util.text.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongInstrumentParser {
    private final SongBooksPlugin plugin;
    private final GenericIdRegistry<SongCategory> songCategoryRegistry;

    public SongInstrumentParser(SongBooksPlugin plugin) {
        this.plugin = plugin;
        this.songCategoryRegistry = plugin.getSongCategoryRegistry();
    }

    public List<SongInstrument> parseAll() {
        List<SongInstrument> songCategories = new ArrayList<>();

        IOUtils.traverseAndLoad(new File(plugin.getDataFolder() + File.separator + "instruments"), file -> {
            String identifier = TextUtils.removeFileExtension(file.getName());

            YMLBase ymlBase = new YMLBase(plugin, file, false);
            FileConfiguration config = ymlBase.getConfiguration();

            String categoryIdentifier = config.getString("category");
            SongCategory associatedCategory = songCategoryRegistry.get(categoryIdentifier);

            if (associatedCategory == null) {
                System.out.println("Failed to load song instrument: " + identifier + " (category not found)");
                return;
            }

            ItemStack instrumentItem = ItemParser.from(config.getConfigurationSection("item"));

            SongInstrument songInstrument = new SongInstrument(identifier, associatedCategory, instrumentItem);
            songCategories.add(songInstrument);
            System.out.println("Loaded song instrument: " + identifier);
        });

        return songCategories;
    }
}
