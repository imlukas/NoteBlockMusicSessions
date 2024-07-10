package dev.imlukas.songbooks.listeners;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.menu.containers.impl.InstrumentSongsMenu;
import dev.imlukas.songbooks.songs.instrument.SongInstrument;
import dev.imlukas.songbooks.songs.instrument.registry.SongInstrumentRegistry;
import dev.imlukas.songbooks.util.pdc.ItemPDCWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InstrumentInteractListener implements Listener {

    private final SongBooksPlugin plugin;
    private final SongInstrumentRegistry instrumentRegistry;

    public InstrumentInteractListener(SongBooksPlugin plugin) {
        this.plugin = plugin;
        this.instrumentRegistry = plugin.getSongInstrumentRegistry();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) {
            return;
        }

        ItemStack item = event.getItem();

        if (item == null) {
            return;
        }

        ItemPDCWrapper wrapper = new ItemPDCWrapper(plugin, item);
        String instrumentId = wrapper.getString("instrument");

        if (instrumentId == null || instrumentId.isEmpty()) {
            return;
        }

        SongInstrument instrument = instrumentRegistry.get(instrumentId);

        if (instrument == null) {
            return;
        }

        event.setCancelled(true);
        new InstrumentSongsMenu(plugin, event.getPlayer(), instrument).open();
    }
}
