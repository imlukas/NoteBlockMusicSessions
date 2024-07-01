package dev.imlukas.songbooks.songs.instrument;

import dev.imlukas.songbooks.songs.category.SongCategory;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Getter
public class SongInstrument {

    private final SongCategory associatedCategory;
    private final Map<String, ItemStack> instrumentItems;

    public SongInstrument(SongCategory associatedCategory, Map<String, ItemStack> instrumentItems) {
        this.associatedCategory = associatedCategory;
        this.instrumentItems = instrumentItems;
    }

}
