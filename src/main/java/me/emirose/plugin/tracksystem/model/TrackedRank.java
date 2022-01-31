package me.emirose.plugin.tracksystem.model;

/**
 * Simply represents a track combined with it's rank
 */
public class TrackedRank {

    private Track track;
    private Rank rank;

    public TrackedRank(Track track, Rank rank) {
        this.track = track;
        this.rank = rank;
    }

    public Track getTrack() {
        return track;
    }

    public Rank getRank() {
        return rank;
    }
}
