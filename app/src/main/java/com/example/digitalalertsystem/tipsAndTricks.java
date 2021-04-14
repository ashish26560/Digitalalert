package com.example.digitalalertsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class tipsAndTricks extends navigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tips_and_tricks);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.activity_tips_and_tricks, null, false);
        drawer.addView(v, 0);

    }
}