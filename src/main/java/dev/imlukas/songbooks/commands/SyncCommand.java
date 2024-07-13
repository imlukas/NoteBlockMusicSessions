package dev.imlukas.songbooks.commands;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.data.PlayerData;
import dev.imlukas.songbooks.data.PlayerDataRegistry;
import dev.imlukas.songbooks.util.commands.audience.bukkit.BukkitPlayerCommandAudience;
import dev.imlukas.songbooks.util.commands.bukkit.BukkitCommandManager;
import dev.imlukas.songbooks.util.file.messages.Messages;
import org.bukkit.entity.Player;

public class SyncCommand {

    public SyncCommand(SongBooksPlugin plugin) {
        BukkitCommandManager commandManager = plugin.getCommandManager();
        PlayerDataRegistry playerDataRegistry = plugin.getPlayerDataRegistry();
        Messages messages = plugin.getMessages();

        commandManager.newCommand("songs")
                .audience(BukkitPlayerCommandAudience.class)
                .argument("sync")
                .handler((sender, context) -> {

                    Player senderPlayer = sender.getPlayer();
                    PlayerData playerData = playerDataRegistry.get(senderPlayer);

                    if (playerData == null) {
                        messages.send(sender, "playerdata.not-found");
                        return;
                    }


                    playerData.setShouldSync(!playerData.shouldSync());
                    messages.send(sender, "playerdata.sync." + (playerData.shouldSync() ? "enabled" : "disabled"));
                }).build();
    }
}
