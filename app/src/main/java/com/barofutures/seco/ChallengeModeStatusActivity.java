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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

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

        recyclerView = findViewById(R.id.routine_list_recycler_view);

        // Routinelistadapter 초기화 및 연결
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        routineListAdapter=new RoutineListAdapter();
        recyclerView.setAdapter(routineListAdapter);


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
                Intent intent=new Intent(getApplicationContext(), ContentsListActivity.class);
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