package com.appfabet.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.appfabet.DrawActivity;
import com.appfabet.Models.LearnType;
import com.appfabet.Models.Level;
import com.appfabet.Models.RandomColorGenerator;
import com.appfabet.R;

import java.util.ArrayList;

public class LevelAdapter extends ArrayAdapter {

    ArrayList<Level> learnList = new ArrayList<>();

    public LevelAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        learnList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RandomColorGenerator randomColorGenerator = new RandomColorGenerator();


        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.grid_view_level_item, null);
        //TextView textView = (TextView) v.findViewById(R.id.textView);
        TextView textView = (TextView) v.findViewById(R.id.patternPic);
        ImageView completedImage = (ImageView) v.findViewById(R.id.completedMark);
       // textView.setText(learnList.get(position).getName());
        textView.setText(learnList.get(position).getDescription());
        ImageView imageView = v.findViewById(R.id.patternPicThings);
        CardView cardView = v.findViewById(R.id.cardview);

        textView.setTextColor(randomColorGenerator.getColor());


        if (learnList.get(position).getName().equals("Things")){
            imageView.setImageResource(learnList.get(position).getResource());

            textView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);

            imageView.requestLayout();
            cardView.requestLayout();

        }

        if(learnList.get(position).isCompleted()) {
            completedImage.setImageResource(R.drawable.accept);
            completedImage.setVisibility(View.VISIBLE);
        } else if(!learnList.get(position).isCompleted()){
            completedImage.setVisibility(View.INVISIBLE);
        }

        return v;
    }
}