package com.appfabet.Models;

import android.app.Activity;
import android.content.Context;

import com.appfabet.R;

import java.util.ArrayList;
import java.util.List;

public class Initializer {

    public static ArrayList<LearnType> learnTypesList = new ArrayList<>();
    ArrayList<LearnVariant> learnVariantsAlphabet = new ArrayList<>();
    ArrayList<LearnVariant> learnVariantsNumbers = new ArrayList<>();
    ArrayList<Level> levelsAlphabetBigLetters = new ArrayList<>();
    ArrayList<Level> levelsAlphabetAllLetters = new ArrayList<>();
    ArrayList<Level> levelsNumbersSimple = new ArrayList<>();
    ArrayList<Level> levelsThings = new ArrayList<>();


    public Initializer() {
    }

    public List<LearnType> initLearnTypesAndLevels(Context context, Activity activity){
        LearnType alphabet = new LearnType("Alphabet", R.drawable.alphabet_pic);
        LearnType numbers = new LearnType("Numbers",R.drawable.numbers_pic);
        //LearnType things = new LearnType("Other things",R.drawable.numbers_pic);

        learnTypesList.add(alphabet);
        learnTypesList.add(numbers);
        //learnTypesList.add(things);


        // Alphabet variants
        LearnVariant bigLetters = new LearnVariant("Big Letters" , R.drawable.alphabet_pic);
        LearnVariant smallLetters = new LearnVariant("Small letters" , R.drawable.alphabet_pic);
        LearnVariant allLetters = new LearnVariant("All letters" , R.drawable.alphabet_pic);
        LearnVariant randomLetter = new LearnVariant("Random letters" , R.drawable.alphabet_pic);

        learnVariantsAlphabet.add(bigLetters);
        learnVariantsAlphabet.add(smallLetters);
        learnVariantsAlphabet.add(allLetters);
        learnVariantsAlphabet.add(randomLetter);

        // Numbers Variants
        LearnVariant simpleNumbers = new LearnVariant("Simple Numbers", R.drawable.numbers_pic);
        LearnVariant complexNumbers = new LearnVariant("Complex Numbers", R.drawable.numbers_pic);

        learnVariantsNumbers.add(simpleNumbers);
        learnVariantsNumbers.add(complexNumbers);

        learnTypesList.get(0).setVariants(learnVariantsAlphabet); // alphabet
        learnTypesList.get(1).setVariants(learnVariantsNumbers); // numbers
        //learnTypesList.get(2).setLevels(levelsThings); // other things

        // big characters init
        char character = 'A';
        int charInt = (int) character;

        for(int i=1; i<9; i++){
            int resId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            int resPicId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            String destinationChar = String.valueOf(charInt);
            Level level = new Level(String.valueOf(i),destinationChar, 1, false, resId, resPicId);
            charInt = charInt+1;
            levelsAlphabetBigLetters.add(level);
        }

        learnTypesList.get(0).getVariants().get(0).setLevels(levelsAlphabetBigLetters);

        // numbers init
        for(int i=1; i<9; i++){
            int resId = context.getResources().getIdentifier("p"+i,"drawable",activity.getPackageName());
            int resPicId = context.getResources().getIdentifier("p"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),"numbers", 1, false, resId, resPicId);
            levelsNumbersSimple.add(level);
        }

        learnTypesList.get(1).getVariants().get(0).setLevels(levelsNumbersSimple);


        // all characters init
        char character1 = 'a';
        int charInt1 = (int) character1;

        for(int i=1; i<9; i++){
            int resId = context.getResources().getIdentifier("p"+i,"drawable",activity.getPackageName());
            int resPicId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            String destinationChar = String.valueOf(charInt1);
            Level level = new Level(String.valueOf(i),destinationChar, 1, false, resId, resPicId);
            charInt1 = charInt1+1;
            levelsAlphabetAllLetters.add(level);
        }

        learnTypesList.get(0).getVariants().get(2).setLevels(levelsAlphabetAllLetters);



        return learnTypesList;
    }




}
