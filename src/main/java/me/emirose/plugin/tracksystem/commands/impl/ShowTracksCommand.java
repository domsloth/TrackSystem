package me.emirose.plugin.tracksystem.commands.impl;

import me.emirose.plugin.tracksystem.TrackSystemPlugin;
import me.emirose.plugin.tracksystem.commands.ACommand;
import me.emirose.plugin.tracksystem.model.Rank;
import me.emirose.plugin.tracksystem.model.Track;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class ShowTracksCommand extends ACommand {

    private final TrackSystemPlugin plugin;

    public ShowTracksCommand(TrackSystemPlugin plugin) {
        super("showtracks");
        this.plugin = plugin;
    }

    @Override
    protected boolean onCommand(CommandSender player, String[] args) {
        Collection<Track> allTracks = plugin.getTrackRepository().getAllTracks();

        for (Track track : allTracks) {
            this.displayTrack(player, track);
        }

        return true;
    }

    @Override
    protected String getUsage() {
        return "";
    }

    private void displayTrack(CommandSender player, Track track) {
        String head = "--%s";
        String body = "  --%s";
        player.sendMessage(String.format(head, track.getName()));

        for (Rank rank : track.getRanks()) {
            player.sendMessage(String.format(body, rank.getName()));
        }
    }
}
