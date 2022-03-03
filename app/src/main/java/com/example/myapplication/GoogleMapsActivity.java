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

        //checkMapPermission();
    }

}