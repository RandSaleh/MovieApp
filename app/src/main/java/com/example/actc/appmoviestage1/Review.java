package com.example.actc.appmoviestage1;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class Review extends AppCompatActivity {

    LinearLayout parentReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews);
        parentReviews = (LinearLayout) findViewById(R.id.parentForReviews);

        ArrayList<String> recieve = new ArrayList<String>();


        Intent receieved = getIntent();
        if (receieved.hasExtra("READY_REVIEW"))

        {
            recieve = receieved.getStringArrayListExtra("READY_REVIEW");


            populate(recieve);


        }

    }


    //--- populate the arraylist

    public void populate(ArrayList<String> result) {
        TextView review;
        for (int i = 0; i < result.size(); i++) {

            review = new TextView(getApplicationContext());

            review.setText(result.get(i));
            review.setId(i + 1);
            review.setPadding(8,8,0,16);

            parentReviews.addView(review);


        }

    }
}
