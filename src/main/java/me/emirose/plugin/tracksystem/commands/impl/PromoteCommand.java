package me.emirose.plugin.tracksystem.commands.impl;

import me.emirose.plugin.tracksystem.commands.ACommand;
import me.emirose.plugin.tracksystem.database.impl.user.UserStorage;
import me.emirose.plugin.tracksystem.model.User;
import me.emirose.plugin.tracksystem.repo.TrackRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PromoteCommand extends ACommand {

    public enum PromoteDirection {
        UP,
        DOWN
    }

    public static class PromoteUPCommand extends PromoteCommand {
        public PromoteUPCommand(ACommand parent, UserStorage userStorage, TrackRepository trackRepository) {
            super(parent, userStorage, trackRepository, "promoteuser", PromoteDirection.UP);
        }
    }

    public static class PromoteDownCommand extends PromoteCommand {
        public PromoteDownCommand(ACommand parent, UserStorage userStorage, TrackRepository trackRepository) {
            super(parent, userStorage, trackRepository, "demoteuser", PromoteDirection.DOWN);
        }
    }

    private final UserStorage userStorage;
    private final TrackRepository trackRepository;
    private final PromoteDirection direction;


    private PromoteCommand(ACommand parent, UserStorage userStorage, TrackRepository trackRepository, String label, PromoteDirection direction) {
        super(parent, label);
        this.userStorage = userStorage;
        this.trackRepository = trackRepository;
        this.direction = direction;
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


        TrackRepository.TransactionResult transactionResult = null;

        switch (direction) {
            case UP:
                transactionResult = trackRepository.promoteNextRank(user);
                break;
            case DOWN:
                transactionResult = trackRepository.promotePreviousRank(user);
                break;
        }

        if (transactionResult == TrackRepository.TransactionResult.ERROR_ALREADY_HIGHEST) {
            player.sendMessage(String.format("%sCan't promote this player any further!", ChatColor.RED));
            return false;
        } else if (transactionResult == TrackRepository.TransactionResult.ERROR_ALREADY_LOWEST) {
            player.sendMessage(String.format("%sCan't demote this player any further!", ChatColor.RED));
            return false;
        }

        player.sendMessage(String.format("%sSuccessfully updated the user", ChatColor.GREEN));
        userStorage.save(user);
        return true;
    }

    @Override
    public String getUsage() {
        return "<user>";
    }
}
