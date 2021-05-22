package com.appfabet;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.appfabet.Listeners.OnSwipeTouchListener;
import com.appfabet.Models.DrawArea;
import com.appfabet.Models.Initializer;
import com.appfabet.Models.LearnVariant;
import com.appfabet.Models.Level;
import com.appfabet.Models.RandomColorGenerator;
import com.appfabet.Models.TextToSpeechInterpreter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawActivity extends AppCompatActivity {
    //SlidrInterface slird;
    int position;
    public static int currentLevelPosition;
    static int learnPosition;
    static int variantPosition;
    ArrayList<Level> levels;
    LearnVariant currentLearnVariant;
    TextView patternElement;
    ConstraintLayout layout;
    ImageView trainingImage;
    boolean fromMenu = false;
    static ImageView completedImageView;
    TextToSpeechInterpreter textToSpeechInterpreter;
    View view;
    String learnType = null;
    int streakCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(android.R.id.content).getRootView();

        ImageButton button = findViewById(R.id.button2);
        ImageButton clearButton = findViewById(R.id.clearButton);
        DrawArea drawArea = findViewById(R.id.drawing);
        patternElement = findViewById(R.id.patternPic);
        layout = findViewById(R.id.backgroundLayout);
        trainingImage = findViewById(R.id.trainingImage);
        completedImageView = findViewById(R.id.completedImageView);
        ImageView speaker = findViewById(R.id.speaker);
        textToSpeechInterpreter = new TextToSpeechInterpreter(getApplicationContext());

        //swipe
        //slird = Slidr.attach(this);


        try {
            Intent intent = getIntent();
            Bundle args = intent.getBundleExtra("TYPE");
            position = args.getInt("levelPosition");
            learnType = args.getString("learnType");
            fromMenu = true;
        } catch (Exception e) {

        }

        try {
            Intent intent = getIntent();
            Bundle args = intent.getBundleExtra("TYPE");

            if(args != null){
                learnPosition = args.getInt("learnPosition");
                variantPosition = args.getInt("variantPosition");
            }

            Bundle streakArgs = intent.getBundleExtra("STREAK");

            if(streakArgs != null){
                streakCount = streakArgs.getInt("streakCount");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        currentLearnVariant = Initializer.learnTypesList.get(learnPosition).getVariants().get(variantPosition);
        checkAndSetCurrentMode(currentLearnVariant);

        drawArea.setCurrentLevelType(levels.get(currentLevelPosition).getType());
        ScorePopup popup = new ScorePopup();

        if(learnType!=null) {
            if(learnType.equals("training")){
                trainingImage.setImageResource(levels.get(currentLevelPosition).getResource());
                trainingImage.setVisibility(View.VISIBLE);
            }
        }

        patternElement.setText(levels.get(currentLevelPosition).getDescription());
        RandomColorGenerator randomColorGenerator = new RandomColorGenerator();
        patternElement.setTextColor(randomColorGenerator.getColor());

        // DLA FILIPA
        // levels.get(currentLevelPosition) <- wszystko co potrzebujesz na temat obecnego levelu
        // levels.get(currentLevelPosition).getResource() <- to jest obrazek który wyswietla sie uzytkownikowi w drawArea
        // levels.get(currentLevelPosition).getPatternPic() <- to jest obrazek pattern ktory potrzebujesz do porownywania


        if (levels.get(currentLevelPosition).isCompleted()) {
            System.out.println("Literka " + levels.get(currentLevelPosition).getDescription() + " " + levels.get(currentLevelPosition).isCompleted());
            completedImageView.setImageResource(R.drawable.accept);
            completedImageView.setVisibility(View.VISIBLE);
        } else if(!levels.get(currentLevelPosition).isCompleted()){
            System.out.println("Literka " + levels.get(currentLevelPosition).getDescription() + " " + levels.get(currentLevelPosition).isCompleted());
            completedImageView.setVisibility(View.INVISIBLE);
            completedImageView.invalidate();
        }


        view.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            @Override
            public void onSwipeLeft() {

                Initializer.learnTypesList.get(learnPosition).getVariants().get(variantPosition).setCurrentLevel(++currentLevelPosition);
                Intent intent = new Intent(DrawActivity.this, DrawActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                try {
                    if (LevelBrowser.gridView != null)
                        LevelBrowser.gridView.invalidateViews();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                startActivity(intent);


            }

            @Override
            public void onSwipeRight() {

                Initializer.learnTypesList.get(learnPosition).getVariants().get(variantPosition).setCurrentLevel(--currentLevelPosition);
                Intent intent = new Intent(DrawActivity.this, DrawActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                try {
                    if (LevelBrowser.gridView != null)
                        LevelBrowser.gridView.invalidateViews();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> result = null;


                try {
                    result = drawArea.checkModel();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Float procentage = drawArea.getPercentage();
                procentage = procentage * 10;

                Bundle args = new Bundle();
                args.putStringArrayList("result", (ArrayList<String>) result);
                args.putInt("levelPosition", currentLevelPosition);
                args.putFloat("percentage", procentage);
                args.putInt("learnPosition", learnPosition);
                args.putInt("variantPosition", variantPosition);
                args.putString("targetValue", levels.get(currentLevelPosition).getDescription());
                args.putInt("streakCount", streakCount);
                //args.putSerializable("activity");
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

        Handler handler = new Handler();


        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textToSpeechInterpreter.speak(levels.get(currentLevelPosition).getDescription());
                    }
                }, 500);


            }
        });


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textToSpeechInterpreter.speak(levels.get(currentLevelPosition).getDescription());
            }
        }, 1000);

        setupStreakView(streakCount);

    }

    private void setupStreakView(int streakCount){

        boolean secondLevelPrize = false;
        boolean thirdLevelPrize = false;

        ImageView streak1 = findViewById(R.id.streak1);
        ImageView streak2 = findViewById(R.id.streak2);
        ImageView streak3 = findViewById(R.id.streak3);

        List<ImageView> streakFieldsImageView = new ArrayList<>();

        streakFieldsImageView.add(streak1);
        streakFieldsImageView.add(streak2);
        streakFieldsImageView.add(streak3);

        if(streakCount > 6){
            streakCount = streakCount - 6;
            thirdLevelPrize= true;

        } else if(streakCount>3){
            streakCount = streakCount -3;
            secondLevelPrize= true;
        }

        for (int i = 0; i < streakCount && i < 3; i++) {

            if(thirdLevelPrize){
                streakFieldsImageView.get(i).setImageResource(R.drawable.crown);
            }
            else if(secondLevelPrize){
                streakFieldsImageView.get(i).setImageResource(R.drawable.apple);
            }
            else{
                streakFieldsImageView.get(i).setImageResource(R.drawable.pear);
            }

            streakFieldsImageView.get(i).setVisibility(View.VISIBLE);

        }

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
            case all:
                if(fromMenu)
                    currentLevelPosition = position;
                else
                    currentLevelPosition = learnVariant.getCurrentLevel();
        }
    }

}
