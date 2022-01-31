package me.emirose.plugin.tracksystem.commands.impl;

import me.emirose.plugin.tracksystem.commands.ACommand;
import me.emirose.plugin.tracksystem.database.ADatabase;
import me.emirose.plugin.tracksystem.database.impl.user.UserStorage;
import me.emirose.plugin.tracksystem.model.TrackedRank;
import me.emirose.plugin.tracksystem.model.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UserInfoCommand extends ACommand {

    private final UserStorage userDatabase;

    public UserInfoCommand(UserStorage userDatabase) {
        super("userinfo");
        this.userDatabase = userDatabase;
    }

    @Override
    protected boolean onCommand(CommandSender sender, String[] args) {
        UUID userId = null;
        if (args.length == 0 && sender instanceof Player) {
            userId = ((Player) sender).getUniqueId();
            this.showInfo(sender, userId);
            return true;
        }

        String name = args[0];
        if (name.length() == 32) {
            try {
                userId = UUID.fromString(name);
            } catch (Exception ex) {
                sender.sendMessage(String.format("%sInvalid UUID - User not found", ChatColor.RED));
                return false;
            }
        } else {
            Player target = Bukkit.getPlayer(name);

            if (target == null) {
                sender.sendMessage(String.format("%sCouldn't find online player with name %s", ChatColor.RED, name));
            } else {
                userId = target.getUniqueId();
            }
        }

        if (userId != null)
            showInfo(sender, userId);
        return true;
    }

    private void showInfo(CommandSender sender, UUID userId) {
        User load = userDatabase.load(userId);
        TrackedRank rank = load.getRank();

        String message =
                String.format("-----\n" +
                        "[UUID]:%s\n" +
                        "[TRACK]:%s\n" +
                        "[RANK]:%s\n" +
                        "------\n", userId, rank.getTrack().getName(), rank.getRank().getName());

        sender.sendMessage(message);
    }


    @Override
    protected String getUsage() {
        return "[<player|uuid>]";
    }
}
