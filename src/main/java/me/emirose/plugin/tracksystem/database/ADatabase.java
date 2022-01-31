package me.emirose.plugin.tracksystem.database;

import java.util.UUID;

public interface ADatabase<T> {

    void loadAll();

    T load(UUID uuid);

    void save(T user);

    void saveAll();

}
