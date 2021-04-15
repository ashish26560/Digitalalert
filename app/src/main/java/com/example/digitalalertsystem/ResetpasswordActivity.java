package com.example.digitalalertsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetpasswordActivity extends AppCompatActivity {


    Button Reset;
    EditText Email;
    FirebaseAuth auth;
    Toolbar resetToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        Reset=findViewById(R.id.reset);
        Email=findViewById(R.id.resetemail);
        resetToolbar=findViewById(R.id.resettoolbar);

        setSupportActionBar(resetToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth=FirebaseAuth.getInstance();

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass=Email.getText().toString();
                auth.sendPasswordResetEmail(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ResetpasswordActivity.this, "please check your email ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(ResetpasswordActivity.this, "somthing is wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }
}