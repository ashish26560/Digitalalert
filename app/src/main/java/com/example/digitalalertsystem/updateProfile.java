package com.example.digitalalertsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
//import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateProfile extends AppCompatActivity {

    //For Retrive Data from database (Niraj)
    private FirebaseUser user;
    Toolbar updatetoolbar;

    private DatabaseReference reference;
    private String userID;

    TextInputEditText Fullname, UserPhoneNo, EmergencyPhoneNo1, EmergencyPhoneNo2;

    //Globle variable for hold USer data
    String fullname, userPhoneNo, emergencyPhoneNo1, emergencyPhoneNo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        updatetoolbar = findViewById(R.id.update);
        setSupportActionBar(updatetoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //navigation drawer
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View v = inflater.inflate(R.layout.activity_update_profile, null, false);
//        drawer.addView(v, 0);



        //For Retrive Data from database (Niraj)
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        Fullname = findViewById(R.id.fullnametxt);
        UserPhoneNo = findViewById(R.id.userPhoneNotxt);
        EmergencyPhoneNo1 = findViewById(R.id.emergencyPhoneNo1txt);
        EmergencyPhoneNo2 = findViewById(R.id.emergencyPhoneNo2txt);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null) {
                    fullname = userprofile.fullname;
                    userPhoneNo = userprofile.userPhoneNo;
                    emergencyPhoneNo1 = userprofile.emergencyPhoneNo1;
                    emergencyPhoneNo2 = userprofile.emergencyPhoneNo2;

                    Fullname.setText(fullname);
                    UserPhoneNo.setText(userPhoneNo);
                    EmergencyPhoneNo1.setText(emergencyPhoneNo1);
                    EmergencyPhoneNo2.setText(emergencyPhoneNo2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateProfile.this, "Something Wrong", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void updatebtn(View view) {
        if (isFullnameChanged()) {
            Toast.makeText(updateProfile.this, "Data has been Updated", Toast.LENGTH_LONG).show();
//            openHome();
        } else {
            Toast.makeText(updateProfile.this, "Data is same and can not be updated  ", Toast.LENGTH_LONG).show();
        }
        if ( isUserPhoneNoChanged()) {
            Toast.makeText(updateProfile.this, "Data has been Updated", Toast.LENGTH_LONG).show();
//            openHome();
        } else {
            Toast.makeText(updateProfile.this, "Data is same and can not be updated  ", Toast.LENGTH_LONG).show();
        }
        if (  isEmegencyNumber1Changed() ) {
            Toast.makeText(updateProfile.this, "Data has been Updated", Toast.LENGTH_LONG).show();
//            openHome();
        } else {
            Toast.makeText(updateProfile.this, "Data is same and can not be updated  ", Toast.LENGTH_LONG).show();
        }if ( isEmegencyNumber2Changed()) {
            Toast.makeText(updateProfile.this, "Data has been Updated", Toast.LENGTH_LONG).show();
//            openHome();
        } else {
            Toast.makeText(updateProfile.this, "Data is same and can not be updated  ", Toast.LENGTH_LONG).show();
        }
    }

//    private void openHome() {
//        Intent hintent = new Intent(updateProfile.this, mainActivity.class);
//        startActivity(hintent);
//
//    }

    private boolean isEmegencyNumber1Changed() {
        if (!emergencyPhoneNo1.equals(EmergencyPhoneNo1.getText().toString())) {
            reference.child(userID).child("emergencyPhoneNo1").setValue(EmergencyPhoneNo1.getText().toString());
            return true;
        } else {
            return false;
        }
    }
    private boolean isEmegencyNumber2Changed() {
        if (!emergencyPhoneNo2.equals(EmergencyPhoneNo2.getText().toString())) {
            reference.child(userID).child("emergencyPhoneNo2").setValue(EmergencyPhoneNo2.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean isUserPhoneNoChanged() {
        if (!userPhoneNo.equals(UserPhoneNo.getText().toString())) {
            reference.child(userID).child("userPhoneNo").setValue(UserPhoneNo.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean isFullnameChanged() {
        if (!fullname.equals(Fullname.getText().toString())) {
            reference.child(userID).child("fullname").setValue(Fullname.getText().toString());
            return true;
        } else {
            return false;
        }
    }
}