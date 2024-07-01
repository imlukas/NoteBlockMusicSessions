package dev.imlukas.songbooks.util.commands.node;

import dev.imlukas.songbooks.util.commands.audience.CommandAudience;
import dev.imlukas.songbooks.util.commands.context.CommandArgument;
import dev.imlukas.songbooks.util.commands.data.Command;
import lombok.Getter;

/**
 * Represents a command node version of a CommandArgument.
 */
@Getter
public class ArgumentCommandNode extends AbstractCommandNode {

    private final Command<?> command;
    private final CommandArgument argument;

    public ArgumentCommandNode(Command<?> command, CommandArgument argument) {
        this.command = command;
        this.argument = argument;
    }

    @Override
    public String getName() {
        return argument.getName();
    }

    @Override
    public String getPermission() {
        return command.getPermission();
    }

    @Override
    public Class<? extends CommandAudience> getTargetAudience() {
        return command.getAudience();
    }

}
