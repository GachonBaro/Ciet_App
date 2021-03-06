package com.barofutures.seco;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.barofutures.seco.googlemap.PermissionUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.material.navigation.NavigationView;
import com.skydoves.progressview.ProgressView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SteppingAuthActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener,
        SensorEventListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private Toolbar toolbar;
    private TextView titleTextView;                     // title TextView
    private ProgressView altitudeProgressView;          // ???????????? ?????? ???
    private TextView endAltitudeText;                   // ?????? ??????
    private TextView currentAltitudeText;               // ?????? ????????????
    private Button startButton;                         // ????????????
    private Button pauseButton;                         // ???????????? ??????
    private Button stopButton;                          // ???????????? ??????

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private FusedLocationProviderApi mFusedLocationProviderApi;
    private boolean mPermissionDenied;
    private LocationManager mLocationManager;
    private LocationCallback locationCallback;
    private Marker mCurrentMarker;
    private boolean state = false;                    // ?????? ??????
    private double startVerticalAccuracyMeters;         // ?????? ??? ??????
    private double currentVerticalAccuracyMeters;         // ?????? ??????

    private String startTime;       // ?????? ??????
    private String endTime;          // ?????? ??????
    private double targetDistance;  // ?????? ??????: (m)??????
    private double sumOfDistance;   // ?????? ????????????
    private double currentDistance; // currentVerticalAccuracyMeters - startVerticalAccuracyMeters
    private double postValue;       // ?????? ????????? currentDistance ???

    private long waitTime = 0;      // back button ?????? ??????

    private String title;           // ?????? ??????
    private String badgeNum;        // 1??? ?????? ??? ?????? ??? ?????? ?????? ???
    private String carbonReduction;     // 1??? ?????? ??? ?????? ?????????

    private SensorManager sensorManager;
    private Sensor pressure;        // ?????? ??????
//    private Sensor stepCounter;     // ?????? ??? ?????? ??????
//    private Sensor temperature;     // ?????? ??????

    private static final double seaLevelPressure = 1013.25;
    private static final double temperature = 15;
    private double startAltitude = 0.0;
    private double currentAltitude = 0.0;
    private double currentTotalDistance = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepping_auth);

        // ????????? ?????? ??????
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar ??????
        toolbar=findViewById(R.id.activity_stepping_auth_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Status Bar ???????????? Padding ??????
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        titleTextView = findViewById(R.id.activity_stepping_auth_title);
        altitudeProgressView = findViewById(R.id.activity_stepping_auth_distance_progressview);
        endAltitudeText = findViewById(R.id.altitude_end_textview);
        currentAltitudeText = findViewById(R.id.current_altitude_textview);
        startButton = findViewById(R.id.activity_stepping_auth_start_button);
        pauseButton = findViewById(R.id.activity_stepping_auth_pause_button);
        stopButton = findViewById(R.id.activity_stepping_auth_stop_button);

        // intent??? ?????? ?????? ??? ???????????? ??????
        Intent authIntent = getIntent();
        badgeNum = authIntent.getExtras().getString("badgeNum");
        title = authIntent.getExtras().getString("title");
        carbonReduction = authIntent.getExtras().getString("carbonReduction");

        titleTextView.setText(title + " ????????????");
        String temp = authIntent.getExtras().getString("badgeCriteria");
        temp = temp.substring(0, temp.length() - 1);
        targetDistance = Double.valueOf(temp) * 4;      // ??? ?????? ??? 4m

        sumOfDistance = 0.0;
        currentDistance = 0.0;
        postValue = 0.0;

        // progressView Max ?????? ?????? ????????? ??????
        altitudeProgressView.setMax((float) targetDistance);
        endAltitudeText.setText(String.valueOf(targetDistance) + "m" + "\n(12???)");

        // ???????????? listener
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = getTime();
                setState(0);        // ?????? ????????? ??????
                Toast.makeText(getApplicationContext(), "????????? ???????????????", Toast.LENGTH_SHORT).show();
            }
        });
        // ????????????/???????????? ?????? listener
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == true) {
                    setState(1);        // ????????? -> ???????????? ????????? ??????
                } else {
                    setState(2);        // ???????????? -> ????????? ????????? ??????
                }

            }
        });
        // ???????????? ?????? listener
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setState(3);        // ????????? ????????? ??????
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        createLocationRequest();

        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
