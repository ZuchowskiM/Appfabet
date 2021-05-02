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
    ArrayList<Level> levelsAlphabetSmallLetters = new ArrayList<>();
    ArrayList<Level> levelsAlphabetAllLetters = new ArrayList<>();
    ArrayList<Level> levelsAlphabetAllChronologicalLetters = new ArrayList<>();
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
        LearnVariant bigLetters = new LearnVariant("Train BIG letters " + '\n' + "Random mode" , R.drawable.big_letters);
        bigLetters.setLearnMode(LearnVariant.mode.random);

        LearnVariant smallLetters = new LearnVariant("Train small letters " + '\n' + "Random mode", R.drawable.alphabbet_small);
        smallLetters.setLearnMode(LearnVariant.mode.random);

        // Alphabet all letters in chronological mode
        LearnVariant allLetters = new LearnVariant("Train All letters " + '\n' + "Random mode", R.drawable.alphabbet_all_letters);
        allLetters.setLearnMode(LearnVariant.mode.random);

        LearnVariant allChronological = new LearnVariant("All letters" , R.drawable.alphabet_pic);
        allChronological.setLearnMode(LearnVariant.mode.all);

        learnVariantsAlphabet.add(bigLetters); // 0
        learnVariantsAlphabet.add(allLetters); // 1
        learnVariantsAlphabet.add(smallLetters); // 2
        learnVariantsAlphabet.add(allChronological); // 3

        // Numbers Variants
        LearnVariant simpleNumbers = new LearnVariant("Train simple numbers " + '\n' + "Chronological mode", R.drawable.numbers_pic);
        simpleNumbers.setLearnMode(LearnVariant.mode.chronological);

        LearnVariant complexNumbers = new LearnVariant("Train complex numbers " + '\n' + "Random mode", R.drawable.numbers_pic);
        complexNumbers.setLearnMode(LearnVariant.mode.random);

        learnVariantsNumbers.add(simpleNumbers);
        learnVariantsNumbers.add(complexNumbers);

        learnTypesList.get(0).setVariants(learnVariantsAlphabet); // alphabet
        learnTypesList.get(1).setVariants(learnVariantsNumbers); // numbers


        // big characters init
        char character = 'A';

        for(int i=1; i<27; i++){
            int resPicId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),String.valueOf(character), 1, false, resPicId, LevelType.BIG_LETTERS);
            character = (char) (character+1);
            levelsAlphabetBigLetters.add(level);
        }

        learnTypesList.get(0).getVariants().get(0).setLevels(levelsAlphabetBigLetters);

        // numbers init
        for(int i=1; i<10; i++){
            int resPicId = context.getResources().getIdentifier("p"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),String.valueOf(i), 1, false, resPicId, LevelType.NUMBERS);
            levelsNumbersSimple.add(level);
        }

        learnTypesList.get(1).getVariants().get(0).setLevels(levelsNumbersSimple);

        learnTypesList.get(1).getVariants().get(1).setLevels(levelsNumbersSimple);


        // all characters init
        char character1 = 'A';
        for(int i=1; i<27; i++){
            int resPicId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),String.valueOf(character1), 1, false, resPicId, LevelType.BIG_LETTERS);
            character1 = (char) (character1+1);
            levelsAlphabetAllLetters.add(level);
        }



        // small chars init
        char character2 = 'a';
        for(int i=1; i<27; i++){
            int resPicId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),String.valueOf(character2), 1, false, resPicId, LevelType.SMALL_LETTERS);
            character2 = (char) (character2+1);
            levelsAlphabetSmallLetters.add(level);
            levelsAlphabetAllLetters.add(level);
        }

        learnTypesList.get(0).getVariants().get(1).setLevels(levelsAlphabetAllLetters);
        learnTypesList.get(0).getVariants().get(2).setLevels(levelsAlphabetSmallLetters);




        // all chrono chars init
        char character3 = 'A';
        char character4 = 'a';
        for(int i=1; i<27; i++){
            //adding big first
            int resPicId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),String.valueOf(character3), 1, false, resPicId, LevelType.BIG_LETTERS);
            character3 = (char) (character3+1);
            levelsAlphabetAllChronologicalLetters.add(level);

            int resPicId1 = context.getResources().getIdentifier("m"+i,"drawable",activity.getPackageName());
            //adding small second
            Level level1 = new Level(String.valueOf(i),String.valueOf(character4), 1, false, resPicId1, LevelType.SMALL_LETTERS);
            character4 = (char) (character4+1);
            levelsAlphabetAllChronologicalLetters.add(level1);
        }
        learnTypesList.get(0).getVariants().get(3).setLevels(levelsAlphabetAllChronologicalLetters);

        return learnTypesList;
    }




}
