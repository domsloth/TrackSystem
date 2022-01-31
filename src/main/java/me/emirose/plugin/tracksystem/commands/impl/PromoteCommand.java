package me.emirose.plugin.tracksystem.commands.impl;

import me.emirose.plugin.tracksystem.commands.ACommand;
import me.emirose.plugin.tracksystem.database.impl.user.UserStorage;
import me.emirose.plugin.tracksystem.model.User;
import me.emirose.plugin.tracksystem.repo.TrackRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PromoteCommand extends ACommand {

    private final UserStorage userStorage;
    private final TrackRepository trackRepository;

    public PromoteCommand(UserStorage userStorage, TrackRepository trackRepository) {
        super("promoteuser");
        this.userStorage = userStorage;
        this.trackRepository = trackRepository;
    }

    @Override
    protected boolean onCommand(CommandSender player, String[] args) {
        if (args.length != 1) {
            sendUsage(player);
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(String.format("%s%s is offline", ChatColor.RED, args[0]));
            return false;
        }

        User user = userStorage.load(target.getUniqueId());

        TrackRepository.TransactionResult transactionResult = trackRepository.promoteNextRank(user);

        if (transactionResult == TrackRepository.TransactionResult.ERROR_ALREADY_HIGHEST) {
            player.sendMessage(String.format("%sCan't promote this player any further!", ChatColor.RED));
            return false;
        }

        player.sendMessage(String.format("%sSuccessfully promoted the user", ChatColor.GREEN));
        userStorage.save(user);
        return true;
    }

    @Override
    protected String getUsage() {
        return "<user>";
    }
}
