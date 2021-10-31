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
    private ProgressView altitudeProgressView;          // 이동층수 표시 바
    private TextView endAltitudeText;                   // 목표 층수
    private TextView currentAltitudeText;               // 현재 이동층수
    private Button startButton;                         // 시작버튼
    private Button pauseButton;                         // 일시정지 버튼
    private Button stopButton;                          // 그만두기 버튼

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private FusedLocationProviderApi mFusedLocationProviderApi;
    private boolean mPermissionDenied;
    private LocationManager mLocationManager;
    private LocationCallback locationCallback;
    private Marker mCurrentMarker;
    private boolean state = false;                    // 진행 상태
    private double startVerticalAccuracyMeters;         // 시작 시 고도
    private double currentVerticalAccuracyMeters;         // 현재 고도

    private String startTime;       // 시작 시간
    private String endTime;          // 종료 시간
    private double targetDistance;  // 목표 거리: (m)단위
    private double sumOfDistance;   // 누적 이동거리
    private double currentDistance; // currentVerticalAccuracyMeters - startVerticalAccuracyMeters
    private double postValue;       // 이전 위치의 currentDistance 값

    private long waitTime = 0;      // back button 눌린 시간

    private String title;           // 활동 이름
    private String badgeNum;        // 1회 완료 시 얻을 수 있는 뱃지 수
    private String carbonReduction;     // 1회 완료 시 탄소 감축량

    private SensorManager sensorManager;
    private Sensor pressure;        // 압력 센서
//    private Sensor stepCounter;     // 걸음 수 측정 센서
//    private Sensor temperature;     // 온도 센서

    private static final double seaLevelPressure = 1013.25;
    private static final double temperature = 15;
    private double startAltitude = 0.0;
    private double currentAltitude = 0.0;
    private double currentTotalDistance = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepping_auth);

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar 설정
        toolbar=findViewById(R.id.activity_stepping_auth_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Status Bar 높이만큼 Padding 부여
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        titleTextView = findViewById(R.id.activity_stepping_auth_title);
        altitudeProgressView = findViewById(R.id.activity_stepping_auth_distance_progressview);
        endAltitudeText = findViewById(R.id.altitude_end_textview);
        currentAltitudeText = findViewById(R.id.current_altitude_textview);
        startButton = findViewById(R.id.activity_stepping_auth_start_button);
        pauseButton = findViewById(R.id.activity_stepping_auth_pause_button);
        stopButton = findViewById(R.id.activity_stepping_auth_stop_button);

        // intent로 목표 층수 값 받아와서 변경
        Intent authIntent = getIntent();
        badgeNum = authIntent.getExtras().getString("badgeNum");
        title = authIntent.getExtras().getString("title");
        carbonReduction = authIntent.getExtras().getString("carbonReduction");

        titleTextView.setText(title + " 인증하기");
        String temp = authIntent.getExtras().getString("badgeCriteria");
        temp = temp.substring(0, temp.length() - 1);
        targetDistance = Double.valueOf(temp) * 4;      // 한 층에 약 4m

        sumOfDistance = 0.0;
        currentDistance = 0.0;
        postValue = 0.0;

        // progressView Max 값을 목표 거리로 변경
        altitudeProgressView.setMax((float) targetDistance);
        endAltitudeText.setText(String.valueOf(targetDistance) + "m" + "\n(12층)");

        // 시작버튼 listener
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = getTime();
                setState(0);        // 시작 상태로 변경
                Toast.makeText(getApplicationContext(), "활동을 시작합니다", Toast.LENGTH_SHORT).show();
            }
        });
        // 일시정지/계속하기 버튼 listener
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == true) {
                    setState(1);        // 활동중 -> 일시정지 상태로 변경
                } else {
                    setState(2);        // 일시정지 -> 활동중 상태로 변경
                }

            }
        });
        // 그만두기 버튼 listener
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setState(3);        // 멈추기 상태로 변경
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

        // 기기에 압력 센서가 없는 경우 activity 종료
        if (pressure == null) {
            Toast.makeText(getApplicationContext(), "이 기기는 압력 센서를 지원하지 않아 해당 기능을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
//        // 기기에 온도 센서가 없는 경우 activity 종료
//        if (temperature == null) {
//            Toast.makeText(getApplicationContext(), "이 기기는 온도 센서를 지원하지 않아 해당 기능을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
//            finish();
//        }

//        // 기기에 걸음수 측정 센서가 없는 경우 activity 종료
//        if (stepCounter == null) {
//            Toast.makeText(getApplicationContext(), "이 기기는 걸음수 측정 센서를 지원하지 않아 해당 기능을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
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

    // 상태 변경 (0 - 시작, 1 - 일시정지, 2 - 계속하기, 3 - 멈추기)
    public void setState(int code) {
        if (code == 0) {
            state = true;

            // 현재 고도를 시작점으로 설정
            startAltitude = currentAltitude;

//            Toast.makeText(getApplicationContext(), "startAltitude = " + String.valueOf(startAltitude), Toast.LENGTH_SHORT).show();


            // 버튼 상태 변경
            startButton.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.VISIBLE);
        }else if (code == 1) {
            state = false;

            // 누적 이동 높이 갱신
//            sumOfDistance += currentDistance;
            sumOfDistance = currentTotalDistance;
            postValue = 0.0;

            // 버튼 상태 변경
            pauseButton.setText("계속하기");
        }else if (code == 2) {
            // 현재 고도를 시작점으로 설정
            startAltitude = currentAltitude;

            state = true;

//            Toast.makeText(getApplicationContext(), "startAltitude = " + String.valueOf(startAltitude), Toast.LENGTH_SHORT).show();

            // 버튼 상태 변경
            pauseButton.setText("일시정지");
        }else if (code == 3) {
            state = false;
            // 액티비티 종료
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
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //status bar의 높이 계산
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
            Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        } else {
            finish(); // 액티비티 종료
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

        if (state) {                        // 활동 중일 때


            // 이동거리 갱신 (현재 고도 - 시작 고도)
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

                Log.d("SteppingAuthActivity22", "업데이트, sumOfDistance = " + sumOfDistance
                        + ", currentDistance = " + currentDistance + ", tempCurrentTotalDistance = " + String.format("%1.2f", tempCurrentTotalDistance));
                currentTotalDistance = tempCurrentTotalDistance;
            }

            // 달성 완료: 이동거리 >= 목표거리
            if (targetDistance <= (currentTotalDistance)) {
                endTime = getTime();        // 달성 완료 시간 저장
                Toast.makeText(getApplicationContext(), "달성완료", Toast.LENGTH_SHORT).show();
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

    // input pressure에 따라 고도를 반환해주는 method
    private double getAltitude(double pressure) {
        double altitude = ((Math.pow(seaLevelPressure/pressure, 1/5.257) - 1) * (temperature + 273.15)) / 0.0065;
        return altitude;
    }
}