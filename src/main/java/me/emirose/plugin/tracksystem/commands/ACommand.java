package me.emirose.plugin.tracksystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public abstract class ACommand implements CommandExecutor {

    private String label;
    private int depth;
    private String permission = "";
    private Map<String, ACommand> subCommands = new HashMap<>();

    protected ACommand(String label) {
        this.label = label.toLowerCase();
        this.depth = 0;

        // TODO: Check for NPE
        Bukkit.getPluginCommand(label).setExecutor(this);
    }

    protected ACommand(ACommand parent, String label) {
        this.label = label.toLowerCase();
        this.depth = parent.depth + 1;

        parent.subCommands.put(this.label, this);
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return this.onCommand(sender, args);
        }

        ACommand cmd = getCommandFromArgs(args);
        List<String> cmdArgs = Arrays.asList(args).subList(cmd.depth, args.length);
        return cmd.onCommand(sender, cmdArgs.toArray(new String[]{}));
    }

    private ACommand getCommandFromArgs(String[] args) {
        ACommand subCommand = this;
        // Run through any arguments
        for (String arg : args) {
            // get the subcommand corresponding to the arg
            if (subCommand.hasSubCommands()) {
                Optional<ACommand> sub = subCommand.getSubCommand(arg);
                if (!sub.isPresent()) {
                    return subCommand;
                }
                // Step down one
                subCommand = sub.orElse(subCommand);

                subCommand.setLabel(arg);
            } else {
                // We are at the end of the walk
                return subCommand;
            }
            // else continue the loop
        }
        return subCommand;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private Optional<ACommand> getSubCommand(String arg) {
        return Optional.ofNullable(subCommands.get(arg.toLowerCase()));
    }

    private boolean hasSubCommands() {
        return !subCommands.isEmpty();
    }

    private boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(permission);
    }

    protected void sendUsage(CommandSender player) {
        player.sendMessage(String.format("%sUsage: %s", ChatColor.RED, getUsage()));
    }

    protected abstract boolean onCommand(CommandSender player, String[] args);

    protected abstract String getUsage();

}
