package com.example.digitalalertsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    public Button Register;
    public LinearLayout Login;
    public EditText Email, Password, ReEnterPassword,FullName, UserPhoneNo, EmergencyPhoneNo1, EmergencyPhoneNo2;
    public FirebaseAuth Auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View v = inflater.inflate(R.layout.activity_register, null, false);
//        drawer.addView(v, 0);

        FullName = findViewById(R.id.fullNametxt);
        Email = findViewById(R.id.emailtxt);
        UserPhoneNo = findViewById(R.id.userPhoneNotxt);
        EmergencyPhoneNo1 = findViewById(R.id.emergencyPhoneNo1txt);
        EmergencyPhoneNo2 = findViewById(R.id.emergencyPhoneNo2txt);
        Password = findViewById(R.id.passwordtxt);
        ReEnterPassword = findViewById(R.id.reEnterPasswordtxt);
        Register = findViewById(R.id.registerbtn);
        Login = findViewById(R.id.loginbtn);
        Auth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Remember", "false");
                editor.apply();

                final String fullname = FullName.getText().toString().trim();
                final String userPhoneNo = UserPhoneNo.getText().toString().trim();
                final String email= Email.getText().toString().trim();
                final String emergencyPhoneNo1 = EmergencyPhoneNo1.getText().toString().trim();
                final String emergencyPhoneNo2 = EmergencyPhoneNo2.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String reEnterPassword = ReEnterPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(fullname) && !TextUtils.isEmpty(userPhoneNo) && !TextUtils.isEmpty(emergencyPhoneNo1) && !TextUtils.isEmpty(emergencyPhoneNo2) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(reEnterPassword)) {
                    if (password.equals(reEnterPassword)) {
                        //create authantication with email and password
                        Auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //store value in the database if task is successfull

                                    User user = new User(fullname,userPhoneNo,email,emergencyPhoneNo1,emergencyPhoneNo2);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this, "User has been Register successfully", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(RegisterActivity.this, "Failed to Register! Try again", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    Auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                //Auto signout if task is successfull
                                                Auth.signOut();
                                                Toast.makeText(RegisterActivity.this, "please check ur Email", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            } else {

                                                Toast.makeText(RegisterActivity.this, "Kindly enter valid Email  or link verify ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Something Is Wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Please enter right password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Please enter Data!!", Toast.LENGTH_SHORT).show();
                }
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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}