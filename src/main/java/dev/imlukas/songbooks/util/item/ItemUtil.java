package dev.imlukas.songbooks.util.item;

import dev.imlukas.songbooks.util.text.TextUtils;
import dev.imlukas.songbooks.util.text.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class ItemUtil {

    private ItemUtil() {
    }

    public static void copyName(ItemStack from, ItemStack to) {
        ItemMeta fromMeta = from.getItemMeta();
        ItemMeta toMeta = to.getItemMeta();
        toMeta.displayName(fromMeta.displayName());
        to.setItemMeta(toMeta);
    }

    public static void setItemName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextUtils.color(name));
        item.setItemMeta(meta);
    }

    public static void addLore(ItemStack item, List<String> toAdd) {
        addLore(item, toAdd.toArray(new String[0]));
    }

    public static void addLore(ItemStack item, String... toAdd) {
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = meta.lore();

        Component[] toAddComponents = new Component[toAdd.length];

        for (int i = 0; i < toAdd.length; i++) {
            toAddComponents[i] = TextUtils.color(toAdd[i]);
        }

        if (lore == null) {
            lore = new ArrayList<>();
        }

        Collections.addAll(lore, toAddComponents);
        meta.lore(lore);
        item.setItemMeta(meta);
    }

    public static void setModelData(ItemStack item, int modelData) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(modelData);
        item.setItemMeta(meta);
    }

    public static <T> void replacePlaceholder(ItemStack item, T replacementObject,
                                              Collection<Placeholder<T>> placeholderCollection) {
        if (item == null || item.getItemMeta() == null) {
            return;
        }
        
        if (placeholderCollection == null || placeholderCollection.isEmpty()) {
            return;
        }

        Placeholder<T>[] placeholders = new Placeholder[placeholderCollection.size()];

        int index = 0;
        for (Placeholder<T> placeholder : placeholderCollection) {
            if (placeholder == null) {
                continue;
            }

            placeholders[index++] = placeholder;
        }

        // shrink array to fit
        if (index != placeholders.length) {
            Placeholder<T>[] newPlaceholders = new Placeholder[index];
            System.arraycopy(placeholders, 0, newPlaceholders, 0, index);
            placeholders = newPlaceholders;
        }

        replacePlaceholder(item, replacementObject, placeholders);
    }

    @SafeVarargs
    public static synchronized <T> void replacePlaceholder(ItemStack item, T replacementObject,
                                                           Placeholder<T>... placeholder) {
        if (item == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }


        Component displayName = meta.displayName();

        if (displayName != null) {
            for (Placeholder<T> componentPlaceholder : placeholder) {
                displayName = componentPlaceholder.replace(displayName, replacementObject);
            }

            meta.displayName(displayName);
        }

        List<Component> lore = meta.lore();

        if (lore != null) {
            for (int i = 0; i < lore.size(); i++) {
                Component line = lore.get(i);

                for (Placeholder<T> componentPlaceholder : placeholder) {
                    line = componentPlaceholder.replace(line, replacementObject);
                }

                lore.set(i, line);
            }

            meta.lore(lore);
        }
        item.setItemMeta(meta);
    }
}
