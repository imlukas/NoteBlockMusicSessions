package dev.imlukas.songbooks.songs.instrument;

import dev.imlukas.songbooks.songs.category.SongCategory;
import dev.imlukas.songbooks.util.registry.Identifiable;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class SongInstrument implements Identifiable {

    private final String identifier;
    private final SongCategory associatedCategory;
    private final ItemStack instrumentItem;

    public SongInstrument(String identifier, SongCategory associatedCategory, ItemStack instrumentItem) {
        this.identifier = identifier;
        this.associatedCategory = associatedCategory;
        this.instrumentItem = instrumentItem;
    }

    public ItemStack getInstrumentItem() {
        return instrumentItem.clone();
    }

}
