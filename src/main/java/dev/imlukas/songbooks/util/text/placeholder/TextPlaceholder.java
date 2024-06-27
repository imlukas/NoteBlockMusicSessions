package dev.imlukas.songbooks.util.text.placeholder;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.intellij.lang.annotations.RegExp;

import java.util.function.Function;

public abstract class TextPlaceholder<I, A> {

    protected static String OPEN_CHAR = "%";
    protected static String CLOSE_CHAR = "%";
    protected final Function<A, String> replacement;
    @RegExp
    protected String placeholder;

    /**
     * Creates a new placeholder with a replacement function.
     *
     * @param placeholder The placeholder to replace
     * @param replacement The function to replace the placeholder with
     */
    protected TextPlaceholder(@RegExp String placeholder, Function<A, String> replacement) {
        this.placeholder = placeholder;
        this.replacement = replacement;
    }

    public static <T extends Audience> Placeholder<T> of(@RegExp String placeholder, String replacement) {
        return new Placeholder<>(placeholder, replacement);
    }

    public static <T extends Audience> Placeholder<T> of(@RegExp String placeholder, Number replacement) {
        return new Placeholder<>(placeholder, replacement);
    }

    public static <T extends Audience> Placeholder<T> of(@RegExp String placeholder, Component replacement) {
        return new Placeholder<>(placeholder, replacement);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public Function<A, String> getReplacement() {
        return replacement;
    }

    public abstract I replace(I text, A audience);
}
