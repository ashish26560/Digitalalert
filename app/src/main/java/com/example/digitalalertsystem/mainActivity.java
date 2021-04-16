
package com.example.digitalalertsystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class mainActivity extends navigationActivity implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener, OnMapReadyCallback {


//my location button is the square button which is at the top right corner of the map

    //        Request code for Phone call permission request.
    private static final int REQUEST_PHONE_CALL = 2;

    //        Request code for location permission request.
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;/*ADD*/
    //    tells weather the permission to access location is denied or not
    private boolean permissionDenied = false;

//    private GoogleMap map;/*ADD*/

    private GoogleMap map;
    Button sendButton;
    Button callButton;
    Button callButton2;
    Button callFireStation;
//    for geting the latitude and longitude we use location manager and location listner in oMmapReady
    private LocationManager locationManager;
    private LocationListener locationListener;

    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;

    private LatLng latLng;

    String phoneNumber1;
    String phoneNumber2;
    String fireStation="101";
    String fullname;
    String myLatitude;
    String myLongitude;
    String message;

//    For Retrive Data from database (Niraj)
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        navigation drawer
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.activity_main, null, false);
        drawer.addView(v, 0);
//        buttons for sending location to defined number and call first emergency no
        sendButton = (Button) findViewById(R.id.sendButton);
        callButton = (Button) findViewById(R.id.callButton);
        callButton2 = (Button) findViewById(R.id.callButton2);
        callFireStation = (Button) findViewById(R.id.callFire);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("DIGITAL ALERT", "the button is clicked");
//                sending the sms
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber1, null, message, null, null);
                smsManager.sendTextMessage(phoneNumber2, null, message, null, null);
            }
        });


        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for just showing the number on diler pad it wont call until user calls it
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                if permission for phone call is granted the call will be initialized
                if (ActivityCompat.checkSelfPermission(mainActivity.this, Manifest.permission.CALL_PHONE) ==
                        PackageManager.PERMISSION_GRANTED) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phoneNumber1));//change the number.
                    startActivity(callIntent);
                }
//                asking for permission
                else {
                    ActivityCompat.requestPermissions(mainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);

//                    ActivityCompat.requestPermissions(mainActivity.this, new String[]{Manifest.permission.CALL_PHONE},123);

                }
            }
        });

        callButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(mainActivity.this, Manifest.permission.CALL_PHONE) ==
                        PackageManager.PERMISSION_GRANTED) {

                    Intent callIntent2 = new Intent(Intent.ACTION_CALL);
                    callIntent2.setData(Uri.parse("tel:" + phoneNumber2));//change the number.
                    startActivity(callIntent2);
                }
//                asking for permission
                else {
                    ActivityCompat.requestPermissions(mainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);

//                    ActivityCompat.requestPermissions(mainActivity.this, new String[]{Manifest.permission.CALL_PHONE},123);

                }

            }
        });
        callFireStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(mainActivity.this, Manifest.permission.CALL_PHONE) ==
                        PackageManager.PERMISSION_GRANTED) {

                    Intent callIntent2 = new Intent(Intent.ACTION_CALL);
                    callIntent2.setData(Uri.parse("tel:" + fireStation));//change the number.
                    startActivity(callIntent2);
                }
//                asking for permission
                else {
                    ActivityCompat.requestPermissions(mainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);

//                    ActivityCompat.requestPermissions(mainActivity.this, new String[]{Manifest.permission.CALL_PHONE},123);

                }

            }
        });

// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//checking permission for sms send
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//            Log.d("PLAYGROUND", "Permission is not granted, requesting");
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);
//            sen.setEnabled(false);
//        } else {
//            Log.d("PLAYGROUND", "Permission is granted");
//        }

//        For Retreving Data from database (Niraj)
//        getting info about the current logged in user
        user = FirebaseAuth.getInstance().getCurrentUser();
//        defining reference (giving destination where data is stored with user id)
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
//                getting the value and storing in string variable

                if (userprofile != null) {
                    phoneNumber1 = userprofile.emergencyPhoneNo1;
                    phoneNumber2 = userprofile.emergencyPhoneNo2;
                    fullname = userprofile.fullname;
                    //textView2.setText(email);
                }
            }
//            shows error if something went wrong
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mainActivity.this, "Something Wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();


// Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        locationListener = new LocationListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onLocationChanged(Location location) {
                try {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title("My Position"));
