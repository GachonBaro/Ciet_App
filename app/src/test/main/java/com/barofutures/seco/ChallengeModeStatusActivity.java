package com.barofutures.seco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.barofutures.seco.adapter.RoutineListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChallengeModeStatusActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RoutineListAdapter routineListAdapter;
    private RecyclerView recyclerView;

    // 추가하기 Floating Button
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_mode_status);

//
        recyclerView = findViewById(R.id.routine_list_recycler_view);

        // Routinelistadapter 초기화 및 연결
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        routineListAdapter=new RoutineListAdapter();
        recyclerView.setAdapter(routineListAdapter);

//       // TODO: 스크롤하면 플로팅 버튼 보이기 / 숨기기
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                if(dy>0){
//                    floatingActionButton.hide();
//                }else if(dy<0){
//                    floatingActionButton.show();
//                }
//            }
//        });

        // actionBar 설정
        toolbar=findViewById(R.id.activity_challenge_mode_material_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Status Bar 높이만큼 Padding 부여
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        // Floating button listener
        floatingActionButton = findViewById(R.id.activity_challenge_mode_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), FreeModeActivity.class);
                startActivity(intent);
            }
        });

        // 오버스크롤 비활성화
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
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