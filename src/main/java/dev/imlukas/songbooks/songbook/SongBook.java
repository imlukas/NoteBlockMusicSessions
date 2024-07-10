package dev.imlukas.songbooks.songbook;

import dev.imlukas.songbooks.songs.song.ParsedSong;
import dev.imlukas.songbooks.util.registry.Identifiable;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SongBook implements Identifiable {

    private final String identifier;
    private final ItemStack displayItem;
    private final List<ParsedSong> songList = new ArrayList<>();

    public SongBook(String identifier, ItemStack displayItem, List<ParsedSong> songList) {
        this.identifier = identifier;
        this.displayItem = displayItem;
        this.songList.addAll(songList);
    }


    public List<ParsedSong> getSongList() {
        return List.copyOf(songList);
    }
}
