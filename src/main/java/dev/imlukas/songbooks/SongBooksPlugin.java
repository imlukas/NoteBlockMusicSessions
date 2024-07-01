package dev.imlukas.songbooks;

import dev.imlukas.songbooks.data.tracker.PlayerDataRegistry;
import dev.imlukas.songbooks.listeners.ConnectionListener;
import dev.imlukas.songbooks.session.tracker.MusicSessionTracker;
import dev.imlukas.songbooks.song.ParsedSong;
import dev.imlukas.songbooks.song.parser.SongParser;
import dev.imlukas.songbooks.song.registry.ParsedSongRegistry;
import dev.imlukas.songbooks.songbook.registry.SongBookRegistry;
import dev.imlukas.songbooks.util.commands.audience.bukkit.BukkitPlayerCommandAudience;
import dev.imlukas.songbooks.util.commands.bukkit.BukkitCommandManager;
import dev.imlukas.songbooks.util.commands.context.arg.external.SongArgument;
import dev.imlukas.songbooks.util.file.io.FileUtils;
import dev.imlukas.songbooks.util.file.messages.Messages;
import dev.imlukas.songbooks.util.menu.registry.MenuRegistry;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class SongBooksPlugin extends JavaPlugin {

    private Messages messages;
    private BukkitCommandManager commandManager;
    private MenuRegistry menuRegistry;
    private PlayerDataRegistry playerDataRegistry;
    private ParsedSongRegistry parsedSongRegistry;
    private SongBookRegistry songBookRegistry;
    private MusicSessionTracker musicSessionTracker;

    @Override
    public void onEnable() {
        // Plugin startup logic
        FileUtils.copyBuiltInResources(this, this.getFile());

        messages = new Messages(this);
        commandManager = new BukkitCommandManager(this, messages);
        playerDataRegistry = new PlayerDataRegistry();
        parsedSongRegistry = new ParsedSongRegistry();
        musicSessionTracker = new MusicSessionTracker(this);

        new SongParser(this).parseAll().forEach(parsedSongRegistry::register);

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

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
