package me.emirose.plugin.tracksystem.database;


import me.emirose.plugin.tracksystem.model.User;

import java.util.UUID;

public interface ADatabase<T> {

    void loadAll();

    T load(UUID uuid);

    void save(T user);

    void saveAll();

}
