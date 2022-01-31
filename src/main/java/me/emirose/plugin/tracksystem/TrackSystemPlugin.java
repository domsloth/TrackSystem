package me.emirose.plugin.tracksystem;

import me.emirose.plugin.tracksystem.commands.ACommand;
import me.emirose.plugin.tracksystem.commands.impl.*;
import me.emirose.plugin.tracksystem.database.impl.user.InMemoryUserStorage;
import me.emirose.plugin.tracksystem.database.impl.user.UserStorage;
import me.emirose.plugin.tracksystem.repo.TrackRepository;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import static jdk.javadoc.internal.doclets.toolkit.util.DocPath.parent;

public final class TrackSystemPlugin extends JavaPlugin {

    private TrackRepository trackRepository;
    private UserStorage userStorage;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.trackRepository = new TrackRepository();
        this.userStorage = new InMemoryUserStorage(trackRepository);

        this.userStorage.loadAll();
        this.trackRepository.loadAll();

        registerCommands();
    }

    private void registerCommands() {
        ACommand aCommand = new ACommand("tracksystem") {
            @Override
            protected boolean onCommand(CommandSender player, String[] args) {
                this.sendHelp(player);
                return true;
            }

            @Override
            public String getUsage() {
                return "<help>";
            }
        };

        new CreateRankCommand(aCommand, trackRepository);
        new CreateTrackCommand(aCommand, trackRepository);
        new ShowTracksCommand(aCommand, this);
        new UserInfoCommand(aCommand, userStorage);
        new SetTrackCommand(aCommand, userStorage, trackRepository);
        new RankChangeWeightCommand(aCommand, trackRepository);
        new PromoteCommand.PromoteUPCommand(aCommand, userStorage, trackRepository);
        new PromoteCommand.PromoteDownCommand(aCommand, userStorage, trackRepository);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public TrackRepository getTrackRepository() {
        return trackRepository;
    }
}
