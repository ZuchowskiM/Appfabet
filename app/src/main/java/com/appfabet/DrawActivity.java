package com.appfabet;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.appfabet.Models.DrawArea;
import com.appfabet.Models.Initializer;
import com.appfabet.Models.Level;
import com.appfabet.Models.LevelNameToLetterDictionary;

import java.util.Locale;

public class DrawActivity extends AppCompatActivity {
    int position;
    Level level;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button2);
        Button clearButton = findViewById(R.id.clearButton);
        DrawArea drawArea = findViewById(R.id.drawing);
        ImageView patternPic = findViewById(R.id.patternPic);
        ImageView speaker = findViewById(R.id.speaker);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("TYPE");
        int learnPosition = args.getInt("learnPosition");
        int variantPosition = args.getInt("variantPosition");
        position = args.getInt("levelPosition");

        level = Initializer.learnTypesList.get(learnPosition).getVariants().get(variantPosition).getLevels().get(position);

        patternPic.setBackgroundResource(level.getResource());

        ScorePopup popup = new ScorePopup();
        System.out.println(level.getName());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = drawArea.checkModel();
                Float procentage = drawArea.getPercentage();
                procentage = procentage * 10;

                Bundle args = new Bundle();
                args.putString("result", result);
                args.putInt("position", position);
                args.putFloat("procentage", procentage);


                popup.setArguments(args);
                popup.show(getSupportFragmentManager(), "Popup");

                //Toast.makeText(DrawActivity.this, "Recognized character: " + result, Toast.LENGTH_LONG).show();
                Toast.makeText(DrawActivity.this, "Score: " + procentage + "Recognized character: " + result , Toast.LENGTH_LONG).show();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawArea.clearArea();
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int lang = textToSpeech.setLanguage(Locale.US);
                    
                    if(lang == TextToSpeech.LANG_MISSING_DATA || lang == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(DrawActivity.this, "Language not supported", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(DrawActivity.this, "Language supported", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(DrawActivity.this, "Speech failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = LevelNameToLetterDictionary.getLetterFromLevelName(Integer.parseInt(level.getName()));

                textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }


}
