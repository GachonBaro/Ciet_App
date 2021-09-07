package com.barofutures.seco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.util.FusedLocationSource;

public class CommerceMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    // Toolbar: 위에 메뉴 아이콘, 메뉴 이름(text) 뜨는 바
    private Toolbar toolbar;
    private ActionBar actionBar;

    // Naver Map
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1000;
    private MapView mapView;
    private UiSettings uiSettings;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commerce_map);

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // View 초기화 및 연결
        mapView=findViewById(R.id.activity_commerce_map_view);
        mapView.getMapAsync(this);
        locationSource=new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        tabLayout=findViewById(R.id.activity_commerce_map_tabs);

        //Apply the toolbar as action bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Status Bar 높이만큼 Padding 부여
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);
        actionBar = getSupportActionBar();

        //App Bar의 좌측 영역에 Drawer를 Open 하기 위한 Icon 추가
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_button_toolbar_menu);

        actionBar.setDisplayShowCustomEnabled(true);

        // Status Bar 높이만큼 Padding 부여
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

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
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap=naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        this.uiSettings=naverMap.getUiSettings();
        // 현위치 버튼, 나침반 추가
        uiSettings.setLocationButtonEnabled(true);
        uiSettings.setCompassEnabled(true);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(locationSource.onRequestPermissionsResult(requestCode,permissions,grantResults)){ return; }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}