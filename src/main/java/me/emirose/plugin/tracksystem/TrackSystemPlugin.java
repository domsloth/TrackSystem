package me.emirose.plugin.tracksystem;

import me.emirose.plugin.tracksystem.commands.impl.*;
import me.emirose.plugin.tracksystem.database.impl.user.InMemoryUserStorage;
import me.emirose.plugin.tracksystem.database.impl.user.UserStorage;
import me.emirose.plugin.tracksystem.repo.TrackRepository;
import org.bukkit.plugin.java.JavaPlugin;

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
        new CreateRankCommand(trackRepository);
        new CreateTrackCommand(trackRepository);
        new ShowTracksCommand(this);
        new UserInfoCommand(userStorage);
        new SetTrackCommand(userStorage, trackRepository);
        new PromoteCommand.PromoteUPCommand(userStorage, trackRepository);
        new PromoteCommand.PromoteDownCommand(userStorage, trackRepository);
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
