package com.appfabet.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LearnType implements Serializable {
    private String name;
    private ArrayList<LearnVariant> variants;
    private int levelsNumber;
    private int logo;

    public LearnType(String name, int logo) {
        this.name = name;
        this.logo = logo;
    }

    public LearnType() {
        name = "";
        variants = new ArrayList<>();
    }


    public ArrayList<LearnVariant> getVariants() {
        return variants;
    }

    public void setVariants(ArrayList<LearnVariant> variants) {
        this.variants = variants;
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

    public int getLevelsNumber() {
        return levelsNumber;
    }

    public void setLevelsNumber(int levelsNumber) {
        this.levelsNumber = levelsNumber;
    }
}
