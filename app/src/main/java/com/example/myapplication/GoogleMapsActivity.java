package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.myapplication.databinding.ActivityGoogleMapsBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class GoogleMapsActivity extends AppCompatActivity{

    private GoogleMap map;
    // binds activity to the fragment in xml
    private ActivityGoogleMapsBinding binding; // TODO: is this same as ActivityMapBinding?
    private String fineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
    private String coarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean isLocationSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // linking activity and layout together
        binding = ActivityGoogleMapsBinding.inflate(getLayoutInflater());
        // this gets the entire xml file including buttons and other elements
        setContentView(binding.getRoot());

        checkMapPermission();
    }

    private void checkMapPermission() {
        // need to check if permission is granted
        // by default maps is only fine location permission so check if permission for FINE location is granted
        // you can't check for course location before checking for Fine location
        // you can only have Fine or both (coarse and fine)
        if(ContextCompat.checkSelfPermission(getApplicationContext(), fineLocation)
                == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getApplicationContext(), coarseLocation)
                    == PackageManager.PERMISSION_GRANTED){
                isLocationSet = true;
                //initializeMap();
            } else{
                // permission not yet granted. Request for permission
                ActivityCompat.requestPermissions(this, new String[]{fineLocation, coarseLocation}, 1234);
            }
        } else{
            // permission not yet granted. Request for permission
            ActivityCompat.requestPermissions(this, new String[]{fineLocation, coarseLocation}, 1234);
            // ActivityCompat gets permission only when this Activity is launched
            // ContextCompat will get the permission as soon as the app launches
        }
    }
}