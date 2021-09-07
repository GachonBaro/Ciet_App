package com.barofutures.seco;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.adapter.RoutineStatusAdapter;
import com.barofutures.seco.fragments.Fragment_Home;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class freeModeStatusActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RoutineStatusAdapter routineStatusAdapter;

    // 추가하기 Floating Button
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_mode_status);

        // View, Adapter 초기화 및 연결
        recyclerView=findViewById(R.id.activity_routine_status_recyclerview);
        routineStatusAdapter=new RoutineStatusAdapter(Fragment_Home.isChallenge, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(routineStatusAdapter);

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar 설정
        toolbar=findViewById(R.id.activity_routine_status_material_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Status Bar 높이만큼 Padding 부여
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        // Floating button listener
        floatingActionButton = findViewById(R.id.activity_free_mode_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(Fragment_Home.isChallenge){
                    // 챌린지 모드
                    intent = new Intent(freeModeStatusActivity.this, ChallengeModeStatusActivity.class);
                }else{
                    // 프리 모드
                    intent = new Intent(freeModeStatusActivity.this, ContentsListActivity.class);
                }
                startActivity(intent);
            }
        });
    }

    // 툴바 뒤로가기 버튼
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