package dev.imlukas.songbooks.util.item.comparator.types;

import net.ottersmp.ottercore.util.item.comparator.impl.MaterialComparator;
import net.ottersmp.ottercore.util.item.comparator.impl.ModelDataComparator;
import net.ottersmp.ottercore.util.item.comparator.parameter.ItemComparatorParameter;
import org.bukkit.Material;

public class ComparatorTypes {

    public static final ItemComparatorParameter<Integer> MODEl_DATA = new ModelDataComparator(0);
    public static final ItemComparatorParameter<Material> MATERIAL = new MaterialComparator(Material.AIR);
}
