package dev.imlukas.songbooks.util.menu.configuration;

import dev.imlukas.songbooks.util.item.parser.ItemParser;
import dev.imlukas.songbooks.util.menu.buttons.DecorationItem;
import dev.imlukas.songbooks.util.menu.buttons.button.Button;
import dev.imlukas.songbooks.util.menu.element.MenuElement;
import dev.imlukas.songbooks.util.menu.layer.BaseLayer;
import dev.imlukas.songbooks.util.menu.mask.PatternMask;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConfigurationApplicator {

    private final Map<String, ItemStack> items = new ConcurrentHashMap<>();
    private final PatternMask mask;
    private final ConfigurationSection config;
    private final String defaultTitle;
    private BaseLayer defaultLayer;


    public ConfigurationApplicator(ConfigurationSection config) {
        this.config = config;

        defaultTitle = config.getString("title");
        ConfigurationSection items = config.getConfigurationSection("items");

        for (String key : items.getKeys(false)) {
            this.items.put(key, ItemParser.from(items.getConfigurationSection(key)));
        }

        List<String> maskLayout = config.getStringList("layout");
        mask = PatternMask.of(maskLayout);
    }

    public ItemStack getItem(String key) {
        return items.get(key);
    }

    public Button makeButton(String key) {
        return new Button(getItem(key));
    }

    public DecorationItem makeDecorationItem(String key) {
        return new DecorationItem(getItem(key).clone());
    }

    private Button getButton(String key) {
        ItemStack item = getItem(key);

        if (item == null) {
            throw new IllegalArgumentException("No item with key " + key + " found! (items: " + items.keySet() + ")");
        }

        return new Button(item.clone());
    }

    public Button registerButton(String key) {
        return registerButton(key, () -> {
        });
    }

    public Button registerButton(String key, Consumer<InventoryClickEvent> clickHandler) {
        return registerElement(key, getButton(key).setClickAction(clickHandler));
    }

    public Button registerButton(String key, Runnable clickHandler) {
        return registerElement(key, getButton(key).onLeftClick(clickHandler));
    }

    public <T extends MenuElement> T registerElement(String key, T element) {
        defaultLayer.applyRawSelection(mask.selection(key), element);
        return element;
    }

    public void applyConfiguration(BaseLayer layer) {
        for (Map.Entry<String, ItemStack> entry : items.entrySet()) {
            String itemId = entry.getKey();
            ItemStack item = entry.getValue();

            MenuElement element = new DecorationItem(item);

            layer.applySelection(mask.selection(itemId), element);
        }
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public PatternMask getMask() {
        return mask;
    }

    public ConfigurationSection getConfig() {
        return config;
    }

    public BaseLayer getDefaultLayer() {
        return defaultLayer;
    }

    public void setDefaultLayer(BaseLayer defaultLayer) {
        this.defaultLayer = defaultLayer;
    }
}
