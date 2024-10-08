package dev.imlukas.songbooks.util.commands.data;

import dev.imlukas.songbooks.util.commands.audience.CommandAudience;
import dev.imlukas.songbooks.util.commands.context.CommandArgument;
import dev.imlukas.songbooks.util.commands.context.arg.LiteralArgument;

/**
 * Represents a command builder. This is used to build commands.
 *
 * @param <T> The type of audience this command will be used for. Audience is the default.
 */
public interface CommandBuilder<T extends CommandAudience> {

    /**
     * Registers an argument to the command.
     *
     * @param argument The argument to register.
     * @return The command builder.
     */
    CommandBuilder<T> argument(CommandArgument<?> argument);

    default CommandBuilder<T> argument(String name) {
        return argument(LiteralArgument.create(name));
    }

    /**
     * Sets the handler for the command.
     *
     * @param handler The handler to set.
     * @return The command builder.
     */
    CommandBuilder<T> handler(CommandHandler<T> handler);

    /**
     * Sets the permission for the command.
     *
     * @param permission The permission to set.
     * @return The command builder.
     */
    CommandBuilder<T> permission(String permission);


    CommandBuilder<T> aliases(String... aliases);

    /**
     * Sets the new target audience for the command.
     *
     * @param audience The audience to set.
     * @param <V>      The type of audience this command will be used for.
     * @return A new command builder of the new audience type.
     */
    <V extends CommandAudience> CommandBuilder<V> audience(Class<V> audience);

    /**
     * Builds and registers the command.
     *
     * @return The built command.
     */
    Command<T> build();

}
