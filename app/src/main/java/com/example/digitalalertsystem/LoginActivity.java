package com.example.digitalalertsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText email,password;
    TextView reset;
    FirebaseAuth auth;
    LinearLayout register;

    CheckBox remember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        LayoutInflater inflater = LayoutInflater.from(this);
//        View v = inflater.inflate(R.layout.activity_login, null, false);
//        drawer.addView(v, 0);


        login=findViewById(R.id.Register);
        email=findViewById(R.id.e1);
        password=findViewById(R.id.e2);
        register=findViewById(R.id.SignUp);
        reset=findViewById(R.id.btn3);

        remember=findViewById(R.id.remember);

//      geting the value of shared prefrences
        SharedPreferences preferences= getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox=preferences.getString("remember","");

//if it is true it will redirect to the desired page and if false then will toast a msg
        if(checkbox.equals("true")){
            Intent intent =new Intent(LoginActivity.this,mainActivity.class);
            startActivity(intent);

        }else if(checkbox.equals("false")){
            Toast.makeText(this,"please sign in.",Toast.LENGTH_SHORT).show();

        }


        auth=FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass=email.getText().toString();
                String cpass=password.getText().toString();

                if(!TextUtils.isEmpty(pass)  && !TextUtils.isEmpty(cpass))
                {
                    auth.signInWithEmailAndPassword(pass,cpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                if(auth.getCurrentUser().isEmailVerified())
                                {
                                    Intent intent=new Intent(LoginActivity.this, mainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Please Verify Link via Email", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Email or Password is Incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Please Enter Data!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

        SpannableString spannableString=new SpannableString("forget password?");
        spannableString.setSpan(new UnderlineSpan(),0,spannableString.length(),0);
        reset.setText(spannableString);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this,ResetpasswordActivity.class);
                startActivity(intent);

            }
        });
//when user checks the checkbox true value is saved in user device & false for the unchecked
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "checked", Toast.LENGTH_SHORT).show();
                }else if (!compoundButton.isChecked()){

                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}