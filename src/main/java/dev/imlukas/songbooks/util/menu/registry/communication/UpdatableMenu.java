package dev.imlukas.songbooks.util.menu.registry.communication;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.util.menu.template.Menu;
import org.bukkit.entity.Player;

public abstract class UpdatableMenu extends Menu {

    protected UpdatableMenu(SongBooksPlugin plugin, Player viewer) {
        super(plugin, viewer);
    }

    /**
     * Handles refreshing placeholders and updating buttons and other elements accordingly.
     */
    public abstract void refresh();

    @Override
    public void open() {
        refresh();
        super.open();
    }
}
