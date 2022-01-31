package me.emirose.plugin.tracksystem.commands;

import me.emirose.plugin.tracksystem.commands.ACommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AHelpCommand extends ACommand {

    protected AHelpCommand(ACommand parent) {
        super(parent, "help");
    }

    @Override
    protected boolean onCommand(CommandSender player, String[] args) {
        sendHeader(player);
        getParent().getSubCommands().forEach((label, cmd) -> {
            if (!"help".equalsIgnoreCase(label))
                sendBody(player, label, cmd);
        });
        sendFooter(player);
        return true;
    }

    private void sendHeader(CommandSender player) {
        player.sendMessage(String.format("%s-----------------", ChatColor.GOLD));
    }


    private void sendBody(CommandSender player, String label, ACommand cmd) {
        player.sendMessage(String.format("%s/%s %s %s", ChatColor.YELLOW, getParent().getLabel(), label, cmd.getUsage()));
    }

    private void sendFooter(CommandSender player) {
        player.sendMessage(String.format("%s-----------------", ChatColor.GOLD));
    }

    @Override
    public String getUsage() {
        return "";
    }
}
