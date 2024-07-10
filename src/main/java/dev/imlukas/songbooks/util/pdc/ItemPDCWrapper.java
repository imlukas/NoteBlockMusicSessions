package dev.imlukas.songbooks.util.pdc;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class ItemPDCWrapper extends PDCWrapper { // ugly wrapper class because pdc bad

    private final ItemStack item;

    public ItemPDCWrapper(JavaPlugin plugin, ItemStack item) {
        this(plugin, item, true);
    }

    public ItemPDCWrapper(JavaPlugin plugin, ItemStack item, boolean clone) {
        super(plugin, (clone ? item.clone() : item).getItemMeta().getPersistentDataContainer());
        this.item = clone ? item.clone() : item;
    }

    public static void modifyItem(JavaPlugin plugin, ItemStack item, Consumer<ItemPDCWrapper> modifier) {
        ItemPDCWrapper wrapper = new ItemPDCWrapper(plugin, item, false);
        modifier.accept(wrapper);
    }
    // utils

    private void modifyMeta(Consumer<ItemMeta> consumer) {
        ItemMeta meta = item.getItemMeta();
        consumer.accept(meta);
        item.setItemMeta(meta);
    }

    @Override
    protected <T> void set(String key, PersistentDataType<T, T> type, T value) {
        modifyMeta(meta -> {
            meta.getPersistentDataContainer().set(createKey(key), type, value);
        });
    }

    public ItemStack getModifiedItem() {
        return item;
    }
}
