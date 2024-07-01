package dev.imlukas.songbooks.util.menu.buttons.custom;

import dev.imlukas.songbooks.util.menu.buttons.button.PredicateButton;
import dev.imlukas.songbooks.util.menu.layer.pagination.PaginableLayer;
import org.bukkit.inventory.ItemStack;

public class NextPageButton extends PredicateButton {

    public NextPageButton(ItemStack displayItem, PaginableLayer layer) {
        super(displayItem, () -> layer.getMaxPage() == 1 || layer.getPage() >= layer.getMaxPage());
        onLeftClick(layer::nextPage);
    }
}
