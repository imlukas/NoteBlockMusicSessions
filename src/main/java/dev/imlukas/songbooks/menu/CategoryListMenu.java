package dev.imlukas.songbooks.menu;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.menu.containers.impl.BookSongsMenu;
import dev.imlukas.songbooks.menu.containers.impl.CategorySongsMenu;
import dev.imlukas.songbooks.songbook.SongBook;
import dev.imlukas.songbooks.songs.category.SongCategory;
import dev.imlukas.songbooks.util.menu.buttons.button.Button;
import dev.imlukas.songbooks.util.menu.buttons.custom.NextPageButton;
import dev.imlukas.songbooks.util.menu.buttons.custom.PreviousPageButton;
import dev.imlukas.songbooks.util.menu.layer.pagination.PaginableArea;
import dev.imlukas.songbooks.util.menu.layer.pagination.PaginableLayer;
import dev.imlukas.songbooks.util.menu.template.Menu;
import dev.imlukas.songbooks.util.registry.GenericIdRegistry;
import org.bukkit.entity.Player;

public class CategoryListMenu extends Menu {

    private final GenericIdRegistry<SongCategory> songBookRegistry;

    public CategoryListMenu(SongBooksPlugin plugin, Player viewer) {
        super(plugin, viewer);
        this.songBookRegistry = plugin.getSongCategoryRegistry();
        setup();
    }

    @Override
    public void setup() {
        PaginableArea area = new PaginableArea(mask.selection("."));
        PaginableLayer layer = new PaginableLayer(menu, area);

        songBookRegistry.getRegistry().values().forEach(category -> {
            Button songBookButton = new Button(category.getDisplayItem());

            songBookButton.onLeftClick(() -> {
                close();
                new CategorySongsMenu(plugin, getViewer(), category).open();
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
        return "category-list";
    }
}
