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
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.appfabet.Adapters.LearnAdapter;
import com.appfabet.Adapters.LevelAdapter;
import com.appfabet.Models.DrawArea;
import com.appfabet.Models.LearnType;
import com.appfabet.Models.Level;

import java.io.Serializable;
import java.util.ArrayList;


public class LevelBrowser extends Activity {


    GridView gridView;
    ArrayList<Level> levels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       setContentView(R.layout.fragment_level_browser);

       gridView = findViewById(R.id.gridView);


       Intent intent = getIntent();
       Bundle args = intent.getBundleExtra("TYPE");
       LearnType learnType = (LearnType)args.getSerializable("TYPEOBJECT");

        levels = (ArrayList<Level>) learnType.getLevels();

        //adapter
        LevelAdapter myAdapter=new LevelAdapter(this,R.layout.grid_view_level_item,levels);
        gridView.setAdapter(myAdapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(LevelBrowser.this, DrawActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("TYPEOBJECT",(Serializable)levels.get(position));
                intent.putExtra("TYPE",args);
                startActivity(intent);
            }
        });

        ///////////////////////////////

       // super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //Button button = findViewById(R.id.button2);
       // DrawArea drawArea = findViewById(R.id.drawing);

       // button.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //  public void onClick(View v) {
           //     drawArea.checkModel();
          //  }
       // });

    }
}