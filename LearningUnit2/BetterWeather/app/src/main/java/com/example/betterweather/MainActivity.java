package com.example.betterweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.example.betterweather.Adapter.ViewPagerAdapter;
import com.example.betterweather.Common.Common;
import com.example.betterweather.Permissions.Permission;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Location";
    CoordinatorLayout coordinatorLayout;
    AppBarLayout appBarLayout;
    TabLayout tabLayout;
    Toolbar toolbar;
    ViewPager viewPager;
    private static final int REQUEST_CODE =1;

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.better_tabs);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        toolbar = findViewById(R.id.better_toolbar);
        setSupportActionBar(toolbar);


        String [] permsToRequest = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


        if (!Permission.hasPermissions(this,permsToRequest))
        {
            ActivityCompat.requestPermissions(this, permsToRequest, REQUEST_CODE);

        }


            buildLocationRequest();
            buildLocationCallBack();
           fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper());

    }


    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Fragment today  = today_weather.getInstance();
        adapter.addFragment(today_weather.getInstance(),"Today");

        adapter.addFragment(five_day_forecast.getInstance(),"5 day Forecast");
        adapter.addFragment(city_search_weather.getInstance(),"City Weather");

        viewPager.setAdapter(adapter);

    }

    private void buildLocationRequest()
    {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);
    }

    private void buildLocationCallBack()
    {
        locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Common.currentLocation = locationResult.getLastLocation();
                Log.i(TAG, "onLocationResult: " + locationResult.getLastLocation());
                Log.i(TAG, "onLocationResult: "+ Common.currentLocation);
                Log.i(TAG, "onLocationResult: "+ Common.currentLocation.getLatitude());
                viewPager = findViewById(R.id.better_viewpager);
                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
            }
        };

    }

}
