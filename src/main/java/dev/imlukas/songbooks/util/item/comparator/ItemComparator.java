package dev.imlukas.songbooks.util.item.comparator;

import net.ottersmp.ottercore.util.item.comparator.parameter.ItemComparatorParameter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemComparator {

    private final List<ItemComparatorParameter<?>> enabledComparators = new ArrayList<>();

    public <T> void enable(ItemComparatorParameter<T> comparator, T value) {
        comparator.setValue(value);
        enabledComparators.add(comparator);
    }

    public Set<ItemStack> getExisting(Inventory inventory) {
        Set<ItemStack> existing = new HashSet<>();

        for (ItemStack content : inventory.getContents()) {
            if (content == null) {
                continue;
            }

            for (ItemComparatorParameter<?> comparator : enabledComparators) {
                if (comparator.compare(content)) {
                    existing.add(content);
                }
            }
        }

        return existing;
    }

    public boolean compare(ItemStack toCompare) {
        for (ItemComparatorParameter<?> comparator : enabledComparators) {
            if (!comparator.compare(toCompare)) {
                return false;
            }
        }

        return false;
    }

}
