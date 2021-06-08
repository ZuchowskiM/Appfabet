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
    ArrayList<LearnVariant> learnVariantsDrawing = new ArrayList<>();
    ArrayList<Level> levelsAlphabetBigLetters = new ArrayList<>();
    ArrayList<Level> levelsAlphabetSmallLetters = new ArrayList<>();
    ArrayList<Level> levelsAlphabetAllLetters = new ArrayList<>();
    ArrayList<Level> levelsAlphabetAllChronologicalLetters = new ArrayList<>();
    ArrayList<Level> levelsNumbersSimple = new ArrayList<>();
    ArrayList<Level> levelsTraning = new ArrayList<>();
    public static boolean firstTime = false;


    public Initializer() {

    }

    public ArrayList<LearnType> initLearnTypesAndLevels(Context context, Activity activity){

        learnTypesList = new ArrayList<>();
        LearnType alphabet = new LearnType("Literki", R.drawable.alphabet_pic);
        LearnType numbers = new LearnType("Cyferki",R.drawable.numbers_pic);
        LearnType training = new LearnType("Rysowanie",R.drawable.trainingkids);

        learnTypesList.add(alphabet);
        learnTypesList.add(numbers);
        learnTypesList.add(training);


        // Alphabet variants in random mode
        LearnVariant bigLetters = new LearnVariant("Duże literki " + '\n' + "Tryb losowy" , R.drawable.big_letters);
        bigLetters.setLearnMode(LearnVariant.mode.random);

        LearnVariant smallLetters = new LearnVariant("Małe literki " + '\n' + "Tryb losowy", R.drawable.alphabbet_small);
        smallLetters.setLearnMode(LearnVariant.mode.random);

        // Alphabet all letters in chronological mode
        LearnVariant allLetters = new LearnVariant("Wszystkie literki " + '\n' + "Tryb losowy", R.drawable.alphabbet_all_letters);
        allLetters.setLearnMode(LearnVariant.mode.random);

        // List of all levels
        LearnVariant allChronological = new LearnVariant("Tryb dowolny" , R.drawable.all_letters_variant_pic);
        allChronological.setLearnMode(LearnVariant.mode.all);

        learnVariantsAlphabet.add(bigLetters); // 0
        learnVariantsAlphabet.add(allLetters); // 1
        learnVariantsAlphabet.add(smallLetters); // 2
        learnVariantsAlphabet.add(allChronological); // 3


        // List of things to train
        LearnVariant trainingThings = new LearnVariant("Trening", R.drawable.things_pic);
        trainingThings.setLearnMode(LearnVariant.mode.all);
        learnVariantsDrawing.add(trainingThings);


        // Numbers Variants
        LearnVariant simpleNumbers = new LearnVariant("Cyferki " + '\n' + "Po kolei", R.drawable.numbers_pic);
        simpleNumbers.setLearnMode(LearnVariant.mode.chronological);

        LearnVariant complexNumbers = new LearnVariant("Cyferki " + '\n' + "Losowo", R.drawable.numbers_pic);
        complexNumbers.setLearnMode(LearnVariant.mode.random);

        learnVariantsNumbers.add(simpleNumbers);
        learnVariantsNumbers.add(complexNumbers);


        //-----------------Adding Variants to list --------------------------
        learnTypesList.get(0).setVariants(learnVariantsAlphabet); // alphabet
        learnTypesList.get(1).setVariants(learnVariantsNumbers); // numbers
        learnTypesList.get(2).setVariants(learnVariantsDrawing); //drawings
        //--------------------------------------------------------------------


        PolishLetters polishLetters = new PolishLetters();

        // big characters init

        for(int i=1; i<polishLetters.getSize(); i++){
            //int resPicId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),polishLetters.bigLetters[i-1], 1, false,  LevelType.BIG_LETTERS);
            levelsAlphabetBigLetters.add(level);
        }

        learnTypesList.get(0).getVariants().get(0).setLevels(levelsAlphabetBigLetters);

        // numbers init
        for(int i=1; i<10; i++){
            int resPicId = context.getResources().getIdentifier("p"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),String.valueOf(i), 1, false, LevelType.NUMBERS);
            levelsNumbersSimple.add(level);
        }

        learnTypesList.get(1).getVariants().get(0).setLevels(levelsNumbersSimple);

        learnTypesList.get(1).getVariants().get(1).setLevels(levelsNumbersSimple);




        // all characters init

        for(int i=1; i < polishLetters.getSize(); i++){
            int resPicId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),polishLetters.bigLetters[i-1], 1, false,  LevelType.BIG_LETTERS);
            levelsAlphabetAllLetters.add(level);
        }


        // small chars init

        for(int i=1; i<polishLetters.getSize(); i++){
            int resPicId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),polishLetters.smallLetters[i-1], 1, false,  LevelType.SMALL_LETTERS);
            levelsAlphabetSmallLetters.add(level);
            levelsAlphabetAllLetters.add(level);
        }

        learnTypesList.get(0).getVariants().get(1).setLevels(levelsAlphabetAllLetters);
        learnTypesList.get(0).getVariants().get(2).setLevels(levelsAlphabetSmallLetters);




        // all chrono chars init

        for(int i=1; i<polishLetters.getSize(); i++){
            //adding big first
            int resPicId = context.getResources().getIdentifier("l"+i,"drawable",activity.getPackageName());
            Level level = new Level(String.valueOf(i),polishLetters.bigLetters[i-1], 1, false,  LevelType.BIG_LETTERS);
            levelsAlphabetAllChronologicalLetters.add(level);

            int resPicId1 = context.getResources().getIdentifier("m"+i,"drawable",activity.getPackageName());
            //adding small second
            Level level1 = new Level(String.valueOf(i),polishLetters.smallLetters[i-1], 1, false,  LevelType.SMALL_LETTERS);
            levelsAlphabetAllChronologicalLetters.add(level1);
        }
        learnTypesList.get(0).getVariants().get(3).setLevels(levelsAlphabetAllChronologicalLetters);


        Level level1 = new Level("Things","Kwiatek",1,false,LevelType.THINGS);
        level1.setResource(context.getResources().getIdentifier("t1","drawable",activity.getPackageName()));
        level1.setPatternPic(context.getResources().getIdentifier("pattern1","drawable",activity.getPackageName()));

        Level level2 = new Level("Things","Jabłko",1,false,LevelType.THINGS);
        level2.setResource(context.getResources().getIdentifier("t2","drawable",activity.getPackageName()));
        level2.setPatternPic(context.getResources().getIdentifier("pattern2","drawable",activity.getPackageName()));

        Level level3 = new Level("Things","Pies",1,false,LevelType.THINGS);
        level3.setResource(context.getResources().getIdentifier("t3","drawable",activity.getPackageName()));
        level3.setPatternPic(context.getResources().getIdentifier("pattern3","drawable",activity.getPackageName()));

        Level level4 = new Level("Things","Świnka",1,false,LevelType.THINGS);
        level4.setResource(context.getResources().getIdentifier("t4","drawable",activity.getPackageName()));
        level4.setPatternPic(context.getResources().getIdentifier("pattern4","drawable",activity.getPackageName()));

        Level level5 = new Level("Things","Ptaszek",1,false,LevelType.THINGS);
        level5.setResource(context.getResources().getIdentifier("t5","drawable",activity.getPackageName()));
        level5.setPatternPic(context.getResources().getIdentifier("pattern5","drawable",activity.getPackageName()));

        levelsTraning.add(level1);
        levelsTraning.add(level2);
        levelsTraning.add(level3);
        levelsTraning.add(level4);
        levelsTraning.add(level5);

        System.out.println("Rozmiar" + levelsTraning.size());
        System.out.println("Rozmiar" + learnTypesList.size());

        learnTypesList.get(2).getVariants().get(0).setLevels(levelsTraning);

        return learnTypesList;
    }





}
