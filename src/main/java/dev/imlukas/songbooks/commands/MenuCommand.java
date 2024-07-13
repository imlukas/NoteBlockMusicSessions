package dev.imlukas.songbooks.commands;

import dev.imlukas.songbooks.SongBooksPlugin;
import dev.imlukas.songbooks.menu.CategoryListMenu;
import dev.imlukas.songbooks.menu.SongBookListMenu;
import dev.imlukas.songbooks.util.commands.audience.bukkit.BukkitPlayerCommandAudience;
import dev.imlukas.songbooks.util.commands.bukkit.BukkitCommandManager;

public class MenuCommand {

    public MenuCommand(SongBooksPlugin plugin) {
        BukkitCommandManager commandManager = plugin.getCommandManager();

        commandManager.newCommand("songcategories")
                .audience(BukkitPlayerCommandAudience.class)
                .handler((sender, context) -> new CategoryListMenu(plugin, sender.getPlayer()).open())
                .build();

        commandManager.newCommand("songbooks")
                .audience(BukkitPlayerCommandAudience.class)
                .handler((sender, context) -> new SongBookListMenu(plugin, sender.getPlayer()).open())
                .build();

    }
}
