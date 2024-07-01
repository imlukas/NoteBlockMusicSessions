package dev.imlukas.songbooks.menu;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.session.MusicSession;
import dev.imlukas.songbooks.session.tracker.MusicSessionTracker;
import dev.imlukas.songbooks.songbook.SongBook;
import dev.imlukas.songbooks.util.menu.buttons.button.Button;
import dev.imlukas.songbooks.util.menu.buttons.custom.NextPageButton;
import dev.imlukas.songbooks.util.menu.buttons.custom.PreviousPageButton;
import dev.imlukas.songbooks.util.menu.layer.pagination.PaginableArea;
import dev.imlukas.songbooks.util.menu.layer.pagination.PaginableLayer;
import dev.imlukas.songbooks.util.menu.template.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SongBookMenu extends Menu {

    private final SongBook book;
    private final MusicSessionTracker sessionTracker;

    protected SongBookMenu(SongBooksPlugin plugin, Player viewer, SongBook book) {
        super(plugin, viewer);
        this.book = book;
        this.sessionTracker = plugin.getMusicSessionTracker();
    }

    @Override
    public void setup() {
        PaginableArea area = new PaginableArea(mask.selection("."));
        PaginableLayer layer = new PaginableLayer(menu, area);

        ItemStack songItem = applicator.getItem("song-item");
        book.getSongList().forEach(song -> {
            Button songButton = new Button(songItem.clone());
            songButton.onLeftClick(() -> sessionTracker.createSession(getViewer(), song));
            area.addElement(songButton);
        });

        NextPageButton nextPageButton = new NextPageButton(applicator.getItem("n"), layer);
        PreviousPageButton previousPageButton = new PreviousPageButton(applicator.getItem("p"), layer);

        applicator.registerButton("c", this::close);
        applicator.registerElement("n", nextPageButton);
        applicator.registerElement("p", previousPageButton);

        menu.addRenderable(layer);
        menu.forceUpdate();

    }

    @Override
    public String getIdentifier() {
        return "";
    }
}
