package com.appfabet.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LearnType implements Serializable {
    private String name;
    private List<Level> levels;
    private int levelsNumber;
    private int currentLevel;
    private int logo;

    public LearnType(String name, int logo) {
        this.name = name;
        this.logo = logo;
    }

    public LearnType() {
        name = "";
        levels = new ArrayList<>();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
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

    public int getLevelsNumber() {
        return levelsNumber;
    }

    public void setLevelsNumber(int levelsNumber) {
        this.levelsNumber = levelsNumber;
    }
}
