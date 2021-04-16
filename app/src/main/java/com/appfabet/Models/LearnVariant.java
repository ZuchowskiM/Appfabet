package com.appfabet.Models;

import java.util.List;

public class LearnVariant {
    private String name;
    private List<Level> levels;
    private int logo;
    public enum mode {random, chronological}
    private mode learnMode;
    private int currentLevel;

    public LearnVariant(String name ,int logo) {
        this.name = name;
        this.logo = logo;
    }


    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public LearnVariant() {
    }

    public mode getLearnMode() {
        return learnMode;
    }

    public void setLearnMode(mode learnMode) {
        this.learnMode = learnMode;
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
