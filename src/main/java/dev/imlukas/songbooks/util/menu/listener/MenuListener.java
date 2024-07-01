package dev.imlukas.songbooks.util.menu.listener;

import dev.imlukas.songbooks.util.menu.base.BaseMenu;
import dev.imlukas.songbooks.util.menu.registry.MenuRegistry;
import dev.imlukas.songbooks.util.menu.registry.meta.HiddenMenuData;
import dev.imlukas.songbooks.util.menu.registry.meta.HiddenMenuTracker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.function.Consumer;

public class MenuListener implements Listener {


    private final MenuRegistry registry;

    private MenuListener(MenuRegistry registry) {
        this.registry = registry;
    }

    public static void register(MenuRegistry registry) {
        JavaPlugin plugin = registry.getPlugin();
        Bukkit.getPluginManager().registerEvents(new MenuListener(registry), plugin);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (!(holder instanceof BaseMenu baseMenu)) {
            return;
        }

        baseMenu.handleDrag(event);
    }


    @EventHandler
    private void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (!(holder instanceof BaseMenu baseMenu)) {
            return;
        }

        baseMenu.handleClick(event);
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (!(holder instanceof BaseMenu baseMenu)) {
            return;
        }

        baseMenu.handleClose();
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {
        String content = event.getMessage();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        HiddenMenuTracker tracker = registry.getHiddenMenuTracker();

        HiddenMenuData data = tracker.getHiddenMenu(uuid);

        if (data == null) {
            return;
        }

        tracker.removeHiddenMenu(uuid);

        if (content.equalsIgnoreCase("cancel")) {
            data.runDisplayTasks();
            return;
        }

        Consumer<String> task = data.getMeta("input-task", Consumer.class);

        if (task == null) {
            return;
        }

        task.accept(content);
        data.runDisplayTasks();
        event.getRecipients().clear();
    }
}
