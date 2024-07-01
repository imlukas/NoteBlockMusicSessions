package dev.imlukas.songbooks.util.item.comparator.impl;

import net.ottersmp.ottercore.util.item.comparator.parameter.ItemComparatorParameter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialComparator extends ItemComparatorParameter<Material> {

    public MaterialComparator(Material value) {
        super(value);
    }

    @Override
    public boolean compare(ItemStack toCompare) {
        Material material = toCompare.getType();
        return getValue().equals(material);
    }

}
