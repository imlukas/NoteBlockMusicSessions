package dev.imlukas.songbooks.util.file.messages.impl;

import dev.imlukas.songbooks.util.file.messages.Message;
import dev.imlukas.songbooks.util.text.TextUtils;
import dev.imlukas.songbooks.util.text.placeholder.Placeholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public class RawMessage implements Message {

    private final Component message;
    private final Placeholder<Audience>[] placeholders;

    @SafeVarargs
    public RawMessage(String message, Placeholder<Audience>... placeholders) {
        this(TextUtils.color(message), placeholders);
    }

    @SafeVarargs
    public RawMessage(Component message, Placeholder<Audience>... placeholders) {
        this.placeholders = placeholders;
        this.message = message;
    }

    @Override
    public Component replacePlaceholders(Component message, Audience audience) {
        for (Placeholder<Audience> placeholder : placeholders) {
            message = placeholder.replace(message, audience);
        }

        return message;
    }

    @Override
    public void send(Audience audience) {
        audience.sendMessage(replacePlaceholders(message, audience));
    }
}
