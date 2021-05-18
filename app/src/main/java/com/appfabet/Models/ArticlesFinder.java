package com.appfabet.Models;

public class ArticlesFinder {


    public String getArticle(char cha){

        String val;
        switch (cha){
            case 'a':
                val = "We think it's an ";
                break;
            case 'e':
                val = "We think it's an ";
                break;
            case 'i':
                val = "We think it's an ";
                break;
            case 'o':
                val = "We think it's an ";
                break;
            case 'u':
                val = "We think it's an ";
                break;
            default:
                val = "We think it's a ";
                break;
        }
        return val;
    }

    public String getArticle2(char cha){

        String val;
        switch (cha){
            case 'a':
                val = "Should be an ";
                break;
            case 'e':
                val = "Should be an ";
                break;
            case 'i':
                val = "Should be an ";
                break;
            case 'o':
                val = "Should be an ";
                break;
            case 'u':
                val = "Should be an ";
                break;
            default:
                val = "Should be an ";
                break;
        }
        return val;
    }
}
