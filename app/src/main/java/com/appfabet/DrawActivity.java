package com.appfabet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.appfabet.Models.DrawArea;
import com.appfabet.Models.Level;

public class DrawActivity extends Activity {

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


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = drawArea.checkModel();

                Toast.makeText(DrawActivity.this, "Recognized character: " + result, Toast.LENGTH_LONG).show();
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
