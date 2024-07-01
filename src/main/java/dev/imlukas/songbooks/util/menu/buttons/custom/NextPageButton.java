package dev.imlukas.songbooks.util.menu.buttons.custom;

import dev.imlukas.songbooks.util.menu.buttons.button.PredicateButton;
import dev.imlukas.songbooks.util.menu.layer.pagination.PaginableLayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NextPageButton extends PredicateButton {

    public NextPageButton(ItemStack displayItem, PaginableLayer layer) {
        this(displayItem, new ItemStack(Material.AIR), layer);
    }

    public NextPageButton(ItemStack displayItem, ItemStack emptyItem, PaginableLayer layer) {
        super(displayItem, emptyItem, () -> layer.getPage() >= layer.getMaxPage() || layer.getMaxPage() == 1);
        onLeftClick(layer::previousPage);
    }
}
