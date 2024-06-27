package dev.imlukas.songbooks.util.file.messages.impl;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import dev.imlukas.songbooks.util.file.messages.AbstractMessage;
import dev.imlukas.songbooks.util.text.TextUtils;
import dev.imlukas.songbooks.util.text.TitleBuilder;
import dev.imlukas.songbooks.util.text.placeholder.Placeholder;
import org.bukkit.configuration.ConfigurationSection;

public class TitleMessage extends AbstractMessage {

    private final Component title;
    private final Component subtitle;
    private final int fadeIn;
    private final int stay;
    private final int fadeOut;

    @SafeVarargs
    public TitleMessage(ConfigurationSection section, Placeholder<Audience>... placeholders) {
        super(section, placeholders);
        title = TextUtils.color(section.getString("title", ""));
        subtitle = TextUtils.color(section.getString("subtitle", ""));
        fadeIn = section.getInt("fadeIn", 60);
        stay = section.getInt("stay", 20);
        fadeOut = section.getInt("fadeOut", 60);
    }

    @Override
    public void send(Audience audience) {
        Component title = replacePlaceholders(this.title, audience);
        Component subtitle = replacePlaceholders(this.subtitle, audience);

        audience.showTitle(TitleBuilder.builder()
                .title(title)
                .subtitle(subtitle)
                .times(fadeIn, stay, fadeOut)
                .build());
    }
}
