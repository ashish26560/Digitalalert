package com.example.digitalalertsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class CreateAccount extends navigationActivity {

    Button Submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_account);
        LayoutInflater inflater = LayoutInflater.from(this);
        View cv = inflater.inflate(R.layout.activity_create_account, null, false);
        drawer.addView(cv,2);


        Submit = findViewById(R.id.Submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View cv) {
                openLoginPage();
            }
        });

    }
    public void openLoginPage(){
        Intent intent =new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}