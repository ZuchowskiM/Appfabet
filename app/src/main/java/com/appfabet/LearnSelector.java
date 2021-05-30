package com.appfabet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appfabet.Adapters.LearnAdapter;
import com.appfabet.Models.CurrentState;
import com.appfabet.Models.Initializer;
import com.appfabet.Models.LearnType;
import com.appfabet.Models.ScreenOptions;

import java.io.Serializable;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class LearnSelector extends AppCompatActivity {

    GridView gridView;
    Initializer initializer = new Initializer();
    CurrentState currentState = new CurrentState();
    GifImageView gifImageView;
    ImageButton closeButton;
    ImageView pencilImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_learn_selector);


        ScreenOptions screenOptions = new ScreenOptions();

        gridView = (GridView) findViewById(R.id.gridView);
        gifImageView = findViewById(R.id.gifImage);
        closeButton = findViewById(R.id.buttonHide);
        pencilImage = findViewById(R.id.pencilImage);

        try{
            //DELETE STATES
            currentState.deleteCurrentState(this);

            Initializer.learnTypesList = currentState.getCurrentState(this);
            System.out.println("lista " + Initializer.learnTypesList);

            if(Initializer.learnTypesList.equals(null)){

                Initializer initializer = new Initializer();
                Initializer.learnTypesList = initializer.initLearnTypesAndLevels(getApplicationContext(), this);
                currentState.setCurrentState(Initializer.learnTypesList,this);
            }

        } catch (NullPointerException e){
            gridView.setVisibility(View.INVISIBLE);
            gifImageView.setVisibility(View.VISIBLE);
            closeButton.setVisibility(View.VISIBLE);
            pencilImage.setVisibility(View.VISIBLE);
            Initializer.firstTime=true;
            System.out.println("Null ptr");
            //init learn list if there was not previous state
            Initializer initializer = new Initializer();
            Initializer.learnTypesList = initializer.initLearnTypesAndLevels(getApplicationContext(), this);
            currentState.setCurrentState(Initializer.learnTypesList,this);
        } catch (Exception e1){
            System.out.println(e1.getMessage());
        }

        //adapter
        LearnAdapter myAdapter=new LearnAdapter(this,R.layout.grid_view_items,Initializer.learnTypesList);

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridView.setNumColumns(1);
        } else {

            int colums = screenOptions.measureCellWidth(this)-1;

            if(colums>myAdapter.getCount()){
                colums = myAdapter.getCount();
            }
            gridView.setNumColumns(colums);
        }

        gridView.setAdapter(myAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LearnSelector.this, LearnVariantsBrowser.class);
                Bundle args = new Bundle();
                args.putInt("position", position);
                intent.putExtra("TYPE",args);
                startActivity(intent);

            }
        });

        if(closeButton.getVisibility()==View.VISIBLE){
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeButton.setVisibility(View.INVISIBLE);
                    gifImageView.setVisibility(View.INVISIBLE);
                    pencilImage.setVisibility(View.INVISIBLE);
                    gridView.setVisibility(View.VISIBLE);
                }
            });
        }


    }


}