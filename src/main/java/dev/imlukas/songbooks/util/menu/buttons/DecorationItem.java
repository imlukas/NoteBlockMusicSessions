package dev.imlukas.songbooks.util.menu.buttons;

import dev.imlukas.songbooks.util.menu.element.MenuElement;
import dev.imlukas.songbooks.util.text.placeholder.Placeholder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;

public class DecorationItem implements MenuElement {

    private boolean allowRemoval = false;
    private ItemStack displayItem;
    private Collection<Placeholder<Player>> placeholders;

    public DecorationItem(ItemStack displayItem) {
        this.displayItem = displayItem;
    }

    @Override
    public boolean canRemove() {
        return allowRemoval;
    }

    public void setAllowRemoval(boolean allowRemoval) {
        this.allowRemoval = allowRemoval;
    }

    @Override
    public ItemStack getDisplayItem() {
        return displayItem;
    }

    @Override
    public void setDisplayItem(ItemStack item) {
        this.displayItem = item;
    }

    @Override
    public void setItemMaterial(Material material) {
        this.displayItem.setType(material);
    }

    @Override
    public void handle(InventoryClickEvent event) {
    }

    @Override
    public MenuElement copy() {
        return new DecorationItem(displayItem).setItemPlaceholders(placeholders);
    }

    @Override
    public Collection<Placeholder<Player>> getItemPlaceholders() {
        return placeholders;
    }

    @Override
    public MenuElement setItemPlaceholders(Collection<Placeholder<Player>> placeholders) {
        this.placeholders = placeholders;
        return this;
    }

    @SafeVarargs
    @Override
    public final MenuElement setItemPlaceholders(Placeholder<Player>... placeholders) {
        this.placeholders = Arrays.stream(placeholders).toList();
        return this;
    }
}
