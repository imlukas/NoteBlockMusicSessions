package dev.imlukas.songbooks.util.menu.buttons.custom;

import dev.imlukas.songbooks.util.menu.buttons.button.PredicateButton;
import dev.imlukas.songbooks.util.menu.layer.pagination.PaginableLayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PreviousPageButton extends PredicateButton {

    public PreviousPageButton(ItemStack displayItem, PaginableLayer layer) {
        this(displayItem, new ItemStack(Material.AIR), layer);
    }

    public PreviousPageButton(ItemStack displayItem, ItemStack emptyItem, PaginableLayer layer) {
        super(displayItem, emptyItem, () -> layer.getPage() <= 1 || layer.getMaxPage() == 1);
        onLeftClick(layer::previousPage);
    }
}
