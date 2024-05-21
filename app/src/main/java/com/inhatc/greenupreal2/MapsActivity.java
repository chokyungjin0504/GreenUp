package com.inhatc.greenupreal2;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inhatc.greenupreal2.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLng objLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

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
        mMap = googleMap;
        long minTime = 1000;
        float minDistance = 1;

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                mUpdateMap(location);
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {
                mAlertStatus(provider);
            }
            public void onProviderEnabled(String provider) {
                mAlertProvider(provider);
            }
            public void onProviderDisabled(String provider) {
                mCheckProvider(provider);
            }
        };


        LocationManager locationManager;
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }
        String strLocationProvider = LocationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(strLocationProvider, minTime, minDistance, locationListener);
        strLocationProvider = LocationManager.NETWORK_PROVIDER;
        locationManager.requestLocationUpdates(strLocationProvider, minTime, minDistance, locationListener);




        // Add a marker in Sydney and move the camera
//        LatLng latLng = new LatLng(37.4482372, 126.6575148);
//        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("inhaTC");
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f)); //확대
//        googleMap.addMarker(markerOptions); //마커
//
//        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

    }

    private void mAlertProvider(String strProvider) {
        Toast.makeText(this, "Changing location Service : " + strProvider, Toast.LENGTH_LONG).show();
    }

    private void mAlertStatus(String strProvider) {
        Toast.makeText(this, strProvider + "Starting location service !", Toast.LENGTH_LONG).show();
    }

    private void mUpdateMap(Location location) {
        double dLatitude = location.getLatitude();
        double dLongitude = location.getLongitude();
        objLocation = new LatLng(dLatitude, dLongitude);

        Marker objMK = mMap.addMarker(new MarkerOptions().position(objLocation).title("Current Position"));
        objMK.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(objLocation, 15f));
    }
    public void mCheckProvider(String strProvider) {
        Toast.makeText(this, strProvider + "Location service turn off ... " +
                "Please Turn on location service...", Toast.LENGTH_SHORT).show();

        Intent objIntent;
        objIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(objIntent);
    }


}