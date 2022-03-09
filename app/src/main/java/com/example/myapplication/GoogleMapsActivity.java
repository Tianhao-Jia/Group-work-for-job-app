package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

public class GoogleMapsActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap map;
    private MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private FusedLocationProviderClient client; // gets location of your device

    // binds activity to the fragment in xml
    private String fineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
    private String coarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean isLocationSet = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        client = LocationServices.getFusedLocationProviderClient(this); // does the preprocessor steps for maps
        initGoogleMap(savedInstanceState);
        checkMapPermission();
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = (MapView) findViewById(R.id.googleMap);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
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
                getCurrentLocation();
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

    private void getCurrentLocation() {
        if(ContextCompat.checkSelfPermission(this, coarseLocation) != PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,fineLocation) != PackageManager.PERMISSION_GRANTED){
                return;
            }
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(location -> {
            Toast.makeText(this, "get curr location ", Toast.LENGTH_LONG).show();
            if(location != null){
                mapView.getMapAsync(googleMap -> {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You're here!");
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                    googleMap.addMarker(markerOptions).showInfoWindow();
                    Toast.makeText(this,"You are at " + location.getLatitude() + " " +  location.getLongitude(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker for Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        map.setMinZoomPreference(12f);

    }
}