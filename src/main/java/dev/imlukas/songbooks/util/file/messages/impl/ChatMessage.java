package dev.imlukas.songbooks.util.file.messages.impl;

import dev.imlukas.songbooks.util.file.messages.AbstractMessage;
import dev.imlukas.songbooks.util.text.TextUtils;
import dev.imlukas.songbooks.util.text.placeholder.Placeholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class ChatMessage extends AbstractMessage {

    private final List<Component> messages = new ArrayList<>();

    @SafeVarargs
    public ChatMessage(ConfigurationSection section, Placeholder<Audience>... placeholders) {
        super(section, placeholders);
        List<String> messages = section.getStringList("message");

        if (messages.isEmpty()) {
            this.messages.add(TextUtils.color(section.getString("message")));
            return;
        }

        for (String message : messages) {
            this.messages.add(TextUtils.color(message));
        }
    }

    @Override
    public void send(Audience audience) {
        for (Component message : messages) {
            audience.sendMessage(replacePlaceholders(message, audience));
        }
    }


}
