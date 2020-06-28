package za.ac.iie.opsc.geoweather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import za.ac.iie.opsc.geoweather.model.FiveDayForecast;
import za.ac.iie.opsc.geoweather.model.location.AccuWeatherLocation;
import za.ac.iie.opsc.geoweather.retrofit.IAccuWeather;
import za.ac.iie.opsc.geoweather.retrofit.RetrofitClient;
import za.ac.iie.opsc.geoweather.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private IAccuWeather weatherService;
    private CompositeDisposable compositeDisposable;

    public MainActivity() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getRetrofit();
        weatherService = retrofit.create(IAccuWeather.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this);
        checkPermissionsAndRequestLocation();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action",
                        Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void checkPermissionsAndRequestLocation() {
        int hasFineLocationPermission = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCourseLocationPermission = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED &&
                hasCourseLocationPermission != PackageManager.PERMISSION_GRANTED) {
            final String [] permissions = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};
            // Request permission - this is asynchronous
            ActivityCompat.requestPermissions(this, permissions, 0);
        }
        else {
            // We have permission, so now ask for the location
            getLocationAndCreateUI();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // is this for our request?
        if (requestCode == 0) {
            if (grantResults.length > 0 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                getLocationAndCreateUI();
            }
            else {
                Toast.makeText(MainActivity.this,
                        "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocationAndCreateUI() {
        LocationRequest locationRequest = buildLocationRequest();
        LocationCallback locationCallback = buildLocationCallBack();
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback, Looper.myLooper());
    }

    private LocationRequest buildLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);
        return locationRequest;
    }

    private LocationCallback buildLocationCallBack() {
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                Log.i("LocationResult", "onLocationResult: " + location);

                // Make the call using Retrofit and RxJava
                compositeDisposable.add(weatherService.getLocationByPosition(
                        location.getLatitude() + "," +
                        location.getLongitude(),
                        BuildConfig.ACCUWEATHER_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AccuWeatherLocation>()
                {
                    @Override
                    public void accept(AccuWeatherLocation location) throws Exception {
                        displayData(location);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception
                    {
                        Log.d("MYERROR", "accept: " + throwable.getMessage());
                    }
                }));
            }
        };
        return locationCallback;
    }

    private void displayData(AccuWeatherLocation location) {
        SectionsPagerAdapter sectionsPagerAdapter =
                new SectionsPagerAdapter(MainActivity.this,
                        getSupportFragmentManager(),
                        location.getLocalizedName(),
                        location.getKey());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}