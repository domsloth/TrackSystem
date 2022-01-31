package me.emirose.plugin.tracksystem.model;

import com.google.common.collect.ImmutableMap;
import org.bukkit.material.Tree;

import java.util.*;

public class Track {

    private String name;
    private int weight;
    private TreeMap<Integer, Rank> ranks = new TreeMap<>();

    public Track(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public List<Rank> getRanks() {
        return new ArrayList<>(ranks.values());
    }

    public TreeMap<Integer, Rank> getRanksMap() {
        return new TreeMap<>(ranks);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Adds the rank at the lowest point possible
     *
     * @param rank rank to add
     */
    public void addRank(Rank rank) {
        int weight = rank.getWeight();
        while (true) {
            if (ranks.get(weight) != null) {
                weight++;
            } else {
                break;
            }
        }
        rank.setWeight(weight);
        ranks.put(rank.getWeight(), rank);
    }


    public void update() {
        TreeMap<Integer, Rank> copy = getRanksMap();
        ranks.clear();

        for (Rank value : copy.values()) {
            addRank(value);
        }
    }
}


