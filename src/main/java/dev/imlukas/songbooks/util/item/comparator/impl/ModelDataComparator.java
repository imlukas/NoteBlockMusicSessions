package dev.imlukas.songbooks.util.item.comparator.impl;

import net.ottersmp.ottercore.util.item.comparator.parameter.ItemComparatorParameter;
import org.bukkit.inventory.ItemStack;

public class ModelDataComparator extends ItemComparatorParameter<Integer> {

    public ModelDataComparator(Integer value) {
        super(value);
    }

    @Override
    public boolean compare(ItemStack value) {
        if (!value.hasItemMeta()) {
            return false;
        }

        if (!value.getItemMeta().hasCustomModelData()) {
            return false;
        }

        int modelData = value.getItemMeta().getCustomModelData();
        return getValue() == modelData;
    }
}