//
//
//
                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    //phoneNumber = "10 digit number";
                    myLatitude = String.valueOf(location.getLatitude());
                    myLongitude = String.valueOf(location.getLongitude());

                    //                    System.lineSeparator()

                    message = "ALERT!!! " + System.lineSeparator() +
                            "I Am ," + " " + fullname +" " + System.lineSeparator() +
                            "Please! Reach To Me As Soon As Posible." + System.lineSeparator() +
                            "Help Me." + System.lineSeparator() +
                            "https://maps.google.com/?q=" + myLatitude + "," + myLongitude;

//                    message = "ALERT!!! I AM IN ,"+fullname+ "PLEASE REACH TO ME AS SOON AS POSIBLE https://maps.google.com/?q=" + myLatitude + "," + myLongitude;
//                    Log.d("DIGITAL ALERT", "the button is clicked" + message);
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(phoneNumber,null,message,null,null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
//        will update th latitude and longitude with location listner and location manager

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }


//    Enables the My Location layer if the fine location permission has been granted
    private void enableMyLocation() {
//        checking the permission for accessing location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
//        permission for SMS service
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PLAYGROUND", "Permission is not granted, requesting");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);
//            sen.setEnabled(false);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            sendButton.setEnabled(true);
//            when permission is granted for SMS then only the button will be clickable
            Log.d("PLAYGROUND", "Permission is granted");
        } else {
            sendButton.setEnabled(false);

        }

    }

    /*ADD*/
//
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();

        // the camera shows user's current position.
        return false;
    }

    ///*ADD*/
    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    ///*ADD*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted
            enableMyLocation();
        } else {
            //if permission was denied display the missing permission error

            permissionDenied = true;

        }
    }

    //
///*ADD*/
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    //
//
////    Displays a dialog with error message explaining that the location permission is missing.
///*ADD*/
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
//
}
/**
 * below code is for my location button and will show blue dot with arrow pointing in direction of device
 * the above code possess the bellow code functionality with same code*/
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
//import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//
//
////  "My Location" button uses GMS Location to set the blue dot representing the users location.
//
//public class mainActivity extends navigationActivity
//        implements
//        OnMyLocationButtonClickListener,
//        OnMyLocationClickListener,
//        OnMapReadyCallback,
//        ActivityCompat.OnRequestPermissionsResultCallback {
//
//
////     Request code for location permission request.
//
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;/*ADD*/
//
//    // tells weather the permission to access location is denied or not
//    private boolean permissionDenied = false;
//
//    private GoogleMap map;/*ADD*/
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
//
////navigation drawer
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View v = inflater.inflate(R.layout.activity_main, null, false);
//        drawer.addView(v, 0);
////setting up the google map
//        SupportMapFragment mapFragment =
//                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }

/*ADD*/
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map = googleMap;
//        map.setOnMyLocationButtonClickListener(this);
//        map.setOnMyLocationClickListener(this);
//        enableMyLocation();
//    }
///*ADD*/
//
//    //    Enables the My Location layer if the fine location permission has been granted
//    private void enableMyLocation() {
////        checking the permission for accessing location
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            if (map != null) {
//                map.setMyLocationEnabled(true);
//            }
//        } else {
//            // Permission to access the location is missing Show rationale and request permission
//            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
//                    Manifest.permission.ACCESS_FINE_LOCATION, true);
//        }
//
//    }
/*ADD*/
//
//    @Override
//    public boolean onMyLocationButtonClick() {
//        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
//
//        // the camera shows user's current position.
//        return false;
//    }
///*ADD*/
//    @Override
//    public void onMyLocationClick(@NonNull Location location) {
//        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
//    }
//
///*ADD*/
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
//            return;
//        }
//
//        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
//            // Enable the my location layer if the permission has been granted
//            enableMyLocation();
//        } else {
//            //if permission was denied display the missing permission error
//
//            permissionDenied = true;
//
//        }
//    }
//
///*ADD*/
//    @Override
//    protected void onResumeFragments() {
//        super.onResumeFragments();
//        if (permissionDenied) {
//            // Permission was not granted display error dialog.
//            showMissingPermissionError();
//            permissionDenied = false;
//        }
//    }
//
//
////    Displays a dialog with error message explaining that the location permission is missing.
///*ADD*/
//    private void showMissingPermissionError() {
//        PermissionUtils.PermissionDeniedDialog
//                .newInstance(true).show(getSupportFragmentManager(), "dialog");
//    }
//
//}