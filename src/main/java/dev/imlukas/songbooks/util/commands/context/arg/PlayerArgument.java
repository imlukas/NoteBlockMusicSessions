package dev.imlukas.songbooks.util.commands.context.arg;

import dev.imlukas.songbooks.util.commands.context.CommandContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerArgument extends AbstractArgument<Player> {

    public PlayerArgument(String name) {
        super(name);
    }

    public static PlayerArgument create(String name) {
        return new PlayerArgument(name);
    }

    @Override
    public AbstractArgument<Player> tabComplete(String... tabComplete) {
        return this;
    }

    @Override
    public AbstractArgument<Player> tabComplete(List<String> tabComplete) {
        return this;
    }

    @Override
    public Player parse(CommandContext context) {
        return Bukkit.getPlayer(context.getLastInput());
    }

    @Override
    public List<String> tabComplete(CommandContext context) {
        List<String> completions = new ArrayList<>();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            completions.add(onlinePlayer.getName());
        }

        return completions;
    }
}
