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
    ArrayList<Level> levelsAlphabetRandomLetters = new ArrayList<>();
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


        // Alphabet variants in random mode
        LearnVariant bigLetters = new LearnVariant("Train big letters " + '\n' + "Random mode" , R.drawable.alphabet_pic);
        bigLetters.setLearnMode(LearnVariant.mode.random);

        LearnVariant smallLetters = new LearnVariant("WORKING! Train small letters " + '\n' + "Chronological mode", R.drawable.alphabbet_small);
        smallLetters.setLearnMode(LearnVariant.mode.chronological);

        // Alphabet all letters in chronological mode
        LearnVariant allLetters = new LearnVariant("Train all letters " + '\n' + "Chronological mode", R.drawable.big_letters);
        allLetters.setLearnMode(LearnVariant.mode.chronological);

        LearnVariant randomLetter = new LearnVariant("WORKING! Train all letters " + '\n' + "Chronological mode" , R.drawable.alphabet_pic);
        randomLetter.setLearnMode(LearnVariant.mode.random);

        learnVariantsAlphabet.add(bigLetters);
        learnVariantsAlphabet.add(allLetters);
        learnVariantsAlphabet.add(smallLetters);
        learnVariantsAlphabet.add(randomLetter);

        // Numbers Variants
        LearnVariant simpleNumbers = new LearnVariant("Train simple numbers " + '\n' + "Chronological mode", R.drawable.numbers_pic);
        simpleNumbers.setLearnMode(LearnVariant.mode.chronological);

        LearnVariant complexNumbers = new LearnVariant("Train complex numbers " + '\n' + "Random mode", R.drawable.numbers_pic);
        complexNumbers.setLearnMode(LearnVariant.mode.random);

        learnVariantsNumbers.add(simpleNumbers);
        learnVariantsNumbers.add(complexNumbers);

        learnTypesList.get(0).setVariants(learnVariantsAlphabet); // alphabet
        learnTypesList.get(1).setVariants(learnVariantsNumbers); // numbers
        //learnTypesList.get(2).setLevels(levelsThings); // other things

        // big characters init
        char character = 'A';

        for(int i=1; i<9; i++){
            int resId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            int resPicId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),String.valueOf(character), 1, false, resId, resPicId);
            character = (char) (character+1);
            levelsAlphabetBigLetters.add(level);
        }

        learnTypesList.get(0).getVariants().get(0).setLevels(levelsAlphabetBigLetters);

        // numbers init
        for(int i=1; i<9; i++){
            int resId = context.getResources().getIdentifier("p"+i,"drawable",activity.getPackageName());
            int resPicId = context.getResources().getIdentifier("p"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),String.valueOf(i), 1, false, resId, resPicId);
            levelsNumbersSimple.add(level);
        }

        learnTypesList.get(1).getVariants().get(0).setLevels(levelsNumbersSimple);

        learnTypesList.get(1).getVariants().get(1).setLevels(levelsNumbersSimple);


        // all characters init
        char character1 = 'A';


        for(int i=1; i<9; i++){
            int resId = context.getResources().getIdentifier("p"+i,"drawable",activity.getPackageName());
            int resPicId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),String.valueOf(character1), 1, false, resId, resPicId);
            character1 = (char) (character1+1);
            levelsAlphabetAllLetters.add(level);
        }

        learnTypesList.get(0).getVariants().get(1).setLevels(levelsAlphabetAllLetters);



        return learnTypesList;
    }




}
