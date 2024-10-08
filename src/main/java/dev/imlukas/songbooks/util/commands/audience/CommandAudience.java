package dev.imlukas.songbooks.util.commands.audience;

import net.kyori.adventure.text.Component;

/**
 * Represents a generic Audience-like class, similar to Bukkit's CommandSender
 */
public interface CommandAudience {

    /**
     * Sends a raw message to the audience.
     *
     * @param message The message to send.
     */
    void sendMessage(Component message);

    default void sendMessage(String message) {
        sendMessage(Component.text(message));
    }

    /**
     * Checks if the audience has a permission.
     *
     * @param permission The permission to check.
     * @return Whether or not the audience has the permission.
     */
    boolean hasPermission(String permission);

    /**
     * Checks if the audience is a console.
     *
     * @return Whether or not the audience is a console.
     */
    boolean isConsole();

}
