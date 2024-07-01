package dev.imlukas.songbooks.menu;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.songbook.registry.SongBookRegistry;
import dev.imlukas.songbooks.util.menu.buttons.button.Button;
import dev.imlukas.songbooks.util.menu.buttons.custom.NextPageButton;
import dev.imlukas.songbooks.util.menu.buttons.custom.PreviousPageButton;
import dev.imlukas.songbooks.util.menu.layer.pagination.PaginableArea;
import dev.imlukas.songbooks.util.menu.layer.pagination.PaginableLayer;
import dev.imlukas.songbooks.util.menu.template.Menu;
import org.bukkit.entity.Player;

public class SongBookListMenu extends Menu {

    private final SongBookRegistry songBookRegistry;

    protected SongBookListMenu(SongBooksPlugin plugin, Player viewer) {
        super(plugin, viewer);
        this.songBookRegistry = plugin.getSongBookRegistry();
    }

    @Override
    public void setup() {
        PaginableArea area = new PaginableArea(mask.selection("."));
        PaginableLayer layer = new PaginableLayer(menu, area);

        songBookRegistry.getSongBooks().values().forEach(songBook -> {
            Button songBookButton = new Button(songBook.getDisplayItem());

            songBookButton.onLeftClick(() -> {
                close();
                new SongBookMenu(plugin, getViewer(), songBook).open();
            });

            area.addElement(songBookButton);
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
        return "songbook-list";
    }
}
