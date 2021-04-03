package com.appfabet.Models;

import android.app.Activity;
import android.content.Context;

import com.appfabet.R;

import java.util.ArrayList;
import java.util.List;

public class Initializer {

    ArrayList<LearnType> learnTypesList = new ArrayList<>();
    ArrayList<Level> levelsAlphabet = new ArrayList<>();
    ArrayList<Level> levelsNumbers = new ArrayList<>();
    ArrayList<Level> levelsThings = new ArrayList<>();


    public Initializer() {
    }

    public List<LearnType> initLearnTypesAndLevels(Context context, Activity activity){
        LearnType alphabet = new LearnType("Alfabet", R.drawable.alphabet_pic);
        LearnType numbers = new LearnType("Cyferki",R.drawable.numbers_pic);
        LearnType things = new LearnType("Rzeczy",R.drawable.numbers_pic);

        learnTypesList.add(alphabet);
        learnTypesList.add(numbers);

        for(int i=1; i<9; i++){
            int resId = context.getResources().getIdentifier("p"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),"Alfabet", 1, false, resId);
            levelsAlphabet.add(level);
            levelsNumbers.add(level);
        }

        learnTypesList.get(0).setLevels(levelsAlphabet);
        learnTypesList.get(1).setLevels(levelsNumbers);

        return learnTypesList;
    }

}
