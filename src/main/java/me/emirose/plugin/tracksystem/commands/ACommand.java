package me.emirose.plugin.tracksystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;

import java.util.*;

public abstract class ACommand implements CommandExecutor, TabCompleter {

    private String label;
    private int depth;
    private String permission = "";
    private Map<String, ACommand> subCommands = new HashMap<>();
    private ACommand parent;

    protected ACommand(String label) {
        this.label = label.toLowerCase();
        this.depth = 0;

        new AHelpCommand(this);

        PluginCommand pluginCommand = Bukkit.getPluginCommand(label);
        pluginCommand.setTabCompleter(this);
        pluginCommand.setExecutor(this);
    }

    protected ACommand(ACommand parent, String label) {
        this.label = label.toLowerCase();
        this.depth = parent.depth + 1;
        this.parent = parent;

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

    public abstract String getUsage();

    public ACommand getParent() {
        return parent;
    }

    public Map<String, ACommand> getSubCommands() {
        return subCommands;
    }

    public String getLabel() {
        return label;
    }

    protected void sendHelp(CommandSender player) {
        getSubCommand("help").ifPresent(aCommand -> {
            aCommand.onCommand(player, new String[]{});
        });
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> result = new ArrayList<>();

        if (args.length == depth + 1) {
            result.addAll(getSubCommands().keySet());
        }

        return result;
    }

}
