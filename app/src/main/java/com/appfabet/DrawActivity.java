package com.appfabet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("TYPE");
        Level learnType = (Level) args.getSerializable("TYPEOBJECT");




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawArea.checkModel();
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
