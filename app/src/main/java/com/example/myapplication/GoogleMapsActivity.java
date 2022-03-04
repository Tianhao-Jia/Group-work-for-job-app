package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.ActivityGoogleMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class GoogleMapsActivity extends Fragment implements OnMapReadyCallback{

    private GoogleMap map;
    private MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    // binds activity to the fragment in xml
    private ActivityGoogleMapsBinding binding; // TODO: is this same as ActivityMapBinding?
    private String fineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
    private String coarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean isLocationSet = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = (MapView) getView().findViewById(R.id.googleMap);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_google_maps, container, false);
        mapView = view.findViewById(R.id.googleMap);

        initGoogleMap(savedInstanceState);

        return view;
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(this);
    }

//    private void checkMapPermission() {
//        // need to check if permission is granted
//        // by default maps is only fine location permission so check if permission for FINE location is granted
//        // you can't check for course location before checking for Fine location
//        // you can only have Fine or both (coarse and fine)
//        if(ContextCompat.checkSelfPermission(getApplicationContext(), fineLocation)
//                == PackageManager.PERMISSION_GRANTED){
//            if(ContextCompat.checkSelfPermission(getApplicationContext(), coarseLocation)
//                    == PackageManager.PERMISSION_GRANTED){
//                isLocationSet = true;
//                //pinpointUserLocation(map == null, map);
//            } else{
//                // permission not yet granted. Request for permission
//                ActivityCompat.requestPermissions(this, new String[]{fineLocation, coarseLocation}, 1234);
//            }
//        } else{
//            // permission not yet granted. Request for permission
//            ActivityCompat.requestPermissions(this, new String[]{fineLocation, coarseLocation}, 1234);
//            // ActivityCompat gets permission only when this Activity is launched
//            // ContextCompat will get the permission as soon as the app launches
//        }
//    }
//
//    private void pinpointUserLocation(boolean mapNotNull, GoogleMap googleMap) {
//        if (mapNotNull) {
////            map = googleMap;
////            map.setMinZoomPreference(12);
////            LatLng ny = new LatLng(40.7143528, -74.0059731);
////            map.moveCamera(CameraUpdateFactory.newLatLng(ny));
//        }
//    }
//
//    private void initializeMap(Bundle mapViewBundle) {
//        binding.googleMap.onCreate(mapViewBundle);
//        // initializing the map to the fragment we created in the xml file
//        binding.googleMap.getMapAsync(this);
//
//        // Note: if emulator doesn't have google play services, maps wont work
//    }

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
        map.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        map.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
}