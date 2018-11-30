package com.example.actc.appmoviestage1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public final class ParsingJsonTrailer {
    private ParsingJsonTrailer(){}

    static String key ;
    public static String RESULTS = "results";
    String getKey(){return key;}
static  ArrayList<String> AllKeysTrailer = new ArrayList<String>();

    public static ArrayList<String> parse(String result) throws Exception {

        JSONObject starter = new JSONObject(result);

        JSONArray results = starter.getJSONArray(RESULTS);

        for(int i =0;i<results.length();i++) {
            JSONObject results_obj = results.getJSONObject(i);

            key = results_obj.getString("key");

            AllKeysTrailer.add(key);

        }

return  AllKeysTrailer;

    }

}
