package dev.imlukas.songbooks.util.menu.registry;

import dev.imlukas.songbooks.util.menu.base.BaseMenu;
import dev.imlukas.songbooks.util.menu.base.ConfigurableBaseMenu;
import dev.imlukas.songbooks.util.menu.configuration.ConfigurationApplicator;
import dev.imlukas.songbooks.util.menu.layer.BaseLayer;
import dev.imlukas.songbooks.util.menu.listener.MenuListener;
import dev.imlukas.songbooks.util.menu.registry.communication.UpdatableMenuRegistry;
import dev.imlukas.songbooks.util.menu.registry.meta.HiddenMenuTracker;
import lombok.Getter;
import net.ottersmp.ottercore.OtterCore;
import net.ottersmp.ottercore.modules.CoreModule;
import net.ottersmp.ottercore.util.file.YMLBase;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public class MenuRegistry {

    private final Map<String, Function<Player, BaseMenu>> menuInitializers = new ConcurrentHashMap<>();
    private final OtterCore plugin;
    private final HiddenMenuTracker hiddenMenuTracker = new HiddenMenuTracker();
    private final UpdatableMenuRegistry updatableMenuRegistry = new UpdatableMenuRegistry();

    public MenuRegistry(OtterCore plugin) {
        this.plugin = plugin;
        MenuListener.register(this);
        load(new File(plugin.getDataFolder(), "menu"));
    }

    public void loadFor(CoreModule module) {
        load(new File(module.getDataFolder() + File.separator + "menu"));
    }

    private void load(File folder) {
        if (!folder.exists()) {
            return;
        }

        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                load(file);
                continue;
            }

            if (!file.getName().endsWith(".yml")) {
                continue;
            }

            FileConfiguration section = new YMLBase(plugin, file, true).getConfiguration();
            registerConfigurable(file.getName(), section);
        }
    }

    public void register(String name, Function<Player, BaseMenu> initializer) {
        menuInitializers.put(name, initializer);
    }

    public void registerConfigurable(String fileName, ConfigurationSection section) {
        String name = fileName.replace(".yml", "");

        int rows = section.getInt("rows", -1);

        if (rows == -1) {
            // get from layout
            List<String> layout = section.getStringList("layout");

            if (layout.isEmpty()) {
                throw new IllegalArgumentException("No rows specified for menu " + name);
            }

            rows = layout.size();
        }

        String title = section.getString("title", name);

        int finalRows = rows;
        register(name, player -> {
            ConfigurationApplicator applicator = new ConfigurationApplicator(section);

            BaseMenu menu = new ConfigurableBaseMenu(player.getUniqueId(), title, finalRows, applicator);
            BaseLayer layer = new BaseLayer(menu);

            applicator.applyConfiguration(layer);
            layer.forceUpdate();

            return menu;
        });
    }

    public Function<Player, BaseMenu> getInitializer(String name) {
        return menuInitializers.get(name);
    }

    public BaseMenu create(String name, Player player) {
        Function<Player, BaseMenu> initializer = getInitializer(name);

        if (initializer == null) {
            plugin.getLogger().warning("No menu initializer found for " + name);
            return null;
        }

        return initializer.apply(player);
    }

    public void registerPostInitTask(String name, Consumer<BaseMenu> consumer) {
        Function<Player, BaseMenu> initializer = getInitializer(name);

        register(name, player -> {
            BaseMenu menu = initializer.apply(player);

            consumer.accept(menu);
            return menu;
        });
    }

    public List<String> getMenuNames() {
        return new ArrayList<>(menuInitializers.keySet());
    }

    public void reload() {
        menuInitializers.clear();

        for (CoreModule value : plugin.getModules().values()) {
            loadFor(value);
        }

        load(new File(plugin.getDataFolder(), "menu"));
    }

}
