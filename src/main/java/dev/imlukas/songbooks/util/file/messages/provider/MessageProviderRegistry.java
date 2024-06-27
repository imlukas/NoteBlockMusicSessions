package dev.imlukas.songbooks.util.file.messages.provider;

import net.kyori.adventure.audience.Audience;
import dev.imlukas.songbooks.util.file.messages.Message;
import dev.imlukas.songbooks.util.file.messages.impl.ActionbarMessage;
import dev.imlukas.songbooks.util.file.messages.impl.ChatMessage;
import dev.imlukas.songbooks.util.file.messages.impl.TitleMessage;
import dev.imlukas.songbooks.util.text.placeholder.Placeholder;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class MessageProviderRegistry {

    private final Map<String, MessageProvider> providers = new HashMap<>();

    public MessageProviderRegistry() {
        registerDefaults();
    }

    public void registerDefaults() {
        register("title", TitleMessage::new);
        register("chat", ChatMessage::new);
        register("actionbar", ActionbarMessage::new);
    }

    public void register(String name, MessageProvider provider) {
        providers.put(name, provider);
    }

    @SafeVarargs
    public final Message get(String name, ConfigurationSection section, Placeholder<Audience>... placeholders) {
        return providers.get(name).provide(section, placeholders);
    }
}
