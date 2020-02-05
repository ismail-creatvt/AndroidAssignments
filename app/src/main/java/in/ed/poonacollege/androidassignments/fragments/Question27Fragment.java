package in.ed.poonacollege.androidassignments.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import in.ed.poonacollege.androidassignments.R;

public class Question27Fragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {


    private static final int REQUEST_CHECK_SETTINGS = 2;
    private static final int REQUEST_LOCATION_PERMISSION = 4;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedClient;
    private LocationRequest locationRequest;

    public Question27Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question27, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment != null){
            mapFragment.getMapAsync(this);
        }

        view.findViewById(R.id.zoom_in).setOnClickListener(this);
        view.findViewById(R.id.zoom_out).setOnClickListener(this);
        view.findViewById(R.id.satellite_view).setOnClickListener(this);
        view.findViewById(R.id.terrain_view).setOnClickListener(this);

        fusedClient = new FusedLocationProviderClient(getContext());

        if(checkLocationPermissions()){
            checkLocationSettings();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void checkLocationSettings() {
        if(getContext() == null) return;
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(getContext());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(locationSettingsResponse -> {
            startLocationUpdates();
        });

        task.addOnFailureListener(e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(getActivity(),
                            REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException ignored) {
                }
            }
        });
    }

    private void startLocationUpdates() {
        fusedClient.requestLocationUpdates(locationRequest,
                new CurrentLocationCallback(),
                Looper.getMainLooper());
    }

    public class CurrentLocationCallback extends LocationCallback{
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            setLocation(locationResult.getLocations().get(0));
        }
    }

    private void setLocation(Location location) {
        if(location == null){
            Toast.makeText(getContext(), "Location not found", Toast.LENGTH_SHORT).show();
        } else{
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if(mMap != null){
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder().target(latLng).zoom(16f).build()));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CHECK_SETTINGS && resultCode == Activity.RESULT_OK){
            startLocationUpdates();
        }
    }

    private boolean checkLocationPermissions() {
        if(hasPermission()){
           return true;
        }
        String[] permissions = aboveQ() ? new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION}
                                        : new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        requestPermissions(permissions, REQUEST_LOCATION_PERMISSION);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_LOCATION_PERMISSION && hasPermission()){
            checkLocationSettings();
        }
    }

    private boolean aboveQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    private boolean hasPermission(){
        if(getContext() == null) return false;
        boolean hasBackgroundPermission;
        if(aboveQ()){
            hasBackgroundPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
        } else{
            hasBackgroundPermission = true;
        }
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                hasBackgroundPermission;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zoom_in:
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                break;
            case R.id.zoom_out:
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
                break;
            case R.id.satellite_view:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.terrain_view:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
    }
}
