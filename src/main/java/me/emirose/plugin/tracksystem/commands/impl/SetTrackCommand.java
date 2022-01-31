package me.emirose.plugin.tracksystem.commands.impl;

import me.emirose.plugin.tracksystem.commands.ACommand;
import me.emirose.plugin.tracksystem.database.impl.user.UserStorage;
import me.emirose.plugin.tracksystem.model.Track;
import me.emirose.plugin.tracksystem.model.User;
import me.emirose.plugin.tracksystem.repo.TrackRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SetTrackCommand extends ACommand {

    private final UserStorage userStorage;
    private final TrackRepository trackRepository;

    public SetTrackCommand(UserStorage userStorage, TrackRepository trackRepository) {
        super("settrack");
        this.userStorage = userStorage;
        this.trackRepository = trackRepository;
    }

    @Override
    protected boolean onCommand(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sendUsage(sender);
            return false;
        }

        User user = fetchUser(sender, args);
        Track track = fetchTrack(sender, args);

        if (user == null || track == null) {
            return false;
        }

        trackRepository.setTrack(user, track);
        userStorage.save(user);
        sender.sendMessage(String.format("%sSuccessfully set the user's track", ChatColor.GREEN));
        return true;
    }

    private Track fetchTrack(CommandSender requester, String[] args) {
        Optional<Track> track = trackRepository.getTrack(args[1]);

        if (!track.isPresent()) {
            requester.sendMessage(String.format("%sTrack '%s' does not exist", ChatColor.RED, args[0]));
            return null;
        }

        return track.get();
    }

    private User fetchUser(CommandSender requester, String[] args) {
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            requester.sendMessage(String.format("%sPlayer '%s' is not online", ChatColor.RED, args[0]));
            return null;
        }

        return userStorage.load(player.getUniqueId());
    }

    @Override
    protected String getUsage() {
        return "<user> <track>";
    }
}
