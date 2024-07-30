package com.inhatc.greenupreal2;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inhatc.greenupreal2.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLng objLocation;
    private Button btnSetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnSetLocation = findViewById(R.id.btnSetLocation);
        btnSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (objLocation != null) {
                    String address = getAddressFromLatLng(objLocation);
                    Intent intent = new Intent();
                    intent.putExtra("location", address);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(MapsActivity.this, "Current location not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

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
    }

    private void mAlertProvider(String strProvider) {
        Toast.makeText(this, "Changing location Service : " + strProvider, Toast.LENGTH_LONG).show();
    }

    private void mAlertStatus(String strProvider) {
        Toast.makeText(this, strProvider + " Starting location service !", Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, strProvider + " Location service turn off ... " +
                "Please Turn on location service...", Toast.LENGTH_SHORT).show();

        Intent objIntent;
        objIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(objIntent);
    }

    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                return address.getAddressLine(0); // 주소의 첫번째 줄을 반환
            } else {
                return "Address not found";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Geocoder service not available";
        }
    }
}
