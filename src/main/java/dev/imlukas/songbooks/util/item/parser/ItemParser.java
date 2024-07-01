package dev.imlukas.songbooks.util.item.parser;

import dev.imlukas.songbooks.util.item.parser.parsers.ItemStackParser;
import dev.imlukas.songbooks.util.item.parser.parsers.impl.VanillaItemParser;
import dev.imlukas.songbooks.util.text.TextUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ItemParser {

    private static final ConcurrentHashMap<String, ItemStackParser> ITEM_PARSERS = new ConcurrentHashMap<>(Map.of(
            "vanilla", new VanillaItemParser()
    ));

    private ItemStack item;
    private ItemMeta meta;

    public ItemParser() {
        this.item = new ItemStack(Material.AIR);
        this.meta = item.getItemMeta();
    }

    public static ItemStack fromOrDefault(ConfigurationSection section, Material defaultItem) {
        ItemStack item = from(section);

        if (item == null) {
            if (defaultItem == null) {
                return null;
            }

            return new ItemStack(defaultItem);
        }

        return item;
    }

    public static ItemStack from(ConfigurationSection section) {
        return new ItemParser().parse(section);
    }

    public static ItemParser asBuilder() {
        return new ItemParser();
    }

    private ItemStack parse(ConfigurationSection section) {
        if (section == null) {
            return null;
        }

        String itemType = section.getString("item-type", "vanilla");
        this.item = ITEM_PARSERS.getOrDefault(itemType, VanillaItemParser::staticParse).parse(section);

        if (item == null) {
            return null;
        }

        this.meta = item.getItemMeta();

        if (meta == null) {
            return null;
        }

        ItemParserSerializer.applySection(this, section);

        return build();
    }


    public ItemParser name(String name) {
        this.meta.displayName(TextUtils.color(name));
        return this;
    }

    public ItemParser lore(List<String> lore) {
        this.meta.lore(TextUtils.color(lore));
        return this;
    }

    public ItemParser amount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemParser modelData(int modelData) {
        this.meta.setCustomModelData(modelData);
        return this;
    }

    public ItemParser unbreakable(boolean unbreakable) {
        this.meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemParser glow(boolean glow) {
        if (glow) {
            this.meta.addEnchant(Enchantment.PIERCING, 1, true);
            return this;
        }

        this.meta.removeEnchant(Enchantment.PIERCING);
        return this;
    }

    public ItemParser trim(ConfigurationSection section) {
        if (!(meta instanceof ArmorMeta armorMeta)) {
            return this;
        }

        TrimMaterial trimMaterial = Registry.TRIM_MATERIAL.get(NamespacedKey.minecraft(section.getString("material").toUpperCase()));
        TrimPattern trimPattern = Registry.TRIM_PATTERN.get(NamespacedKey.minecraft(section.getString("pattern").toUpperCase()));

        if (trimMaterial == null || trimPattern == null) {
            return this;
        }

        ArmorTrim armorTrim = new ArmorTrim(trimMaterial, trimPattern);

        armorMeta.setTrim(armorTrim);
        return this;
    }

    public ItemParser vanillaEnchantments(ConfigurationSection section) {
        for (String enchantmentName : section.getKeys(false)) {
            int level = section.getInt(enchantmentName);

            Enchantment enchant = Registry.ENCHANTMENT.get(NamespacedKey.minecraft(enchantmentName));

            if (enchant == null) {
                throw new IllegalArgumentException("[ItemParser] Enchantment " + enchantmentName + " is null");
            }

            meta.addEnchant(enchant, level, true);
        }
        return this;
    }

    public ItemParser attributes(ConfigurationSection section) {
        for (String key : section.getKeys(false)) {
            ConfigurationSection attributeSection = section.getConfigurationSection(key);

            if (attributeSection == null) {
                continue;
            }

            Attribute attribute = Attribute.valueOf(key.toUpperCase().replace("-", "_"));
            AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(attributeSection.getString("operation").toUpperCase());
            double amount = attributeSection.getDouble("value");


            EquipmentSlot slot = null;
            if (attributeSection.contains("slot")) {
                slot = EquipmentSlot.valueOf(attributeSection.getString("slot").toUpperCase());
            }

            AttributeModifier modifier = new AttributeModifier(
                    UUID.randomUUID(),
                    "item-modifier-" + attribute.name().toLowerCase(),
                    amount,
                    operation,
                    slot
            );

            meta.addAttributeModifier(attribute, modifier);
        }
        return this;
    }

    public ItemParser flags(List<String> flags) {
        List<ItemFlag> itemFlags = new ArrayList<>();

        for (String flag : flags) {
            itemFlags.add(ItemFlag.valueOf(flag.toUpperCase()));
        }

        meta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}
