package dev.imlukas.songbooks.util.commands.context.arg;

import dev.imlukas.songbooks.util.commands.context.CommandArgument;
import dev.imlukas.songbooks.util.commands.context.CommandContext;

/**
 * Represents a command argument with a default value.
 */
public class ProxiedDefaultArgument<T> implements CommandArgument<T> {

    private final CommandArgument<T> argument;
    private final Object value;

    public ProxiedDefaultArgument(CommandArgument<T> argument, Object value) {
        this.argument = argument;
        this.value = value;
    }

    @Override
    public String getName() {
        return argument.getName();
    }

    @Override
    public T parse(CommandContext context) {
        Object result = argument.parse(context);

        if (result == null) {
            return (T) value;
        }

        return (T) result;
    }

}
