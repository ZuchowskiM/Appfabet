package com.appfabet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;

import com.appfabet.Adapters.LearnAdapter;
import com.appfabet.Models.LearnType;

import java.io.Serializable;
import java.util.ArrayList;

public class LearnSelector extends Activity {

    GridView gridView;
    ArrayList<LearnType> learnTypesList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_learn_selector);
        gridView = (GridView) findViewById(R.id.gridView);

        //inicjalizacja listy nauczania
        LearnType alfabet = new LearnType("Alfabet",R.drawable.alphabet_pic);
        LearnType cyferki = new LearnType("Cyferki",R.drawable.numbers_pic);

        learnTypesList.add(alfabet);
        learnTypesList.add(cyferki);


        //adapter
        LearnAdapter myAdapter=new LearnAdapter(this,R.layout.grid_view_items,learnTypesList);
        gridView.setAdapter(myAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(LearnSelector.this, LevelBrowser.class);
                Bundle args = new Bundle();
                args.putSerializable("TYPEOBJECT",(Serializable)learnTypesList.get(position));
                intent.putExtra("TYPE",args);
                startActivity(intent);
            }
        });

    }

}