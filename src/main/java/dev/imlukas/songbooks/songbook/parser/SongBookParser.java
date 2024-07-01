package dev.imlukas.songbooks.songbook.parser;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import dev.imlukas.songbooks.songs.song.registry.ParsedSongRegistry;
import dev.imlukas.songbooks.songbook.SongBook;
import dev.imlukas.songbooks.util.file.YMLBase;
import dev.imlukas.songbooks.util.file.io.IOUtils;
import dev.imlukas.songbooks.util.item.parser.ItemParser;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SongBookParser {
    private final SongBooksPlugin plugin;
    private final ParsedSongRegistry parsedSongRegistry;

    public SongBookParser(SongBooksPlugin plugin) {
        this.plugin = plugin;
        this.parsedSongRegistry = plugin.getParsedSongRegistry();
    }

    public List<SongBook> parseAll() {
        List<SongBook> songBooks = new ArrayList<>();

        IOUtils.traverseAndLoad(new File(plugin.getDataFolder() + File.separator + "songbooks"), file -> {
            YMLBase ymlBase = new YMLBase(plugin, file, false);

            FileConfiguration config = ymlBase.getConfiguration();

            String identifier = file.getName();
            ConfigurationSection itemSection = config.getConfigurationSection("item");
            ItemStack displayItem = ItemParser.from(itemSection);

            List<String> songNames = config.getStringList("songs");
            List<ParsedSong> songs = new ArrayList<>();
            for (String songName : songNames) {
                ParsedSong song = parsedSongRegistry.get(songName);
                if (song != null) {
                    songs.add(song);
                }
            }

            SongBook songBook = new SongBook(identifier, displayItem, songs);
            songBooks.add(songBook);
            System.out.println("Loaded song book: " + identifier);
        });

        return songBooks;
    }
}
