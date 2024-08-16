package dev.imlukas.songbooks.listeners;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.session.tracker.trackers.OwnMusicSessionTracker;
import dev.imlukas.songbooks.songs.instrument.SongInstrument;
import dev.imlukas.songbooks.songs.instrument.registry.SongInstrumentRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InstrumentSwitchListener implements Listener {

    private final SongBooksPlugin plugin;
    private final OwnMusicSessionTracker sessionTracker;
    private final SongInstrumentRegistry instrumentRegistry;

    public InstrumentSwitchListener(SongBooksPlugin plugin) {
        this.plugin = plugin;
        this.sessionTracker = plugin.getOwnMusicSessionTracker();
        this.instrumentRegistry = plugin.getSongInstrumentRegistry();
    }

    @EventHandler
    public void onSwitch(PlayerItemHeldEvent event) {
        PlayerInventory inventory = event.getPlayer().getInventory();

        int previousSlot = event.getPreviousSlot();
        int newSlot = event.getNewSlot();

        if (previousSlot == newSlot) { // There was no switch
            return;
        }

        ItemStack previous = inventory.getItem(previousSlot);
        SongInstrument oldInstrument = instrumentRegistry.getInstrument(previous);

        if (oldInstrument == null) { // Player wasn't holding an instrument
            return;
        }

        ItemStack current = inventory.getItem(newSlot);
        SongInstrument newInstrument = instrumentRegistry.getInstrument(current);

        if (oldInstrument.equals(newInstrument)) { // The player switched to the same instrument no need to do anything
            return;
        }

        // The player switched to a different instrument or an empty slot or a non-instrument item
        sessionTracker.removeSession(event.getPlayer());


    }
}
