package dev.imlukas.songbooks.util.file.messages.impl;

import dev.imlukas.songbooks.util.file.messages.AbstractMessage;
import dev.imlukas.songbooks.util.text.TextUtils;
import dev.imlukas.songbooks.util.text.placeholder.Placeholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;


public class ActionbarMessage extends AbstractMessage {

    private final Component message;

    @SafeVarargs
    public ActionbarMessage(ConfigurationSection section, Placeholder<Audience>... placeholders) {
        super(section, placeholders);
        message = TextUtils.color(section.getString("message"));
    }

    @Override
    public void send(Audience audience) {
        audience.sendActionBar(replacePlaceholders(message, audience));
    }
}
