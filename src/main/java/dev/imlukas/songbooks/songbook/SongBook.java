package dev.imlukas.songbooks.songbook;

import dev.imlukas.songbooks.song.ParsedSong;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SongBook {

    private final String identifier;
    private final ItemStack displayItem;
    private final List<ParsedSong> songList = new ArrayList<>();

    public SongBook(ItemStack displayItem, String identifier, List<ParsedSong> songList) {
        this.displayItem = displayItem;
        this.identifier = identifier;
        this.songList.addAll(songList);
    }


    public List<ParsedSong> getSongList() {
        return List.copyOf(songList);
    }
}
