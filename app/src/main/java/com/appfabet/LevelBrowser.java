package com.appfabet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appfabet.Models.DrawArea;
import com.appfabet.Models.LearnType;

import java.util.ArrayList;


public class LevelBrowser extends Activity {

    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.fragment_level_browser);
//
//        Intent intent = getIntent();
//        Bundle args = intent.getBundleExtra("TYPE");
//        LearnType learnType = (LearnType)args.getSerializable("TYPEOBJECT");
//
//        title = findViewById(R.id.title);
//
//        title.setText(learnType.getName());

        ///////////////////////////////


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button2);
        DrawArea drawArea = findViewById(R.id.drawing);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawArea.checkModel();
            }
        });

    }
}