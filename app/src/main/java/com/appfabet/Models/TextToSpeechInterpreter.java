package com.appfabet.Models;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class TextToSpeechInterpreter {

    private final TextToSpeech textToSpeech;
    private Context context;

    public TextToSpeechInterpreter(Context context)
    {
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
//                      DEBUG LINES
//                    int lang = textToSpeech.setLanguage(Locale.US);
//
//                    if(lang == TextToSpeech.LANG_MISSING_DATA || lang == TextToSpeech.LANG_NOT_SUPPORTED){
//                        Toast.makeText(context, "Language not supported", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(context, "Language supported", Toast.LENGTH_SHORT).show();
//                    }
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
                else {
                    //Toast.makeText(context, "Speech failed", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void speak(String text){

        String data = text;
        textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
    }



}
