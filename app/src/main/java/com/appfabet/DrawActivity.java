package com.appfabet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.appfabet.Models.DrawArea;
import com.appfabet.Models.Initializer;
import com.appfabet.Models.LearnVariant;
import com.appfabet.Models.Level;

import java.util.ArrayList;
import java.util.Random;

public class DrawActivity extends AppCompatActivity {
    int position;
    public static int currentLevelPosition;
    static int learnPosition;
    static int variantPosition;
    ArrayList<Level> levels;
    LearnVariant currentLearnVariant;
     ImageView patternPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button2);
        Button clearButton = findViewById(R.id.clearButton);
        DrawArea drawArea = findViewById(R.id.drawing);
        patternPic = findViewById(R.id.patternPic);
        ImageView speaker = findViewById(R.id.speaker);

        try{
            Intent intent = getIntent();
            Bundle args = intent.getBundleExtra("TYPE");
            learnPosition = args.getInt("learnPosition");
            variantPosition = args.getInt("variantPosition");
            //position = args.getInt("levelPosition");
        } catch (Exception e){
            e.printStackTrace();
        }

        currentLearnVariant = Initializer.learnTypesList.get(learnPosition).getVariants().get(variantPosition);
        checkAndSetCurrentMode(currentLearnVariant);


        patternPic.setBackgroundResource(levels.get(currentLevelPosition).getResource());

        ScorePopup popup = new ScorePopup();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = drawArea.checkModel();
                Float procentage = drawArea.getProcentage();
                procentage = procentage * 10;

                Bundle args = new Bundle();
                args.putString("result", result);
                args.putInt("levelPosition", currentLevelPosition);
                args.putFloat("procentage", procentage);
                args.putInt("learnPosition", learnPosition);
                args.putInt("variantPosition", variantPosition);
                args.putString("targetValue", levels.get(currentLevelPosition).getDescription());

                popup.setArguments(args);
                popup.show(getSupportFragmentManager(), "Popup");

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawArea.clearArea();
            }
        });

    }

    private void checkAndSetCurrentMode(LearnVariant learnVariant) {

        levels = (ArrayList<Level>) learnVariant.getLevels();

        switch (learnVariant.getLearnMode()){
            case random:
                Random r = new Random();
                int randomVal = r.nextInt(learnVariant.getLevels().size());
                if(randomVal!=currentLevelPosition)
                    currentLevelPosition = randomVal;
                else{
                    while (randomVal==currentLevelPosition){
                        currentLevelPosition = r.nextInt(learnVariant.getLevels().size());
                    }
                }

                break;
            case chronological:
                currentLevelPosition = learnVariant.getCurrentLevel();
                break;
        }
    }

}
