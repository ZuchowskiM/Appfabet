package com.appfabet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.appfabet.Adapters.LearnAdapter;
import com.appfabet.Adapters.LearnVariantAdapter;
import com.appfabet.Models.Initializer;
import com.appfabet.Models.LearnType;
import com.appfabet.Models.LearnVariant;

import java.io.Serializable;
import java.util.ArrayList;

public class LearnVariantsBrowser extends AppCompatActivity {

    GridView gridView;
    ArrayList<LearnType> learnTypesList;
    static int learnPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_variants_browser);

        gridView = (GridView) findViewById(R.id.gridViewVariants);

        //inicjalizacja listy nauczania
        learnTypesList = Initializer.learnTypesList;

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("TYPE");
        learnPosition = args.getInt("position");
        


        //adapter
        LearnVariantAdapter myAdapter=new LearnVariantAdapter(this,R.layout.grid_view_items,learnTypesList.get(learnPosition).getVariants());
        gridView.setAdapter(myAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(learnTypesList.get(learnPosition).getVariants().get(position).getName().equals("All letters")){
                    Intent intent = new Intent(LearnVariantsBrowser.this, LevelBrowser.class);
                    Bundle args = new Bundle();
                    args.putInt("learnPosition", learnPosition);
                    args.putInt("variantPosition", position);
                    intent.putExtra("TYPE",args);
                    startActivity(intent);
                } else{

                    Intent intent = new Intent(LearnVariantsBrowser.this, DrawActivity.class);
                    Bundle args = new Bundle();
                    args.putInt("learnPosition", learnPosition);
                    args.putInt("variantPosition", position);
                    intent.putExtra("TYPE",args);
                    startActivity(intent);

                }

            }
        });

    }
}