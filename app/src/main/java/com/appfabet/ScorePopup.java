package com.appfabet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.appfabet.Models.Initializer;
import com.appfabet.Models.SoundNotifier;

import java.util.Objects;

public class ScorePopup extends AppCompatDialogFragment {

    Button accept;
    Button retry;
    ImageView scoreImageView;
    TextView infoTextView;
    TextView Close;
    boolean btnEnabled;
    int levelPosition;
    int variantPosition;
    int learnPosition;
    String targetValue;
    String charRecon;
    SoundNotifier soundNotifier;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.popup_score, container, false);

        accept = view.findViewById(R.id.acceptBtn);
        retry = view.findViewById(R.id.retryBtn);
        scoreImageView = view.findViewById(R.id.score);
        infoTextView = view.findViewById(R.id.infoTextView);
        Close = (TextView) view.findViewById(R.id.close);
        soundNotifier = new SoundNotifier(getContext());

        //odbieranie wartosci przyznanych przez model
        Bundle bundle=getArguments();
        if (bundle != null) {
            charRecon = bundle.getString("result");
            learnPosition = bundle.getInt("learnPosition");
            variantPosition = bundle.getInt("variantPosition");
            levelPosition = bundle.getInt("levelPosition");
            targetValue = bundle.getString("targetValue");

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Recognized character: ").append(charRecon);

        }
        if (bundle != null) {
           Float score =  bundle.getFloat("procentage");
           int scoreInt = Math.round(score);
           String value = String.valueOf(scoreInt + " / " + "10");

           if(targetValue.equals(charRecon))
           {
               if(scoreInt>=1 && scoreInt<4){
                   String message = "We think it's a " + charRecon + '\n' +
                           "Good job :D";
                   scoreImageView.setImageResource(R.drawable.smile);
                   infoTextView.setText(message);
                   btnEnabled=true;
               }
               if(scoreInt>=4 && scoreInt<8)
               {
                   String message = "We think it's a " + charRecon + '\n' +
                           "Great job :D";
                   scoreImageView.setImageResource(R.drawable.smile_ok1);
                   infoTextView.setText(message);
                   btnEnabled=true;
               }
               if(scoreInt>=8){
                   String message = "We think it's a " + charRecon + '\n' +
                           "Excellent job :D";
                   scoreImageView.setImageResource(R.drawable.smile_ok);
                   infoTextView.setText(message);
                   btnEnabled=true;
               }

               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       soundNotifier.playSuccessNotifier();
                   }
               }, 500);


           } else {
               btnEnabled=false;
               accept.setVisibility(View.GONE);
               scoreImageView.setImageResource(R.drawable.fail);
               String str = "We think it's a " + charRecon + " :(" + '\n' +
                       "Should be  " + targetValue ;
               infoTextView.setText(str);

               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       soundNotifier.playFailNotifier();
                   }
               }, 500);
           }

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
                    getDialog().dismiss();
                    Initializer.learnTypesList.get(learnPosition).getVariants().get(variantPosition).setCurrentLevel(++levelPosition);
                    Intent intent = new Intent(getActivity(), DrawActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        } else {

        }


        return view;
    }
}
