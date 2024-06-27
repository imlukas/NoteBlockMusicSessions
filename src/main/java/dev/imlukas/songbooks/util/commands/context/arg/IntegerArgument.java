package dev.imlukas.songbooks.util.commands.context.arg;

import dev.imlukas.songbooks.util.commands.context.CommandContext;

public class IntegerArgument extends AbstractArgument<Integer> {

    public IntegerArgument(String name) {
        super(name);
    }

    public static IntegerArgument create(String name, String... tabComplete) {
        IntegerArgument integerArgument = create(name);
        integerArgument.tabComplete(tabComplete);

        return integerArgument;
    }

    public static IntegerArgument create(String name) {
        return new IntegerArgument(name);
    }

    @Override
    public Integer parse(CommandContext context) {
        try {
            return Integer.parseInt(context.getLastInput());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
