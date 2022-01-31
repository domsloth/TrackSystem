package me.emirose.plugin.tracksystem.commands.impl;

import me.emirose.plugin.tracksystem.commands.ACommand;
import me.emirose.plugin.tracksystem.model.Track;
import me.emirose.plugin.tracksystem.repo.TrackRepository;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public class CreateTrackCommand extends ACommand {

    private final TrackRepository trackRepository;

    public CreateTrackCommand(TrackRepository trackRepository) {
        super("createtrack");
        this.trackRepository = trackRepository;
    }

    @Override
    protected boolean onCommand(CommandSender player, String[] args) {
        String name = null;
        int weight = 0;

        if (args.length < 1) {
            sendUsage(player);
            return false;
        }

        name = args[0];

        if (args.length > 1) {
            try {
                weight = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                player.sendMessage(String.format("%sInvalid parsed weight. Must be a number!", ChatColor.RED));
                return false;
            }
        }

        this.trackRepository.createTrack(name, weight);
        player.sendMessage(String.format("%sSuccessfully created Rank %s", ChatColor.GREEN, name));
        return true;
    }

    @Override
    protected String getUsage() {
        return "<name> [<weight>]";
    }
}
