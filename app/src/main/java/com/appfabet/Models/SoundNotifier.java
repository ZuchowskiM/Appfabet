package com.appfabet.Models;


import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.appfabet.R;

public class SoundNotifier {

    private AudioAttributes audioAttributes;
    private SoundPool soundPool;
    private Context context;
    private int successSoundID;
    private int failSoundID;

    public SoundNotifier(Context context){

        this.context = context;
        createSoundPool();
        successSoundID = soundPool.load(context, R.raw.success2, 1);
        failSoundID = soundPool.load(context, R.raw.failure, 1);
    }
    
    private void createSoundPool(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            createNewSoundPool();
        }
        else{
            createOldSoundPool();
        }
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .build();

    }

    public void playSuccessNotifier(){

        soundPool.play(successSoundID, 1, 1,0,0,1);
    }

    public void playFailNotifier(){
        soundPool.play(failSoundID, 1, 1,0,0,1);
    }

}
