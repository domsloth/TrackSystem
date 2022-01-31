package me.emirose.plugin.tracksystem.model;

import javax.annotation.Nullable;

public class Rank implements Comparable<Rank> {

    private String name;
    private int weight;

    public Rank(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }


    @Override
    public int compareTo(Rank o) {
        if (weight > o.getWeight()) {
            return 1;
        } else if (weight < o.getWeight()) {
            return -1;
        }

        return 0;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
