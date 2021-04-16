package com.example.digitalalertsystem;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class tipsAndTricks extends AppCompatActivity {

    Toolbar tipsToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tips_and_tricks);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View v = inflater.inflate(R.layout.activity_tips_and_tricks, null, false);
//        drawer.addView(v, 0);
        tipsToolbar=findViewById(R.id.tipstoolbar);

        setSupportActionBar(tipsToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}