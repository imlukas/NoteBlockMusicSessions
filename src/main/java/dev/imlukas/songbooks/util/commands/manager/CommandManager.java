package dev.imlukas.songbooks.util.commands.manager;

import dev.imlukas.songbooks.util.commands.audience.CommandAudience;
import dev.imlukas.songbooks.util.commands.data.CommandBuilder;

/**
 * Represents the proprietary command manager for Core. This is used to register commands across all platforms.
 *
 * @param <T> The type of audience this command manager will be used for. Audience is the default.
 */
public interface CommandManager<T extends CommandAudience> {

    /**
     * Creates a new command builder.
     *
     * @param name The name of the command. This is the first word after the slash.
     * @return The command builder.
     */
    CommandBuilder<T> newCommand(String name);

    void syncCommands();


}
