package dev.imlukas.songbooks.util.menu.base;

import dev.imlukas.songbooks.util.item.ItemUtil;
import dev.imlukas.songbooks.util.menu.concurrency.MainThreadExecutor;
import dev.imlukas.songbooks.util.menu.element.MenuElement;
import dev.imlukas.songbooks.util.menu.element.Renderable;
import dev.imlukas.songbooks.util.text.TextUtils;
import dev.imlukas.songbooks.util.text.placeholder.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

public class BaseMenu implements InventoryHolder {

    private final UUID destinationPlayerId;
    private final List<Renderable> renderables = new ArrayList<>();
    private final Map<Integer, MenuElement> elements = new HashMap<>();
    private final Inventory inventory;
    private final List<Runnable> closeTasks = new ArrayList<>();
    private Runnable onOpen;
    private Consumer<ItemStack> onPlayerItemClick;

    private boolean allowClose = true;
    private boolean allowRemoveItems = false;
    private boolean allowInputItems = false;

    public BaseMenu(UUID playerId, String title, int rows) {
        this.inventory = Bukkit.createInventory(this, rows * 9, TextUtils.color(title));
        this.destinationPlayerId = playerId;
    }


    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void open() {
        if (!Bukkit.isPrimaryThread()) {
            MainThreadExecutor.INSTANCE.execute(this::open);
            return;
        }

        Player player = getPlayer();

        if (player == null) {
            return;
        }
        forceUpdate();
        player.openInventory(inventory);
        if (onOpen != null) {
            onOpen.run();
        }
    }

    public Consumer<ItemStack> getOnPlayerItemClick() {
        return onPlayerItemClick;
    }

    public void setOnPlayerItemClick(Consumer<ItemStack> onPlayerItemClick) {
        this.onPlayerItemClick = onPlayerItemClick;
    }

    public void setItemPlaceholders(Placeholder<Player>... placeholders) {
        setItemPlaceholders(List.of(placeholders));
    }

    public void setItemPlaceholders(Collection<Placeholder<Player>> placeholders) {
        for (Renderable renderable : renderables) {
            renderable.setItemPlaceholders(placeholders);
        }

        elements.values().forEach(element -> element.setItemPlaceholders(placeholders));
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(destinationPlayerId);
    }

    public void addRenderable(Renderable... renderable) {
        renderables.addAll(Arrays.asList(renderable));
    }

    public void forceUpdate() {
        Player player = getPlayer();

        if (player == null) {
            return;
        }

        for (Renderable renderable : renderables) {
            if (renderable.isActive()) {
                renderable.forceUpdate();
            }
        }

        for (Map.Entry<Integer, MenuElement> entry : elements.entrySet()) {
            int slot = entry.getKey();
            MenuElement element = entry.getValue();
            element.onRefresh();

            ItemStack item = element.getDisplayItem().clone();

            ItemUtil.replacePlaceholder(item, player, element.getItemPlaceholders());
            inventory.setItem(slot, item);
        }
    }

    public void setElement(int slot, MenuElement element) {
        elements.put(slot, element);
    }

    public void onOpen(Runnable task) {
        onOpen = task;
    }

    public void onClose(Runnable task) {
        closeTasks.add(task);
    }

    public void allowClose(boolean allow) {
        allowClose = allow;
    }

    public void allowRemoveItems(boolean allow) {
        allowRemoveItems = allow;
    }

    public void allowInputItems(boolean allow) {
        allowInputItems = allow;
    }

    public boolean canClose() {
        return allowClose;
    }

    public boolean canRemoveItems() {
        return allowRemoveItems;
    }

    public boolean canInputItems() {
        return allowInputItems;
    }

    public void handleDrag(InventoryDragEvent event) {
        ItemStack cursor = event.getOldCursor();

        if (!cursor.getType().isAir() && !canInputItems()) {
            event.setCancelled(true);
        }
    }

    public void handleClick(InventoryClickEvent event) {
        ItemStack cursor = event.getCursor();
        ItemStack current = event.getCurrentItem();

        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory != null && clickedInventory.getHolder() instanceof Player) {
            if (current == null) {
                return;
            }

            Consumer<ItemStack> action = getOnPlayerItemClick();

            if (action == null) {
                return;
            }

            event.setCancelled(true);
            action.accept(current);
            return;
        }

        if (!canRemoveItems() && current != null && (event.isShiftClick() && !canInputItems())) {
            event.setCancelled(true);
        }

        if (!cursor.getType().isAir() && !canInputItems()) {
            event.setCancelled(true);
        }

        int slot = event.getRawSlot();

        if (slot < 0 || slot >= inventory.getSize()) {
            return;
        }

        MenuElement element = elements.get(slot);

        if (element == null) {
            return;
        }

        element.handle(event);

        if (element.canRemove() && canRemoveItems()) {
            return;
        }

        event.setCancelled(true);
    }

    public void handleClose() {
        for (Runnable task : closeTasks) {
            task.run();
        }
    }
}
