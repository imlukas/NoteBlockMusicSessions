package dev.imlukas.songbooks;

import dev.imlukas.songbooks.commands.GiveInstrumentCommand;
import dev.imlukas.songbooks.commands.MenuCommand;
import dev.imlukas.songbooks.commands.PlaySongCommand;
import dev.imlukas.songbooks.commands.StopSongCommand;
import dev.imlukas.songbooks.data.PlayerDataRegistry;
import dev.imlukas.songbooks.listeners.ConnectionListener;
import dev.imlukas.songbooks.listeners.InstrumentInteractListener;
import dev.imlukas.songbooks.session.tracker.GuestTrackingTask;
import dev.imlukas.songbooks.session.tracker.trackers.GuestMusicSessionTracker;
import dev.imlukas.songbooks.session.tracker.trackers.OwnMusicSessionTracker;
import dev.imlukas.songbooks.songbook.SongBook;
import dev.imlukas.songbooks.songbook.parser.SongBookParser;
import dev.imlukas.songbooks.songs.category.SongCategory;
import dev.imlukas.songbooks.songs.category.parser.SongCategoryParser;
import dev.imlukas.songbooks.songs.instrument.parser.SongInstrumentParser;
import dev.imlukas.songbooks.songs.instrument.registry.SongInstrumentRegistry;
import dev.imlukas.songbooks.songs.song.parser.SongParser;
import dev.imlukas.songbooks.songs.song.registry.ParsedSongRegistry;
import dev.imlukas.songbooks.util.commands.bukkit.BukkitCommandManager;
import dev.imlukas.songbooks.util.file.io.FileUtils;
import dev.imlukas.songbooks.util.file.messages.Messages;
import dev.imlukas.songbooks.util.menu.registry.MenuRegistry;
import dev.imlukas.songbooks.util.registry.GenericIdRegistry;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class SongBooksPlugin extends JavaPlugin {

    private Messages messages;
    private BukkitCommandManager commandManager;
    private MenuRegistry menuRegistry;
    private PlayerDataRegistry playerDataRegistry;

    private GenericIdRegistry<SongCategory> songCategoryRegistry;
    private SongInstrumentRegistry songInstrumentRegistry;

    private ParsedSongRegistry parsedSongRegistry;
    private GenericIdRegistry<SongBook> songBookRegistry;

    private OwnMusicSessionTracker ownMusicSessionTracker;
    private GuestMusicSessionTracker guestMusicSessionTracker;

    @Override
    public void onEnable() {
        // Plugin startup logic
        FileUtils.copyBuiltInResources(this, this.getFile());

        messages = new Messages(this);
        commandManager = new BukkitCommandManager(this, messages);
        menuRegistry = new MenuRegistry(this);
        playerDataRegistry = new PlayerDataRegistry();

        songCategoryRegistry = new GenericIdRegistry<>();
        songInstrumentRegistry = new SongInstrumentRegistry();

        parsedSongRegistry = new ParsedSongRegistry();
        songBookRegistry = new GenericIdRegistry<>();

        ownMusicSessionTracker = new OwnMusicSessionTracker(this);
        guestMusicSessionTracker = new GuestMusicSessionTracker(this);

        GuestTrackingTask guestTrackingTask = new GuestTrackingTask(this);
        guestTrackingTask.start();

        new SongCategoryParser(this).parseAll().forEach(songCategoryRegistry::register);
        new SongInstrumentParser(this).parseAll().forEach(songInstrumentRegistry::register);

        new SongParser(this).parseAll().forEach(parsedSongRegistry::register);
        new SongBookParser(this).parseAll().forEach(songBookRegistry::register);

        registerCommands();
        registerListeners();
    }

    public void registerListeners() {
        registerListener(new ConnectionListener(this));
        registerListener(new InstrumentInteractListener(this));
    }

    public void registerCommands() {
        new GiveInstrumentCommand(this);
        new MenuCommand(this);
        new PlaySongCommand(this);
        new StopSongCommand(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
