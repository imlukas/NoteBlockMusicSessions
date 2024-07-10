package dev.imlukas.songbooks;

import dev.imlukas.songbooks.data.PlayerDataRegistry;
import dev.imlukas.songbooks.listeners.ConnectionListener;
import dev.imlukas.songbooks.listeners.InstrumentInteractListener;
import dev.imlukas.songbooks.menu.CategoryListMenu;
import dev.imlukas.songbooks.menu.SongBookListMenu;
import dev.imlukas.songbooks.session.tracker.MusicSessionTracker;
import dev.imlukas.songbooks.songbook.SongBook;
import dev.imlukas.songbooks.songs.category.SongCategory;
import dev.imlukas.songbooks.songs.category.parser.SongCategoryParser;
import dev.imlukas.songbooks.songs.instrument.parser.SongInstrumentParser;
import dev.imlukas.songbooks.songs.instrument.registry.SongInstrumentRegistry;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import dev.imlukas.songbooks.songs.song.parser.SongParser;
import dev.imlukas.songbooks.songs.song.registry.ParsedSongRegistry;
import dev.imlukas.songbooks.songbook.parser.SongBookParser;
import dev.imlukas.songbooks.util.commands.audience.bukkit.BukkitPlayerCommandAudience;
import dev.imlukas.songbooks.util.commands.bukkit.BukkitCommandManager;
import dev.imlukas.songbooks.util.commands.context.arg.StringArgument;
import dev.imlukas.songbooks.util.commands.context.arg.external.SongArgument;
import dev.imlukas.songbooks.util.file.io.FileUtils;
import dev.imlukas.songbooks.util.file.messages.Messages;
import dev.imlukas.songbooks.util.menu.registry.MenuRegistry;
import dev.imlukas.songbooks.util.registry.GenericIdRegistry;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
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

    private MusicSessionTracker musicSessionTracker;

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

        musicSessionTracker = new MusicSessionTracker(this);

        new SongCategoryParser(this).parseAll().forEach(songCategoryRegistry::register);
        new SongInstrumentParser(this).parseAll().forEach(songInstrumentRegistry::register);

        new SongParser(this).parseAll().forEach(parsedSongRegistry::register);
        new SongBookParser(this).parseAll().forEach(songBookRegistry::register);


        commandManager.newCommand("instruments").argument("give")
                .permission("songs.admin")
                .audience(BukkitPlayerCommandAudience.class)
                .argument(StringArgument.create("item-id")
                        .tabComplete(songInstrumentRegistry.getItemIds()))
                .handler((sender, ctx) -> {
                    Player player = sender.getPlayer();
                    String itemId = ctx.getArgument("item-id");

                    ItemStack instrumentItem = songInstrumentRegistry.getInstrumentItem(itemId);

                    if (instrumentItem == null) {
                        messages.send(player, "command.item-not-found");
                        return;
                    }

                    player.getInventory().addItem(instrumentItem);
                }).build();

        commandManager.newCommand("songcategories")
                .audience(BukkitPlayerCommandAudience.class)
                .handler((sender, context) -> new CategoryListMenu(this, sender.getPlayer()).open())
                .build();

        commandManager.newCommand("songbooks")
                .audience(BukkitPlayerCommandAudience.class)
                .handler((sender, context) -> new SongBookListMenu(this, sender.getPlayer()).open())
                .build();

        commandManager.newCommand("playsong")
                .audience(BukkitPlayerCommandAudience.class)
                .argument(SongArgument.create(this, "song"))
                .handler((sender, context) -> {

                    Player senderPlayer = sender.getPlayer();
                    ParsedSong song = context.getArgument("song");

                    if (song == null) {
                        messages.send(sender, "song-not-found");
                        return;
                    }

                    musicSessionTracker.createSession(senderPlayer, song);
                }).build();

        commandManager.newCommand("stopsong")
                .audience(BukkitPlayerCommandAudience.class)
                .handler((sender, context) -> {
                    Player senderPlayer = sender.getPlayer();
                    musicSessionTracker.removeSession(senderPlayer);
                }).build();

        registerListener(new ConnectionListener(this));
        registerListener(new InstrumentInteractListener(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
