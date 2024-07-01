package dev.imlukas.songbooks.songs.category;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class SongCategory {

    private final String identifier;
    private final ItemStack displayItem;

    public SongCategory(String identifier, ItemStack displayItem) {
        this.identifier = identifier;
        this.displayItem = displayItem;
    }
}
