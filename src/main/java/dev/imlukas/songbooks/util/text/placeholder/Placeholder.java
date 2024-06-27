package dev.imlukas.songbooks.util.text.placeholder;

import dev.imlukas.songbooks.util.text.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.intellij.lang.annotations.RegExp;

import java.util.function.Function;

public class Placeholder<A> extends TextPlaceholder<Component, A> {

    public Placeholder(@RegExp String placeholder, Function<A, String> replacement) {
        super(placeholder, replacement);
    }

    public Placeholder(@RegExp String placeholder, String replacement) {
        this(placeholder, audience -> replacement);
    }

    public Placeholder(@RegExp String placeholder, Number replacement) {
        this(placeholder, String.valueOf(replacement));
    }

    public Placeholder(@RegExp String placeholder, Component replacement) {
        this(placeholder, MiniMessage.miniMessage().serialize(replacement));
    }

    public String replace(String text, A audience) {
        return MiniMessage.miniMessage().serialize(replace(Component.text(text), audience));
    }

    @Override
    public Component replace(Component text, A audience) {
        if (!placeholder.startsWith(OPEN_CHAR)) {
            placeholder = OPEN_CHAR + placeholder;
        }

        if (!placeholder.endsWith(CLOSE_CHAR)) {
            placeholder = placeholder + CLOSE_CHAR;
        }

        String replacementResult = replacement.apply(audience);
        Component replacementComponent = TextUtils.color(replacementResult);

        return text.replaceText(TextReplacementConfig.builder().match(placeholder).replacement(replacementComponent).build());
    }
}
