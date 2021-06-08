package com.appfabet.Models;

public class PolishLetters {

    public String[] bigLetters = {"A","Ą","B","C","Ć","D","E","Ę","F","G","H","I","J","K","L","Ł","M","N","Ń","O"
    ,"Ó","P","R","S","Ś","T","U","W","Y","Z","Ź","Ż"};

    public String[] smallLetters = {"a","ą","b","c","ć","d","e","ę","f","h","i","j","k","l","ł","m","n","ń","o",
    "ó","p","r","s","ś","t","u","w","y","z","ź","ż"};

    public int getSize(){
        return bigLetters.length;
    }
}
