package dev.imlukas.songbooks.listeners;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.menu.containers.impl.InstrumentSongsMenu;
import dev.imlukas.songbooks.songs.instrument.SongInstrument;
import dev.imlukas.songbooks.songs.instrument.registry.SongInstrumentRegistry;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        Material type = item.getType();

        if (type != Material.STICK) {
            return;
        }

        SongInstrument instrument = instrumentRegistry.getInstrument(item);

        if (instrument == null) {
            return;
        }

        event.setCancelled(true);
        new InstrumentSongsMenu(plugin, event.getPlayer(), instrument).open();
    }
}
