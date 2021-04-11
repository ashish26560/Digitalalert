package com.example.digitalalertsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetpasswordActivity extends AppCompatActivity {


    Button click;
    EditText email;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        click=findViewById(R.id.Register);
        email=findViewById(R.id.e1);
        auth=FirebaseAuth.getInstance();

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass=email.getText().toString();
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