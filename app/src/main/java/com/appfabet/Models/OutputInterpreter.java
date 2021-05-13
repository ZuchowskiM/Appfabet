package com.appfabet.Models;

import android.content.Context;
import com.appfabet.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class OutputInterpreter {

    private Context context;
    private  Map<String, Object> son;
    private InputStream currentDictionary;

    public OutputInterpreter(Context context) {
        this.context = context;
    }

    private void createArrayFromJson() throws IOException {

        JsonReader reader = new JsonReader(new InputStreamReader(currentDictionary, StandardCharsets.UTF_8));
        Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
        son = new Gson().fromJson(reader, mapType);

        //DEBUG
//        for (Map.Entry<String, Object> entry : son.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
//        }

    }

    public String getResultFromBigDictionary(int dictionaryIndex) throws IOException {

        currentDictionary = context.getResources().openRawResource(R.raw.emnist_big_en);

        createArrayFromJson();

        return son.get(String.valueOf(dictionaryIndex)).toString();
    }

    public String getResultFromSmallDictionary(int dictionaryIndex) throws IOException {
        currentDictionary = context.getResources().openRawResource(R.raw.emnist_small_en);

        createArrayFromJson();


        return son.get(String.valueOf(dictionaryIndex)).toString();
    }

    public String getResultFromNumberDictionary(int dictionaryIndex) throws IOException {

        currentDictionary = context.getResources().openRawResource(R.raw.emnist_dictionary);

        createArrayFromJson();


        return son.get(String.valueOf(dictionaryIndex)).toString();
    }



}
