package dev.imlukas.songbooks.util.menu.base;

import dev.imlukas.songbooks.util.menu.configuration.ConfigurationApplicator;
import dev.imlukas.songbooks.util.menu.element.MenuElement;
import dev.imlukas.songbooks.util.menu.mask.PatternMask;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Getter
public class ConfigurableBaseMenu extends BaseMenu {

    private final ConfigurationApplicator applicator;


    public ConfigurableBaseMenu(UUID playerId, String title, int rows, ConfigurationApplicator applicator) {
        super(playerId, title, rows);
        this.applicator = applicator;
    }

    public ItemStack getItem(String key) {
        return getApplicator().getItem(key);
    }

    public MenuElement getDecorationItem(String key) {
        return getApplicator().makeDecorationItem(key);
    }

    public PatternMask getMask() {
        return getApplicator().getMask();
    }

    public String getTitle() {
        return getApplicator().getDefaultTitle();
    }

    public ConfigurationSection getConfig() {
        return getApplicator().getConfig();
    }


}
