package com.example.actc.appmoviestage1;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public final  class ParsingJsonReview {
    private ParsingJsonReview(){}

    /////we recieve the result string
    static     ArrayList<String> reviews = new ArrayList<String>();
    static   String compine;
    static   String author ;
    static   String content;
    static String reviewComment;
    public static String RESULTS = "results";
    public static  String AUTHOR = "author";
    public  static String  CONTENT ="content";


    public static ArrayList<String> parse(String response) throws Exception {


        JSONObject starter = new JSONObject(response);
        JSONArray result_array = starter.getJSONArray(RESULTS);
        for (int i = 0 ;i <result_array.length();i++)
        { JSONObject temp = result_array.getJSONObject(i);
            author = temp.getString(AUTHOR);
            content=temp.getString(CONTENT);
            compine=author+"   :  "+ content;
            reviews.add(compine);

        }





        return reviews;


    }


}



