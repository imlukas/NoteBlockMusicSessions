package dev.imlukas.songbooks.songs.instrument;

import dev.imlukas.songbooks.songs.category.SongCategory;
import dev.imlukas.songbooks.util.registry.Identifiable;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Getter
public class SongInstrument implements Identifiable {

    private final String identifier;
    private final SongCategory associatedCategory;
    private final Map<String, ItemStack> instrumentItems;

    public SongInstrument(String identifier, SongCategory associatedCategory, Map<String, ItemStack> instrumentItems) {
        this.identifier = identifier;
        this.associatedCategory = associatedCategory;
        this.instrumentItems = instrumentItems;
    }

    public ItemStack getInstrumentItem(String instrumentId) {
        return instrumentItems.get(instrumentId).clone();
    }

}
