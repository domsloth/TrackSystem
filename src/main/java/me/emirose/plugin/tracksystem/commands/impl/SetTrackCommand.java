package me.emirose.plugin.tracksystem.commands.impl;

import me.emirose.plugin.tracksystem.commands.ACommand;
import org.bukkit.command.CommandSender;

public class SetTrackCommand extends ACommand {
    public SetTrackCommand() {
        super("settrack");
    }

    @Override
    protected boolean onCommand(CommandSender player, String[] args) {
        return false;
    }

    @Override
    protected String getUsage() {
        return null;
    }
}
