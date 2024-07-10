package dev.imlukas.songbooks.songs.instrument.registry;

import dev.imlukas.songbooks.songs.instrument.SongInstrument;
import dev.imlukas.songbooks.util.registry.GenericIdRegistry;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SongInstrumentRegistry extends GenericIdRegistry<SongInstrument> {

    public ItemStack getInstrumentItem(String itemId) {
        for (SongInstrument songInstrument : getRegistry().values()) {
            ItemStack instrumentItem = songInstrument.getInstrumentItems().get(itemId);

            if (instrumentItem == null) {
                continue;
            }

            return instrumentItem.clone();
        }

        return null;
    }

    public List<String> getItemIds() {
        List<String> itemIds = new ArrayList<>();

        for (SongInstrument songInstrument : getRegistry().values()) {
            itemIds.addAll(songInstrument.getInstrumentItems().keySet());
        }

        return itemIds;
    }
}
