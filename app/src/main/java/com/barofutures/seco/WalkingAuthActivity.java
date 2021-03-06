/*
Copyright 2021 Baro Futures

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.barofutures.seco;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import com.google.android.datatransport.runtime.dagger.internal.Factory;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.navigation.NavigationView;
import com.skydoves.progressview.ProgressView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
public class WalkingAuthActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private Toolbar toolbar;
    private TextView titleTextView;                     // title TextView
    private ProgressView distanceProgressView;          // ???????????? ?????? ???
    private TextView endDistanceText;                   // ????????????
    private TextView currentDistanceText;               // ?????? ????????????
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
    private Marker mCurrentMarker;
    private LatLng startLatLng = new LatLng(0, 0);        //polyline ?????????
    private LatLng endLatLng = new LatLng(0, 0);        //polyline ??????
    private List<Polyline> polylineList;
    private boolean state = false;                    // ?????? ??????

    private String startTime;       // ?????? ??????
    private String endTime;          // ?????? ??????
    private double targetDistance;  // ?????? ??????: (m)??????
    private double sumOfDistance;   // ?????? ????????????

    private long waitTime = 0;      // back button ?????? ??????

    private String title;           // ?????? ??????
    private String badgeNum;        // 1??? ?????? ??? ?????? ??? ?????? ?????? ??????
    private String carbonReduction;     // 1??? ?????? ??? ?????? ?????????

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_auth);

        // ????????? ?????? ??????
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar ??????
        toolbar=findViewById(R.id.activity_walking_auth_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Status Bar ???????????? Padding ??????
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        polylineList = new ArrayList<Polyline>();

        titleTextView = findViewById(R.id.activity_walking_auth_title);
        distanceProgressView = findViewById(R.id.activity_walking_auth_distance_progressview);
        endDistanceText = findViewById(R.id.distance_end_textview);
        currentDistanceText = findViewById(R.id.current_distance_textview);
        startButton = findViewById(R.id.activity_walking_auth_start_button);
        pauseButton = findViewById(R.id.activity_walking_auth_pause_button);
        stopButton = findViewById(R.id.activity_walking_auth_stop_button);

        // intent??? ?????? ?????? ??? ???????????? ??????
        Intent authIntent = getIntent();
        badgeNum = authIntent.getExtras().getString("badgeNum");
        title = authIntent.getExtras().getString("title");
        carbonReduction = authIntent.getExtras().getString("carbonReduction");

        titleTextView.setText(title + " ????????????");
        String temp = authIntent.getExtras().getString("badgeCriteria");
        temp = temp.substring(0, temp.length() - 2);
        targetDistance = Double.valueOf(temp);

        sumOfDistance = 0.0;

        // progressView Max ?????? ?????? ????????? ??????
        distanceProgressView.setMax((float) targetDistance);
        endDistanceText.setText(String.valueOf(targetDistance) + "km");

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
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
            //?????? ????????? ??????????????? ??????
            startLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

            // ?????? ?????? ??????
            startButton.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.VISIBLE);
        }else if (code == 1) {
            state = false;

            // ?????? ?????? ??????
            pauseButton.setText("????????????");
        }else if (code == 2) {
            state = true;
            //?????? ????????? ??????????????? ??????
            startLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            // ?????? ?????? ??????
            pauseButton.setText("????????????");
        }else if (code == 3) {
            state = false;
            // ???????????? ??????
            finish();
        }
    }
    //polyline??? ???????????? ?????????
    private void drawPath(){
        PolylineOptions options = new PolylineOptions().add(startLatLng).add(endLatLng).width(15).color(Color.parseColor("#277CEA")).geodesic(true);
        polylineList.add(mMap.addPolyline(options));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 18));
    }

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

//        LatLng SEOUL = new LatLng(37.56, 126.97);
//
//        MarkerOptions markerOptions = new MarkerOptions();         // ?????? ??????
//        markerOptions.position(SEOUL);
//        markerOptions.title("??????");                         // ?????? ??????
//        markerOptions.snippet("????????? ??????");         // ?????? ??????
//        mMap.addMarker(markerOptions);
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));                 // ?????? ??????
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));                         // ?????? ??????
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);                           // ?????? ?????? ??????
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        enableMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
        if(state){                        // ?????? ?????? ???
            endLatLng = new LatLng(latitude, longtitude);        //?????? ????????? ???????????? ??????

            drawPath();                                            //polyline ?????????

            // ?????? ???????????? ??????
            Location startLocation = new Location("start");
            startLocation.setLatitude(startLatLng.latitude);
            startLocation.setLongitude(startLatLng.longitude);
            Location endLocation = new Location("end");
            endLocation.setLatitude(endLatLng.latitude);
            endLocation.setLongitude(endLatLng.longitude);
            sumOfDistance += startLocation.distanceTo(endLocation) * 0.001;     // m -> km

            // update progressView and textView
            distanceProgressView.setProgress((float) sumOfDistance);
            distanceProgressView.setLabelText(String.format("%1.2f", sumOfDistance) + "km");
            currentDistanceText.setText(String.format("%1.2f", sumOfDistance) + "km");

            Log.d("????????????", String.valueOf(sumOfDistance));
//            Toast.makeText(getApplicationContext(), "????????????: " + String.valueOf(sumOfDistance), Toast.LENGTH_SHORT).show();

            startLatLng = new LatLng(latitude, longtitude);        //???????????? ???????????? ?????? ??????

            // ?????? ??????: ???????????? >= ????????????
            if (targetDistance <= sumOfDistance) {
                endTime = getTime();        // ?????? ?????? ?????? ??????

                Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "s: " + startTime + ", e: " + endTime, Toast.LENGTH_SHORT).show();


                if (title.equalsIgnoreCase("?????????")) {      // ???????????? ??????
                    Intent authIntent = new Intent(getApplicationContext(), PhotoAuthActivity.class);
                    authIntent.putExtra("title", title);
                    authIntent.putExtra("badgeNum", badgeNum);
                    authIntent.putExtra("carbonReduction", carbonReduction);
                    authIntent.putExtra("startTime", startTime);
                    startActivity(authIntent);
                    finish();
                }
                else {      // ??????, ???????????? ???????????? ??????
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
    }

//    private double getDistance (double startLatitude, double startLongtitude, double endLatitude, double endLongtitude) {
//        return Math.sqrt(Math.pow(endLatitude - startLatitude, 2) + Math.pow(endLongtitude - startLongtitude, 2));
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
}