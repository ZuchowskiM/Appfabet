package com.appfabet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.appfabet.Adapters.LearnAdapter;
import com.appfabet.Adapters.LevelAdapter;
import com.appfabet.Models.DrawArea;
import com.appfabet.Models.Initializer;
import com.appfabet.Models.LearnType;
import com.appfabet.Models.Level;
import com.appfabet.Models.ScreenOptions;

import java.io.Serializable;
import java.util.ArrayList;


public class LevelBrowser extends Activity {

    static int position;
    static int learnPosition;
    static int variantPosition;
    String learnType = null;
    static GridView gridView;
    ArrayList<Level> levels = new ArrayList<>();


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       setContentView(R.layout.fragment_level_browser);

       gridView = findViewById(R.id.gridView);


        ScreenOptions screenOptions = new ScreenOptions();

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("TYPE");
        if(args!=null)
        {
            learnPosition = args.getInt("learnPosition");
            variantPosition = args.getInt("variantPosition");
            try{
                learnType=args.getString("LearnType");
                if(learnType.equals("Trening")){

                }

            } catch (Exception e){

            }

        }



        //curr level
        levels = (ArrayList<Level>) Initializer.learnTypesList.get(learnPosition).getVariants().get(variantPosition).getLevels();
        System.out.println("learnPosition " + learnPosition);
        System.out.println("variantPosition " + variantPosition);
        System.out.println("rozmiar" + levels.size());


        //adapter
        LevelAdapter myAdapter=new LevelAdapter(this,R.layout.grid_view_level_item,levels);
        gridView.setAdapter(myAdapter);


        int colums = screenOptions.measureCellWidth(this);

        if(colums>myAdapter.getCount()){
            colums = myAdapter.getCount();
        }
        gridView.setNumColumns(colums+1);

        if(args!=null) {
            try{
                if(learnType.equals("Trening")){
                    if(colums>myAdapter.getCount()){
                        colums = myAdapter.getCount();
                    }
                    gridView.setNumColumns(colums);
                }
            } catch (Exception e){}
        }





        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(LevelBrowser.this, DrawActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("TYPEOBJECT",(Serializable)levels.get(position));
                    args.putInt("learnPosition", learnPosition);
                    args.putInt("variantPosition", variantPosition);
                    args.putInt("levelPosition", position);
                    if(learnType!=null){
                        if(learnType.equals("Trening")){
                            args.putString("learnType", "Trening");
                        }
                    }
                    intent.putExtra("TYPE",args);
                    startActivity(intent);
                }
            });

        }

    }