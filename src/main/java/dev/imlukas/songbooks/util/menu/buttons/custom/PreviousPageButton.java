package dev.imlukas.songbooks.util.menu.buttons.custom;

import dev.imlukas.songbooks.util.menu.buttons.button.PredicateButton;
import dev.imlukas.songbooks.util.menu.layer.pagination.PaginableLayer;
import org.bukkit.inventory.ItemStack;

public class PreviousPageButton extends PredicateButton {

    public PreviousPageButton(ItemStack displayItem, PaginableLayer layer) {
        super(displayItem, () -> layer.getPage() <= 1 || layer.getMaxPage() == 1);
        onLeftClick(layer::previousPage);
    }
}
