package com.appfabet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.appfabet.Models.ArticlesFinder;
import com.appfabet.Models.CurrentState;
import com.appfabet.Models.Initializer;
import com.appfabet.Models.RandomColorGenerator;
import com.appfabet.Models.SoundNotifier;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
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
    String learnType;
    String targetValue;
    ArrayList<String> charRecon;
    SoundNotifier soundNotifier;
    CurrentState currentState = new CurrentState();
    int streakCount;
    LinearLayout linearLayoutStreakCount;
    List<ImageView> streakFieldsImageView = new ArrayList<>();
    int longAnimationDuration;
    boolean correct;


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
        linearLayoutStreakCount = view.findViewById(R.id.LinearLayoutStreakCount);

        streakFieldsImageView.clear();
        streakFieldsImageView.add(view.findViewById(R.id.streakField1));
        streakFieldsImageView.add(view.findViewById(R.id.streakField2));
        streakFieldsImageView.add(view.findViewById(R.id.streakField3));

        for (ImageView i :
                streakFieldsImageView) {
            i.setVisibility(View.GONE);
        }

        longAnimationDuration = getResources().getInteger(android.R.integer.config_longAnimTime);


        //odbieranie wartosci przyznanych przez model
        Bundle bundle=getArguments();
        if (bundle != null) {
            RandomColorGenerator randomColorGenerator = new RandomColorGenerator();
            charRecon = bundle.getStringArrayList("result");
            learnPosition = bundle.getInt("learnPosition");
            correct = bundle.getBoolean("isGood");
            variantPosition = bundle.getInt("variantPosition");
            levelPosition = bundle.getInt("levelPosition");
            targetValue = bundle.getString("targetValue");
            streakCount = bundle.getInt("streakCount");

            try{
               learnType = bundle.getString("learnType");
               if(learnType!=null && learnType.equals("Trening")){
                   if(correct){
                       scoreImageView.setImageResource(R.drawable.smile);
                       infoTextView.setText("Brawo !");
                       infoTextView.setTextColor(randomColorGenerator.getColor());
                       btnEnabled=true;
                       streakCount++;

                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               soundNotifier.playSuccessNotifier();
                           }
                       }, 500);

                   } else{
                       scoreImageView.setImageResource(R.drawable.fail);
                       infoTextView.setText("Niestety to nie to :(");
                       infoTextView.setTextColor(randomColorGenerator.getColor());
                       btnEnabled=false;
                       accept.setVisibility(View.GONE);
                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               soundNotifier.playFailNotifier();
                           }
                       }, 500);
                   }


               } else {


                   Float score =  bundle.getFloat("percentage");
                   int scoreInt = Math.round(score);
                   String value = String.valueOf(scoreInt + " / " + "10");



                   if(charRecon.contains(targetValue))
                   {
                       if(scoreInt>=1 && scoreInt<4){
                           String message = //articlesFinder.getArticle(charRecon.get(0).charAt(0)) + charRecon.get(0) + '\n' +
                                   "Dobra robota :D";
                           scoreImageView.setImageResource(R.drawable.smile);
                           infoTextView.setText(message);
                           infoTextView.setTextColor(randomColorGenerator.getColor());
                           btnEnabled=true;
                       }
                       if(scoreInt>=4 && scoreInt<8)
                       {
                           String message = //articlesFinder.getArticle(charRecon.get(0).charAt(0)) + charRecon.get(0) + '\n' +
                                   "??wietnie :D";
                           scoreImageView.setImageResource(R.drawable.smile_ok1);
                           infoTextView.setText(message);
                           infoTextView.setTextColor(randomColorGenerator.getColor());
                           btnEnabled=true;
                       }
                       if(scoreInt>=8){
                           String message = //articlesFinder.getArticle(charRecon.get(0).charAt(0)) + charRecon.get(0) + '\n' +
                                   "Wybitnie! :D";
                           scoreImageView.setImageResource(R.drawable.smile_ok);
                           infoTextView.setText(message);
                           infoTextView.setTextColor(randomColorGenerator.getColor());
                           btnEnabled=true;
                       }

                       streakCount++;

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
                       String str = "My??limy, ??e to " + charRecon.get(0) + " :(" + '\n' +
                               "Powinno by?? " + targetValue ;
                       infoTextView.setText(str);
                       infoTextView.setTextColor(randomColorGenerator.getColor());

                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               soundNotifier.playFailNotifier();
                           }
                       }, 500);
                   }

               }
            } catch (Exception e){}

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Recognized character: ").append(charRecon);
        }





        setStreakCountImages(streakCount);


        //zamykanie okna
        Close.setOnClickListener(v -> Objects.requireNonNull(getDialog()).dismiss());

        //ponawianie
        retry.setOnClickListener(v -> Objects.requireNonNull(getDialog()).dismiss());

        if(btnEnabled)
        {
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Objects.requireNonNull(getDialog()).dismiss();
                    Initializer.learnTypesList.get(learnPosition).getVariants().get(variantPosition).getLevels().get(levelPosition).setCompleted(true);
                    if(levelPosition==Initializer.learnTypesList.get(learnPosition).getVariants().get(variantPosition).getLevels().size()-1){
                        Initializer.learnTypesList.get(learnPosition).getVariants().get(variantPosition).setCurrentLevel(0);
                    } else{
                        Initializer.learnTypesList.get(learnPosition).getVariants().get(variantPosition).setCurrentLevel(++levelPosition);
                    }
                    currentState.setCurrentState(Initializer.learnTypesList, getActivity());
                    Intent intent = new Intent(getActivity(), DrawActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


                    Bundle args = new Bundle();
                    if(learnType!=null && learnType.equals("Trening")){
                        args.putString("learnType","Trening");
                        System.out.println("Wysylam");
                    }

                    args.putInt("streakCount", streakCount);
                    intent.putExtra("STREAK", args);


                    try{
                        if(LevelBrowser.gridView!=null)
                            LevelBrowser.gridView.invalidateViews();
                    } catch (Exception e) { System.out.println(e.getMessage()); }
                    startActivity(intent);
                }
            });
        } else {

        }


        return view;
    }

    private void setStreakCountImages(int streakCount){

        System.out.println(streakCount);

        boolean secondLevelPrize = false;
        boolean thirdLevelPrize = false;

        if(streakCount > 6){
            streakCount = streakCount - 6;
            thirdLevelPrize= true;

        } else if(streakCount>3){
            streakCount = streakCount -3;
            secondLevelPrize= true;
        }

        for (int i = 0; i < streakCount && i < 3; i++) {

            if(thirdLevelPrize){
                streakFieldsImageView.get(i).setImageResource(R.drawable.crown);
            }
            else if(secondLevelPrize){
                streakFieldsImageView.get(i).setImageResource(R.drawable.apple);
            }
            else{
                streakFieldsImageView.get(i).setImageResource(R.drawable.pear);
            }

            animateImageViewToVisible(streakFieldsImageView.get(i), i);

        }
    }

    private void animateImageViewToVisible(ImageView imageView, int delayDurationMultiplier){

        imageView.setAlpha(0f);
        imageView.setVisibility(View.VISIBLE);

        imageView.animate()
                .alpha(1f)
                .setStartDelay(100 + delayDurationMultiplier*400)
                .setDuration(longAnimationDuration)
                .setListener(null);
    }
}
