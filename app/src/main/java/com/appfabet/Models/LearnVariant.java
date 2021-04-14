package com.appfabet.Models;

import java.util.List;

public class LearnVariant {
    private String name;
    private List<Level> levels;
    private int logo;


    public LearnVariant(String name ,int logo) {
        this.name = name;
        this.logo = logo;
    }

    public LearnVariant() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
