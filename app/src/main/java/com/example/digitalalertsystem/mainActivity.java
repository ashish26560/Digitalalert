package com.example.digitalalertsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class mainActivity extends navigationActivity {

    Button Login;
    Button Create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_delete);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.activity_main, null, false);
        drawer.addView(v, 1);

        Create = findViewById(R.id.createAccount);
        Login = findViewById(R.id.Register);
//        call = findViewById(R.id.callAmbulance);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationPage();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void openRegistrationPage() {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
