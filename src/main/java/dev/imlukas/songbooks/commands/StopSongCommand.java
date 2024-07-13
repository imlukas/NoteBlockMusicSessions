package dev.imlukas.songbooks.commands;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.session.tracker.trackers.OwnMusicSessionTracker;
import dev.imlukas.songbooks.songs.song.ParsedSong;
import dev.imlukas.songbooks.util.commands.audience.bukkit.BukkitPlayerCommandAudience;
import dev.imlukas.songbooks.util.commands.bukkit.BukkitCommandManager;
import dev.imlukas.songbooks.util.commands.context.arg.external.SongArgument;
import dev.imlukas.songbooks.util.file.messages.Messages;
import org.bukkit.entity.Player;

public class StopSongCommand {

    public StopSongCommand(SongBooksPlugin plugin) {
        BukkitCommandManager commandManager = plugin.getCommandManager();
        OwnMusicSessionTracker ownMusicSessionTracker = plugin.getOwnMusicSessionTracker();
        Messages messages = plugin.getMessages();

        commandManager.newCommand("playsong")
                .audience(BukkitPlayerCommandAudience.class)
                .argument(SongArgument.create(plugin, "song"))
                .handler((sender, context) -> {

                    Player senderPlayer = sender.getPlayer();
                    ParsedSong song = context.getArgument("song");

                    if (song == null) {
                        messages.send(sender, "song.not-found");
                        return;
                    }

                    ownMusicSessionTracker.createSession(senderPlayer, song);
                }).build();
    }
}
