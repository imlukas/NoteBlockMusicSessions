package dev.imlukas.songbooks.util.commands.data.builder;

import dev.imlukas.songbooks.util.commands.audience.CommandAudience;
import dev.imlukas.songbooks.util.commands.context.CommandArgument;
import dev.imlukas.songbooks.util.commands.data.Command;
import dev.imlukas.songbooks.util.commands.data.CommandHandler;

import java.util.List;

/**
 * Represents a simple implementation of a Command.
 *
 * @param <T> The audience type.
 */
public class SimpleCommand<T extends CommandAudience> implements Command<T> {

    private final List<CommandArgument> arguments;
    private final Class<T> audience;
    private final String permission;
    private CommandHandler<T> handler;

    public SimpleCommand(List<CommandArgument> arguments, CommandHandler<T> handler, Class<T> audience, String permission) {
        this.arguments = arguments;
        this.handler = handler;
        this.audience = audience;
        this.permission = permission;
    }

    @Override
    public List<CommandArgument> getArguments() {
        return arguments;
    }

    @Override
    public CommandHandler<T> getHandler() {
        return handler;
    }

    public void setHandler(CommandHandler<T> handler) {
        this.handler = handler;
    }

    @Override
    public Class<T> getAudience() {
        return audience;
    }

    @Override
    public String getPermission() {
        return permission;
    }
}
