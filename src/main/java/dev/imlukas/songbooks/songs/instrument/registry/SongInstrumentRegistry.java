package dev.imlukas.songbooks.songs.instrument.registry;

import dev.imlukas.songbooks.songs.instrument.SongInstrument;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongInstrumentRegistry {

    private final Map<Integer, SongInstrument> registry = new HashMap<>();

    public SongInstrument getInstrument(String instrumentId) {
        for (SongInstrument songInstrument : registry.values()) {
            if (!songInstrument.getIdentifier().equalsIgnoreCase(instrumentId)) {
                continue;
            }

            return songInstrument;
        }

        return null;
    }

    public SongInstrument getInstrument(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasCustomModelData()) {
            return null;
        }

        Material type = itemStack.getType();

        if (type != Material.STICK) {
            return null;
        }

        int itemModelData = itemStack.getItemMeta().getCustomModelData();
        return getInstrument(itemModelData);
    }

    public SongInstrument getInstrument(int itemModelData) {
        return registry.get(itemModelData);
    }

    public List<String> getItemIds() {
        List<String> itemIds = new ArrayList<>();

        for (SongInstrument songInstrument : registry.values()) {
            itemIds.add(songInstrument.getIdentifier());
        }

        return itemIds;
    }

    public void register(SongInstrument songInstrument) {
        registry.put(songInstrument.getInstrumentItem().getItemMeta().getCustomModelData(), songInstrument);
    }
}
