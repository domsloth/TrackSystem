package me.emirose.plugin.tracksystem.model;

/**
 * Simply represents a rank combined with it's track
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
