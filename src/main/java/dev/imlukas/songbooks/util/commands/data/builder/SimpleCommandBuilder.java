package dev.imlukas.songbooks.util.commands.data.builder;

import dev.imlukas.songbooks.util.commands.audience.CommandAudience;
import dev.imlukas.songbooks.util.commands.context.CommandArgument;
import dev.imlukas.songbooks.util.commands.context.arg.LiteralArgument;
import dev.imlukas.songbooks.util.commands.data.Command;
import dev.imlukas.songbooks.util.commands.data.CommandBuilder;
import dev.imlukas.songbooks.util.commands.data.CommandHandler;
import dev.imlukas.songbooks.util.commands.manager.AbstractCommandManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a simple implementation of a CommandBuilder.
 *
 * @param <T> The audience type.
 */
public class SimpleCommandBuilder<T extends CommandAudience> implements CommandBuilder<T> {

    private final List<CommandArgument> arguments;
    private final String name;
    private final Class<T> audienceClass;

    private final AbstractCommandManager manager;

    private CommandHandler<T> handler;
    private String permission;
    private String[] aliases;

    public SimpleCommandBuilder(AbstractCommandManager manager, String name, Class<T> audienceClass, List<CommandArgument> arguments, String permission, String... aliases) {
        this.manager = manager;
        this.name = name;
        this.audienceClass = audienceClass;
        this.arguments = arguments;
        this.permission = permission;
        this.aliases = aliases;
    }

    public SimpleCommandBuilder(AbstractCommandManager manager, String name, Class<T> audienceClass) {
        this(
                manager,
                name,
                audienceClass,
                new LinkedList<>(),
                null
        );
    }

    @Override
    public CommandBuilder<T> argument(CommandArgument argument) {
        arguments.add(argument);
        return this;
    }

    @Override
    public CommandBuilder<T> handler(CommandHandler<T> handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public CommandBuilder<T> permission(String permission) {
        this.permission = permission;
        return this;
    }

    @Override
    public CommandBuilder<T> aliases(String... aliases) {
        this.aliases = aliases;
        return this;
    }

    @Override
    public <V extends CommandAudience> CommandBuilder<V> audience(Class<V> audience) {
        return new SimpleCommandBuilder<>(manager, name, audience, arguments, permission, aliases);
    }

    @Override
    public Command<T> build() {
        for (String alias : aliases) {
            build(alias);
        }

        return build(name);
    }

    public Command<T> build(String name) {
        List<CommandArgument> realArguments = new LinkedList<>();

        realArguments.add(LiteralArgument.create(name));
        realArguments.addAll(arguments);

        Command<T> command = new SimpleCommand<>(realArguments, handler, audienceClass, permission);
        manager.registerCommand(command);

        return command;
    }
}
