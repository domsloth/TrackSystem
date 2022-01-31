package me.emirose.plugin.tracksystem.repo;

import me.emirose.plugin.tracksystem.model.Rank;
import me.emirose.plugin.tracksystem.model.Track;
import me.emirose.plugin.tracksystem.model.TrackedRank;
import me.emirose.plugin.tracksystem.model.User;

import java.util.*;

public class TrackRepository {

    private TreeMap<Integer, Track> weightedTracks = new TreeMap<>();

    public TrackRepository() {

    }

    public void loadAll() {
        Track defaultTrack = new Track("default", 0);
        registerTrack(defaultTrack);

        Track staff = new Track("staff", 10);
        registerTrack(staff);

        createRank("commoner", defaultTrack);
        createRank("apprentice", defaultTrack);
        createRank("hero", defaultTrack);
        createRank("lord", defaultTrack);

        createRank("helper", staff);
        createRank("moderator", staff);
        createRank("admin", staff);
    }

    private void registerTrack(Track track) {
        if (weightedTracks.containsKey(track.getWeight())) {
            System.out.println("Overlapping Weights, uppering weight");
            track.setWeight(track.getWeight() + 1);
            registerTrack(track);
            return;
        }

        weightedTracks.put(track.getWeight(), track);
    }

    public Collection<Track> getAllTracks() {
        return weightedTracks.values();
    }


    public Optional<Track> getTrack(String trackName) {
        return getAllTracks().stream().filter(track -> Objects.equals(track.getName(), trackName)).findAny();
    }

    public Track getTrack(int weight) {
        return weightedTracks.floorEntry(weight).getValue();
    }

    public void createRank(String name, Track track) {
        Rank rank = new Rank(name, 0);
        if (track == null) {
            track = getTrack(0);
        }
        track.addRank(rank);
    }

    public void createTrack(String name, int weight) {
        registerTrack(new Track(name, weight));
    }

    public Rank getNextRank(Track track, Rank rank) {
        Map.Entry<Integer, Rank> higherEntry = track.getRanksMap().higherEntry(rank.getWeight());
        if (higherEntry == null) {
            return null;
        }

        return higherEntry.getValue();
    }

    public Rank getPreviousRank(Track track, Rank rank) {
        Map.Entry<Integer, Rank> lowerEntry = track.getRanksMap().lowerEntry(rank.getWeight());
        if (lowerEntry == null) {
            return null;
        }

        return lowerEntry.getValue();
    }

    public TransactionResult promoteNextRank(User user) {
        Rank nextRank = getNextRank(user.getRank().getTrack(), user.getRank().getRank());
        if (nextRank == null) {
            return TransactionResult.ERROR_ALREADY_HIGHEST;
        }

        user.setRank(new TrackedRank(user.getRank().getTrack(), nextRank));
        return TransactionResult.SUCCESS;
    }

    public void setTrack(User user, Track track) {
        user.setRank(new TrackedRank(track, getDefaultRankForTrack(track)));
    }

    public Rank getDefaultRankForTrack(Track defaultTrack) {
        return defaultTrack.getRanks().size() > 0 ? defaultTrack.getRanks().get(0) : null;
    }

    public enum TransactionResult {
        ERROR_ALREADY_HIGHEST,
        ERROR_ALREADY_LOWEST,
        SUCCESS
    }

}
