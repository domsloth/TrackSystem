package me.emirose.plugin.tracksystem.commands.impl;

import me.emirose.plugin.tracksystem.commands.ACommand;
import me.emirose.plugin.tracksystem.model.TrackedRank;
import me.emirose.plugin.tracksystem.repo.TrackRepository;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RankChangeWeightCommand extends ACommand {

    private final TrackRepository trackRepository;

    public RankChangeWeightCommand(ACommand parent, TrackRepository trackRepository) {
        super(parent, "setweight");
        this.trackRepository = trackRepository;
    }

    @Override
    protected boolean onCommand(CommandSender player, String[] args) {
        if (args.length != 2) {
            sendUsage(player);
            return false;
        }

        TrackedRank rank = fetchRank(player, args);
        int weight = fetchWeight(player, args);

        if (rank == null || weight < 0) {
            return false;
        }

        rank.getRank().setWeight(weight);
        rank.getTrack().update();
        return true;
    }

    private int fetchWeight(CommandSender requester, String[] args) {
        int weight = -1;

        try {
            weight = Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
        }

        if (weight < 0)
            requester.sendMessage(String.format("%sInvalid Weight. Must be a positive number", ChatColor.RED));

        return weight;
    }

    private TrackedRank fetchRank(CommandSender requester,String[] strings) {
        TrackedRank rank = trackRepository.getRank(strings[0]);

        if (rank == null) {
            requester.sendMessage(String.format("%sCouldn't find rank '%s'", strings[0]));
        }

        return rank;
    }

    @Override
    public String getUsage() {
        return "<rank> <weight>";
    }
}
