package com.appfabet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;

import com.appfabet.Adapters.LearnAdapter;
import com.appfabet.Models.CurrentState;
import com.appfabet.Models.Initializer;
import com.appfabet.Models.LearnType;

import java.io.Serializable;
import java.util.ArrayList;

public class LearnSelector extends AppCompatActivity {

    GridView gridView;
    Initializer initializer = new Initializer();
    CurrentState currentState = new CurrentState();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_learn_selector);
        gridView = (GridView) findViewById(R.id.gridView);


        try{
            Initializer.learnTypesList = currentState.getCurrentState(this);
            System.out.println("lista " + Initializer.learnTypesList);

            if(Initializer.learnTypesList.equals(null)){
                Initializer initializer = new Initializer();
                Initializer.learnTypesList = initializer.initLearnTypesAndLevels(getApplicationContext(), this);
                currentState.setCurrentState(Initializer.learnTypesList,this);
            }

        } catch (NullPointerException e){
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

    }

}