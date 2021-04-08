package com.appfabet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class StartScreen extends AppCompatActivity {

    private static int Time_Out = 3500;

    public StartScreen() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent (StartScreen.this, LearnSelector.class);
                startActivity(homeIntent);
                finish();
            }
        },Time_Out);
    }
}
