package dev.imlukas.songbooks.util.item.parser.parsers.impl;

import dev.imlukas.songbooks.util.item.parser.parsers.ItemStackParser;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public final class VanillaItemParser implements ItemStackParser {

    public static ItemStack staticParse(ConfigurationSection section) {
        return new VanillaItemParser().parse(section);
    }

    @Override
    public ItemStack parse(ConfigurationSection section) {
        Material material = Material.getMaterial(section.getString("material", section.getString("type", "PAPER")).toUpperCase());

        if (material == null) {
            System.out.println("Material " + section.getString("material") + " not found");
            return null;
        }

        return new ItemStack(material);
    }
}
