package dev.imlukas.songbooks.util.file.messages;

import dev.imlukas.songbooks.util.text.placeholder.Placeholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;

public abstract class AbstractMessage implements Message {

    protected final ConfigurationSection section;
    protected Placeholder<Audience>[] placeholders;

    @SafeVarargs
    protected AbstractMessage(ConfigurationSection section, Placeholder<Audience>... placeholders) {
        this.section = section;
        this.placeholders = placeholders;
    }

    public Component replacePlaceholders(Component message, Audience audience) {
        for (Placeholder<Audience> placeholder : placeholders) {
            message = placeholder.replace(message, audience);
        }
        return message;
    }
}
