package com.example.digitalalertsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class navigationActivity extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navView;
    FirebaseAuth auth;
    private FirebaseUser user;

    private DatabaseReference reference;
    private String userID;
    //    for creating the actionbar
    ActionBarDrawerToggle toggle;

    String fullname;

    View mHeaderView;

    TextView Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

//        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.navView);


        // NavigationView Header
        mHeaderView =  navView.getHeaderView(0);


        // View
        Name = (TextView) mHeaderView.findViewById(R.id.username);
        setSupportActionBar(toolbar);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null) {
                    fullname = userprofile.fullname;
//                    userPhoneNo = userprofile.userPhoneNo;
//                    emergencyPhoneNo1 = userprofile.emergencyPhoneNo1;
//                    emergencyPhoneNo2 = userprofile.emergencyPhoneNo2;

                    Name.setText(fullname);
//                    UserPhoneNo.setText(userPhoneNo);
//                    EmergencyPhoneNo1.setText(emergencyPhoneNo1);
//                    EmergencyPhoneNo2.setText(emergencyPhoneNo2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(navigationActivity.this, "Something Wrong", Toast.LENGTH_LONG).show();
            }
        });


        // Set username & email
//        Name.setText(SharedPrefManager.getInstance(this).getUsername());


        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        Intent intent = new Intent(navigationActivity.this, mainActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.signout:

//when user signout the prefrence should be false so that login page could appear
                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Remember", "false");
                        editor.apply();
//signout the current user through firebase auth
                        auth.signOut();
                        Intent soutintent = new Intent(navigationActivity.this, LoginActivity.class);
                        startActivity(soutintent);
                        break;

                    case R.id.delaccount:

                        Deleteaccount();
                        break;
                    case R.id.updateprofile:

                        Intent mintent = new Intent(navigationActivity.this, updateProfile.class);
                        startActivity(mintent);
                        break;
                    case R.id.tipsandtricks:

                        Intent tipsintent = new Intent(navigationActivity.this, tipsAndTricks.class);
                        startActivity(tipsintent);
                        break;


//Paste your privacy policy link

//                    case  R.id.nav_Policy:{
//
//                        Intent browserIntent  = new Intent(Intent.ACTION_VIEW , Uri.parse(""));
//                        startActivity(browserIntent);
//
//                    }
                    //       break;
                    case R.id.nav_share: {

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));

                    }
                    break;
                }
                return false;
            }
        });


    }

    public void Deleteaccount() {
        AlertDialog.Builder dailog = new AlertDialog.Builder(navigationActivity.this);
        dailog.setTitle("Are You Sure??");
        dailog.setMessage("Deleting this account will result in completely removing your" +
                " account from the system and you won't be able to access the app:");

        dailog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Remember", "false");
                            editor.apply();

                            Toast.makeText(navigationActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(navigationActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(navigationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dailog.setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        AlertDialog alertDialog = dailog.create();
        alertDialog.show();
    }


}