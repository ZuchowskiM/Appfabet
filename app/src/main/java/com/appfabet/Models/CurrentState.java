package com.appfabet.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class CurrentState {

    public void setCurrentState(ArrayList<LearnType> data, FragmentActivity fragmentActivity){
        SharedPreferences sharedPreferences = Objects.requireNonNull(fragmentActivity.getSharedPreferences("CURRENT_STATE", MODE_PRIVATE));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String currentData = gson.toJson(data);
        editor.putString("DATA", currentData);
        editor.apply();
    }

    public ArrayList<LearnType> getCurrentState(FragmentActivity fragmentActivity){
        SharedPreferences sharedPreferences = Objects.requireNonNull(fragmentActivity.getSharedPreferences("CURRENT_STATE", MODE_PRIVATE));
        Gson gson = new Gson();
        String json = sharedPreferences.getString("DATA", null);
        Type type = new TypeToken<ArrayList<LearnType>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void deleteCurrentState(FragmentActivity fragmentActivity){
        SharedPreferences settings = Objects.requireNonNull(fragmentActivity.getSharedPreferences("CURRENT_STATE", Context.MODE_PRIVATE));
        settings.edit().clear().apply();
    }

}
