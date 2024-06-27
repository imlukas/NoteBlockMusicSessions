package dev.imlukas.songbooks.util.file.messages.provider;

import net.kyori.adventure.audience.Audience;
import dev.imlukas.songbooks.util.file.messages.Message;
import dev.imlukas.songbooks.util.text.placeholder.Placeholder;
import org.bukkit.configuration.ConfigurationSection;

public interface MessageProvider {
    Message provide(ConfigurationSection section, Placeholder<Audience>... placeholders);
}
