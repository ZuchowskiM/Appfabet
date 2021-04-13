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

    public OutputInterpreter(Context context) {
        this.context = context;
    }

    public void createArrayFromJson() throws IOException {

        InputStream jsonFile = context.getResources().openRawResource(R.raw.emnist_pl_dictrionary);


        JsonReader reader = new JsonReader(new InputStreamReader(jsonFile, StandardCharsets.UTF_8));
        Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
        son = new Gson().fromJson(reader, mapType);

        //DEBUG
//        for (Map.Entry<String, Object> entry : son.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
//        }

    }

    public String getResultFromDictionary(int dictionaryIndex){

        return son.get(String.valueOf(dictionaryIndex)).toString();
    }



}
