package me.emirose.plugin.tracksystem.database.impl.user;

import me.emirose.plugin.tracksystem.database.InMemoryCache;
import me.emirose.plugin.tracksystem.model.Rank;
import me.emirose.plugin.tracksystem.model.Track;
import me.emirose.plugin.tracksystem.model.TrackedRank;
import me.emirose.plugin.tracksystem.model.User;
import me.emirose.plugin.tracksystem.repo.TrackRepository;

import java.util.UUID;

public class InMemoryUserStorage extends InMemoryCache<User> implements UserStorage {

    private final TrackRepository trackRepository;

    public InMemoryUserStorage(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    protected User create(UUID uuid) {
        User user = new User(uuid);
        this.loadNewUser(user);
        return user;
    }

    private void loadNewUser(User user) {
        Track defaultTrack = trackRepository.getTrack(0);
        //Shouldn't happen
        if (defaultTrack == null) {
            System.out.println("No default track");
            return;
        }

        Rank defaultRank = trackRepository.getDefaultRankForTrack(defaultTrack);

        if (defaultRank == null) {
            defaultRank = new Rank("default", -1);
        }

        user.setRank(new TrackedRank(defaultTrack, defaultRank));
    }

}
