package com.appfabet.Models;

import java.io.Serializable;

public class Level implements Serializable {
    private String name;
    private String description;
    // 1 - easy, 2- medium, 3-good
    private int level;
    private boolean completed;
    private int rating;
    private int logo;
    private int resource;

    public Level(String name, String description, int level, boolean completed, int logo,int resource) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.completed = completed;
        this.logo = logo;
        this.resource = resource;
    }

    public Level() {

    }


    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
