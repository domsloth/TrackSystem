package me.emirose.plugin.tracksystem.model;

import me.emirose.plugin.tracksystem.database.ADatabaseObject;

import java.util.UUID;

public class User implements ADatabaseObject {

    private final UUID uuid;
    private TrackedRank rank;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    public TrackedRank getRank() {
        return rank;
    }

    public void setRank(TrackedRank rank) {
        this.rank = rank;
    }
}
