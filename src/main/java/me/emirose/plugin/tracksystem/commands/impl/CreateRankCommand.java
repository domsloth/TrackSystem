package me.emirose.plugin.tracksystem.commands.impl;

import jdk.javadoc.internal.doclets.toolkit.util.DocPath;
import me.emirose.plugin.tracksystem.commands.ACommand;
import me.emirose.plugin.tracksystem.model.Track;
import me.emirose.plugin.tracksystem.repo.TrackRepository;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public class CreateRankCommand extends ACommand {

    private TrackRepository trackRepository;

    public CreateRankCommand(ACommand parent, TrackRepository trackRepository) {
        super(parent, "createrank");
        this.trackRepository = trackRepository;
    }

    @Override
    protected boolean onCommand(CommandSender player, String[] args) {
        String name = null;
        Track track = null;

        if (args.length < 1) {
            sendUsage(player);
            return false;
        }

        name = args[0];

        if (args.length > 1) {
            String trackName = args[1];
            Optional<Track> optionalTrack = trackRepository.getTrack(trackName);
            if (!optionalTrack.isPresent()) {
                player.sendMessage(String.format("%sCouldn't find track with the name '%s'", ChatColor.RED, trackName));
                return false;
            }
            track = optionalTrack.get();
        }

        this.trackRepository.createRank(name, track);
        player.sendMessage(String.format("%sSuccessfully created Rank %s", ChatColor.GREEN, name));
        return true;
    }


    @Override
    public String getUsage() {
        return "<name> [<track>]";
    }

}
