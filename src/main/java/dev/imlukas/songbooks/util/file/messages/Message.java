package dev.imlukas.songbooks.util.file.messages;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import dev.imlukas.songbooks.util.text.placeholder.Placeholder;

public interface Message {

    Component replacePlaceholders(Component message, Audience audience);

    void send(Audience audience);
}
