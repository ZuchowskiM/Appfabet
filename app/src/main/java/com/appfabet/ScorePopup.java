package com.appfabet;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class ScorePopup extends AppCompatDialogFragment {

    Button accept;
    Button retry;
    TextView scoreTextView;
    TextView infoTextView;
    TextView Close;
    boolean btnEnabled;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.popup_score, container, false);

        accept = view.findViewById(R.id.acceptBtn);
        retry = view.findViewById(R.id.retryBtn);
        scoreTextView = view.findViewById(R.id.score);
        infoTextView = view.findViewById(R.id.infoTextView);
        Close = (TextView) view.findViewById(R.id.close);


        //odbieranie wartosci przyznanych przez model
        Bundle bundle=getArguments();
        if (bundle != null) {
            String charRecon = bundle.getString("result");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Recognized character: ").append(charRecon);
            infoTextView.setText(String.valueOf(stringBuilder));
        }
        if (bundle != null) {
           Float score =  bundle.getFloat("procentage");
           int scoreInt = Math.round(score);
           String value = String.valueOf(scoreInt + " / " + "10");

           if(scoreInt>6){
               scoreTextView.setTextColor(Color.GREEN);
               btnEnabled=true;
           }
           if(scoreInt<7){
               scoreTextView.setTextColor(Color.RED);
               btnEnabled=false;
           }


           scoreTextView.setText(value);
        }

        //zamykanie okna
        Close.setOnClickListener(v -> Objects.requireNonNull(getDialog()).dismiss());

        //ponawianie
        retry.setOnClickListener(v -> Objects.requireNonNull(getDialog()).dismiss());

        if(btnEnabled)
        {
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {

        }


        return view;
    }
}