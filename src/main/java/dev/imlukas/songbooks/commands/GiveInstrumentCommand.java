package dev.imlukas.songbooks.commands;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.songs.instrument.registry.SongInstrumentRegistry;
import dev.imlukas.songbooks.util.commands.audience.bukkit.BukkitPlayerCommandAudience;
import dev.imlukas.songbooks.util.commands.bukkit.BukkitCommandManager;
import dev.imlukas.songbooks.util.commands.context.arg.StringArgument;
import dev.imlukas.songbooks.util.file.messages.Messages;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveInstrumentCommand {

    public GiveInstrumentCommand(SongBooksPlugin plugin) {
        BukkitCommandManager commandManager = plugin.getCommandManager();
        SongInstrumentRegistry songInstrumentRegistry = plugin.getSongInstrumentRegistry();
        Messages messages = plugin.getMessages();

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
    }
}
