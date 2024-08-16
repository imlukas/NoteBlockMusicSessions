package dev.imlukas.songbooks.commands;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.menu.CategoryListMenu;
import dev.imlukas.songbooks.menu.SongBookListMenu;
import dev.imlukas.songbooks.util.commands.audience.bukkit.BukkitPlayerCommandAudience;
import dev.imlukas.songbooks.util.commands.bukkit.BukkitCommandManager;

public class MenuCommand {

    public MenuCommand(SongBooksPlugin plugin) {
        BukkitCommandManager commandManager = plugin.getCommandManager();

        commandManager.newCommand("songs")
                .audience(BukkitPlayerCommandAudience.class)
                .argument("categories")
                .handler((sender, context) -> new CategoryListMenu(plugin, sender.getPlayer()).open())
                .build();

        commandManager.newCommand("songs")
                .audience(BukkitPlayerCommandAudience.class)
                .argument("songbooks")
                .handler((sender, context) -> new SongBookListMenu(plugin, sender.getPlayer()).open())
                .build();

    }
}
