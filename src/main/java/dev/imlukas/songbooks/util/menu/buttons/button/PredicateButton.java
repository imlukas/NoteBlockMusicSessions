package dev.imlukas.songbooks.util.menu.buttons.button;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.BooleanSupplier;

public class PredicateButton extends Button {

    private final ItemStack originalItem;
    private BooleanSupplier removeIf;

    public PredicateButton(ItemStack displayItem) {
        this(displayItem, () -> false);
    }

    public PredicateButton(ItemStack displayItem, BooleanSupplier removeIf) {
        super(displayItem);

        this.originalItem = displayItem.clone();
        this.removeIf = removeIf;
    }

    public void removeIf(BooleanSupplier removeIf) {
        this.removeIf = removeIf;
    }

    public void test() {
        if (removeIf == null) {
            return;
        }

        if (!removeIf.getAsBoolean()) {
            setDisplayItem(originalItem.clone());
            return;
        }

        this.getDisplayItem().setType(Material.AIR);
    }

    @Override
    public void onRefresh() {
        test();
    }
}
