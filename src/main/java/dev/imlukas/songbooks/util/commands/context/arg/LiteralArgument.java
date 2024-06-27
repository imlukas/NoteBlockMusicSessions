package dev.imlukas.songbooks.util.commands.context.arg;

import dev.imlukas.songbooks.util.commands.context.CommandArgument;
import dev.imlukas.songbooks.util.commands.context.CommandContext;

import java.util.Collections;
import java.util.List;

/**
 * Represents a "literal" argument, which matches the input exactly.
 */
public class LiteralArgument implements CommandArgument<String> {

    private final String name;

    public LiteralArgument(String name) {
        this.name = name;
    }

    public static LiteralArgument create(String name) {
        return new LiteralArgument(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String parse(CommandContext context) {
        if (context.getLastInput().equalsIgnoreCase(name)) {
            return name;
        }

        return null;
    }

    @Override
    public List<String> tabComplete(CommandContext context) {
        return Collections.singletonList(name);
    }
}
