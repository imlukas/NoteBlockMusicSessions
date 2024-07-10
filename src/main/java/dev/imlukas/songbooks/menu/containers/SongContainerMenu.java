package dev.imlukas.songbooks.menu.containers;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.session.tracker.MusicSessionTracker;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import dev.imlukas.songbooks.songs.song.registry.ParsedSongRegistry;
import dev.imlukas.songbooks.util.menu.buttons.button.Button;
import dev.imlukas.songbooks.util.menu.buttons.custom.NextPageButton;
import dev.imlukas.songbooks.util.menu.buttons.custom.PreviousPageButton;
import dev.imlukas.songbooks.util.menu.layer.pagination.PaginableArea;
import dev.imlukas.songbooks.util.menu.layer.pagination.PaginableLayer;
import dev.imlukas.songbooks.util.menu.template.Menu;
import dev.imlukas.songbooks.util.text.placeholder.Placeholder;
import dev.imlukas.songbooks.util.text.placeholder.TextPlaceholder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class SongContainerMenu extends Menu {

    private final MusicSessionTracker sessionTracker;

    protected SongContainerMenu(SongBooksPlugin plugin, Player viewer) {
        super(plugin, viewer);
        this.sessionTracker = plugin.getMusicSessionTracker();
    }

    @Override
    public void setup() {
        Player viewer = getViewer();
        PaginableArea area = new PaginableArea(mask.selection("."));
        PaginableLayer layer = new PaginableLayer(menu, area);

        ItemStack songItem = applicator.getItem("song-item");

        parseSongs().forEach(song -> {
            if (song == null) {
                return;
            }

            Song originalSong = song.getSong();
            List<Placeholder<Player>> songPlaceholders = List.of(
                    TextPlaceholder.of("song-title", song.getTitle()),
                    TextPlaceholder.of("song-author", originalSong.getAuthor()),
                    TextPlaceholder.of("song-description", originalSong.getDescription())
            );

            Button songButton = new Button(songItem.clone());
            songButton.setItemPlaceholders(songPlaceholders);
            songButton.onLeftClick(() -> sessionTracker.createSession(viewer, song));
            area.addElement(songButton);
        });

        ItemStack emptyItem = applicator.getItem("b");
        NextPageButton nextPageButton = new NextPageButton(applicator.getItem("n"), emptyItem, layer);
        PreviousPageButton previousPageButton = new PreviousPageButton(applicator.getItem("p"), emptyItem, layer);

        applicator.registerButton("c", this::close);
        applicator.registerElement("n", nextPageButton);
        applicator.registerElement("p", previousPageButton);

        menu.addRenderable(layer);
        menu.forceUpdate();

    }

    public abstract String getIdentifier();

    public abstract List<ParsedSong> parseSongs();
}
