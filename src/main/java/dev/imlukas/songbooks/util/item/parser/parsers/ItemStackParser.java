package dev.imlukas.songbooks.util.item.parser.parsers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;


public interface ItemStackParser {

    ItemStack parse(ConfigurationSection section);
}