//        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        // ????????? ?????? ????????? ?????? ?????? activity ??????
        if (pressure == null) {
            Toast.makeText(getApplicationContext(), "??? ????????? ?????? ????????? ???????????? ?????? ?????? ????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show();
            finish();
        }
//        // ????????? ?????? ????????? ?????? ?????? activity ??????
//        if (temperature == null) {
//            Toast.makeText(getApplicationContext(), "??? ????????? ?????? ????????? ???????????? ?????? ?????? ????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show();
//            finish();
//        }

//        // ????????? ????????? ?????? ????????? ?????? ?????? activity ??????
//        if (stepCounter == null) {
//            Toast.makeText(getApplicationContext(), "??? ????????? ????????? ?????? ????????? ???????????? ?????? ?????? ????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show();
//            finish();
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        // Register a listener for the sensor.
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        // Be sure to unregister the sensor when the activity pauses.
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register a listener for the sensor.
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Be sure to unregister the sensor when the activity pauses.
        sensorManager.unregisterListener(this);
    }

    // get current time
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        String getTime = dateFormat.format(date);
        return getTime;
    }

    // ?????? ?????? (0 - ??????, 1 - ????????????, 2 - ????????????, 3 - ?????????)
    public void setState(int code) {
        if (code == 0) {
            state = true;

            // ?????? ????????? ??????????????? ??????
            startAltitude = currentAltitude;

//            Toast.makeText(getApplicationContext(), "startAltitude = " + String.valueOf(startAltitude), Toast.LENGTH_SHORT).show();


            // ?????? ?????? ??????
            startButton.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.VISIBLE);
        }else if (code == 1) {
            state = false;

            // ?????? ?????? ?????? ??????
//            sumOfDistance += currentDistance;
            sumOfDistance = currentTotalDistance;
            postValue = 0.0;

            // ?????? ?????? ??????
            pauseButton.setText("????????????");
        }else if (code == 2) {
            // ?????? ????????? ??????????????? ??????
            startAltitude = currentAltitude;

            state = true;

//            Toast.makeText(getApplicationContext(), "startAltitude = " + String.valueOf(startAltitude), Toast.LENGTH_SHORT).show();

            // ?????? ?????? ??????
            pauseButton.setText("????????????");
        }else if (code == 3) {
            state = false;
            // ???????????? ??????
            finish();
        }
    }


    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            // Start location updates.
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);

            if (mCurrentLocation != null) {
                Log.i("Location", "Latitude: " + mCurrentLocation.getLatitude()
                        + ", Longitude: " + mCurrentLocation.getLongitude());
            }
        }
    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar??? back??? ????????? ??? ??????
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //status bar??? ?????? ??????
    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        enableMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitude = location.getLatitude(), longtitude = location.getLongitude();

        if (mCurrentMarker != null) mCurrentMarker.remove();
        mCurrentLocation = location;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(latitude, longtitude));
        mCurrentMarker =  mMap.addMarker(markerOptions);


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 18));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }

    }

    /*
     * back button click event
     */
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - waitTime >= 1500 ) {
            waitTime = System.currentTimeMillis();
            Toast.makeText(this,"???????????? ????????? ?????? ??? ????????? ???????????????.",Toast.LENGTH_SHORT).show();
        } else {
            finish(); // ???????????? ??????
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float millibarsOfPressure = event.values[0];
        currentAltitude = getAltitude(millibarsOfPressure);

        // Do something with this sensor data.
//        Log.d("SteppingAuthActivity22", "Pressure = " + millibarsOfPressure);
//        Toast.makeText(getApplicationContext(), "Pressure = " + millibarsOfPressure, Toast.LENGTH_SHORT).show();

        // get Latitude
        Log.d("SteppingAuthActivity22", "Altitude = " + getAltitude(millibarsOfPressure));
//        Toast.makeText(getApplicationContext(), "Altitude = " + getAltitude(millibarsOfPressure), Toast.LENGTH_SHORT).show();

        if (state) {                        // ?????? ?????? ???


            // ???????????? ?????? (?????? ?????? - ?????? ??????)
            if (currentAltitude > startAltitude) {
                currentDistance = Math.abs(currentAltitude - startAltitude);
            }

            Log.d("SteppingAuthActivity22", "currentDistance = " + currentDistance);

            double tempCurrentTotalDistance = sumOfDistance + currentDistance;

            if (postValue < currentDistance) {
                // update progressView and textView
                if (tempCurrentTotalDistance > targetDistance) {
                    altitudeProgressView.setProgress(((float) targetDistance));
                    altitudeProgressView.setLabelText(String.format("%1.2f", targetDistance) + "m");
                    currentAltitudeText.setText(String.format("%1.2f", targetDistance) + "m");
                } else {
                    altitudeProgressView.setProgress(((float) (tempCurrentTotalDistance)));
                    altitudeProgressView.setLabelText(String.format("%1.2f", tempCurrentTotalDistance) + "m");
                    currentAltitudeText.setText(String.format("%1.2f", tempCurrentTotalDistance) + "m");
                }
                postValue = currentDistance;

                Log.d("SteppingAuthActivity22", "????????????, sumOfDistance = " + sumOfDistance
                        + ", currentDistance = " + currentDistance + ", tempCurrentTotalDistance = " + String.format("%1.2f", tempCurrentTotalDistance));
                currentTotalDistance = tempCurrentTotalDistance;
            }

            // ?????? ??????: ???????????? >= ????????????
            if (targetDistance <= (currentTotalDistance)) {
                endTime = getTime();        // ?????? ?????? ?????? ??????
                Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "s: " + startTime + ", e: " + endTime, Toast.LENGTH_SHORT).show();

                Intent authIntent = new Intent(getApplicationContext(), AuthCompletionActivity.class);
                authIntent.putExtra("title", title);
                authIntent.putExtra("badgeNum", badgeNum);
                authIntent.putExtra("carbonReduction", carbonReduction);
                authIntent.putExtra("startTime", startTime);
                authIntent.putExtra("endTime", endTime);
                startActivity(authIntent);
                finish();
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    // input pressure??? ?????? ????????? ??????????????? method
    private double getAltitude(double pressure) {
        double altitude = ((Math.pow(seaLevelPressure/pressure, 1/5.257) - 1) * (temperature + 273.15)) / 0.0065;
        return altitude;
    }
}