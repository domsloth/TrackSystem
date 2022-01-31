package me.emirose.plugin.tracksystem.database;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class InMemoryCache<T extends ADatabaseObject> implements ADatabase<T> {

    Map<UUID, T> userRepository = new HashMap<>();

    @Override
    public void loadAll() {
        // Unimplemented
    }

    @Override
    public T load(UUID uuid) {
        return userRepository.getOrDefault(uuid, create(uuid));
    }

    protected abstract T create(UUID uuid);

    @Override
    public void save(T user) {
        userRepository.put(user.getUUID(), user);
    }

    @Override
    public void saveAll() {
        // Unimplemented
    }
}
