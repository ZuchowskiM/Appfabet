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
import com.appfabet.Models.Level;

public class DrawActivity extends AppCompatActivity {

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
        Level learnType = (Level) args.getSerializable("TYPEOBJECT");

        patternPic.setBackgroundResource(learnType.getResource());

        ScorePopup popup = new ScorePopup();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = drawArea.checkModel();
                Float procentage = drawArea.getProcentage();
                procentage = procentage * 10;

                Bundle args = new Bundle();
                args.putString("result", result);
                //args.putString("objective", );
                args.putFloat("percentage", procentage);

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

    }


}
