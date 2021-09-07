package com.barofutures.seco;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.barofutures.seco.adapter.FreeModePagerAdapter;
import com.barofutures.seco.fragments.authorize.ReceiptAuthFragment;
import com.barofutures.seco.fragments.authorize.CameraAuthFragment;
import com.google.android.material.tabs.TabLayout;

public class AuthorizeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CameraAuthFragment cameraAuthFragment;
    private ReceiptAuthFragment receiptAuthFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);

        // View, Adapter 초기화 및 연결


        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar 설정
        toolbar=findViewById(R.id.activity_authorize_status_material_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Status Bar 높이만큼 Padding 부여
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.activity_authorize_view_pager);

        // TabLayout에 viewPager 연결
        tabLayout = (TabLayout) findViewById(R.id.activity_authorize_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // create fragments
        cameraAuthFragment=new CameraAuthFragment();
        receiptAuthFragment =new ReceiptAuthFragment();

        // connect Fragment using ViewPagerAdapter
        FreeModePagerAdapter viewPagerAdapter = new FreeModePagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(cameraAuthFragment, "카메라 인증");
        viewPagerAdapter.addFragment(receiptAuthFragment, "결제내역 인증");
        viewPager.setAdapter(viewPagerAdapter);
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
}