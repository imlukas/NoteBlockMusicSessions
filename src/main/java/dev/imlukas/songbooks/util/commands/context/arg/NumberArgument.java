package dev.imlukas.songbooks.util.commands.context.arg;

import dev.imlukas.songbooks.util.commands.context.CommandContext;

public class NumberArgument extends AbstractArgument<Object> {
    public NumberArgument(String name) {
        super(name);
    }

    public static NumberArgument create(String name) {
        return new NumberArgument(name);
    }

    @Override
    public Object parse(CommandContext context) {

        try {
            return Double.parseDouble(context.getLastInput());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
