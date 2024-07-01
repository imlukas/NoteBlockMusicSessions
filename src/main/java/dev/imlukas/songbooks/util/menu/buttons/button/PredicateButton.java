package dev.imlukas.songbooks.util.menu.buttons.button;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.BooleanSupplier;

public class PredicateButton extends Button {

    private final ItemStack originalItem;
    private ItemStack emptyItem;
    private BooleanSupplier removeIf;

    public PredicateButton(ItemStack displayItem) {
        this(displayItem, () -> false);
    }

    public PredicateButton(ItemStack displayItem, BooleanSupplier removeIf) {
        this(displayItem, new ItemStack(Material.AIR), removeIf);
    }

    public PredicateButton(ItemStack displayItem, ItemStack emptyItem, BooleanSupplier removeIf) {
        super(displayItem);

        this.originalItem = displayItem.clone();
        this.emptyItem = emptyItem.clone();
        this.removeIf = removeIf;
    }

    public void removeIf(BooleanSupplier removeIf) {
        this.removeIf = removeIf;
    }

    public void setEmptyItem(ItemStack emptyItem) {
        this.emptyItem = emptyItem;
    }

    public void test() {
        if (removeIf == null) {
            return;
        }

        if (!removeIf.getAsBoolean()) {
            setDisplayItem(originalItem.clone());
            return;
        }

        this.setDisplayItem(emptyItem);
    }

    @Override
    public void onRefresh() {
        test();
    }
}
