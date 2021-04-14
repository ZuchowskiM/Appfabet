package com.appfabet.Models;

public class LevelNameToLetterDictionary {

    static public String getLetterFromLevelName(int level){

        switch (level){
            case 1:
                return "A";
            case 2:
                return "B";

            case 3:
                return "C";

            case 4:
                return "D";

            case 5:
                return "E";

            case 6:
                return "F";

            case 7:
                return "G";

            case 8:
                return "H";

            case 9:
                return "I";

            default:
                return "null";
        }


    }


}
