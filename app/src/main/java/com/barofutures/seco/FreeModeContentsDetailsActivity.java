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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class FreeModeContentsDetailsActivity extends AppCompatActivity {
    private int pageNum;                // viewPager에서 몇 번째 page인지 (0 - meal, 1 - activity, 2 - quest, 3 - favorites)
    private int recyclerPosition;       // recycler view item 중에 몇 번째 item인지

    private ScrollView scrollView;
    private ConstraintLayout mealConstraintLayout;
    private ConstraintLayout activityConstraintLayout;
    private ImageView background;
    private ImageButton backButton;
    private TextView title;
    private TextView summary;
    private TextView contents;
    private TextView badgeCriteria;
    private TextView badgeNum;
    private TextView carbonReduction;
    // eco-activity만 해당
    private TextView caloriesConsumption;

    private Button enableButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_mode_contents_details);

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // intent의 값 전달 받기
        Intent intent = getIntent();
        pageNum = intent.getExtras().getInt("page_number");
        recyclerPosition = intent.getExtras().getInt("recycler_position");

        // View 초기화 및 연결
        scrollView = findViewById(R.id.free_mode_contents_linear_layout);
        enableButton = findViewById(R.id.contents_details_enable_button);
        backButton = findViewById(R.id.contents_details_back_button);

        // 데이터 띄우기
        background = findViewById(R.id.free_mode_meal_contents_image);
        background.setImageResource(intent.getExtras().getInt("background"));
        title = findViewById(R.id.contents_details_title_text_view);
        title.setText(intent.getExtras().getString("title"));
        summary = findViewById(R.id.contents_details_description1_summary);
        summary.setText(intent.getExtras().getString("summary"));
        contents = findViewById(R.id.contents_details_description1_contents);
        contents.setText(intent.getExtras().getString("contents"));

        badgeCriteria = findViewById(R.id.contents_details_badge_criteria_text_view);
        badgeCriteria.setText(intent.getExtras().getString("badgeCriteria"));
        badgeNum = findViewById(R.id.activity_free_mode_contents_badge_num_text_view);
        badgeNum.setText("배지 " + intent.getExtras().getString("badgeNum") + "개");

        carbonReduction = findViewById(R.id.contents_details_carbon_reduction_text_view);
        carbonReduction.setText(intent.getExtras().getString("carbonReduction"));

            if (pageNum == 0) {     // eco-meal page인 경우
                mealConstraintLayout = findViewById(R.id.amount_of_carbon_reduction);
                activityConstraintLayout = findViewById(R.id.amount_of_carbon_reduction_calories_consumption);
                mealConstraintLayout.setVisibility(View.VISIBLE);
                activityConstraintLayout.setVisibility(View.INVISIBLE);
            }
            else if (pageNum == 1) {    // eco-activity page인 경우
                caloriesConsumption = findViewById(R.id.activity_contents_details_calories_consumption_text_view);
                caloriesConsumption.setText(intent.getExtras().getString("caloriesConsumption"));

                mealConstraintLayout = findViewById(R.id.amount_of_carbon_reduction);
                activityConstraintLayout = findViewById(R.id.amount_of_carbon_reduction_calories_consumption);
                mealConstraintLayout.setVisibility(View.INVISIBLE);
                activityConstraintLayout.setVisibility(View.VISIBLE);
            } else if(pageNum==3){
                mealConstraintLayout = findViewById(R.id.amount_of_carbon_reduction);
                activityConstraintLayout = findViewById(R.id.amount_of_carbon_reduction_calories_consumption);
                mealConstraintLayout.setVisibility(View.VISIBLE);
                activityConstraintLayout.setVisibility(View.INVISIBLE);
            } else{
                mealConstraintLayout = findViewById(R.id.amount_of_carbon_reduction);
                activityConstraintLayout = findViewById(R.id.amount_of_carbon_reduction_calories_consumption);
                mealConstraintLayout.setVisibility(View.VISIBLE);
                activityConstraintLayout.setVisibility(View.INVISIBLE);
            }

        // 상태바 높이 만큼 padding 주기
        scrollView.setPadding(0, getStatusBarHeight(), 0, 0);

        // 뒤로가기 버튼 누르면 activity 종료
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 걷기, 자전거로 출퇴근, 플로깅인 경우
                if (pageNum == 1 && !intent.getExtras().getString("title").equalsIgnoreCase("계단 이용하기")){
                    Intent authIntent = new Intent(getApplicationContext(), WalkingAuthActivity.class);
                    authIntent.putExtra("title", intent.getExtras().getString("title"));
                    authIntent.putExtra("badgeCriteria", intent.getExtras().getString("badgeCriteria"));
                    authIntent.putExtra("badgeNum", intent.getExtras().getString("badgeNum"));
                    authIntent.putExtra("carbonReduction", intent.getExtras().getString("carbonReduction"));
                    startActivity(authIntent);
                }
                // 계단 이용하기 인 경우
                else if (intent.getExtras().getString("title").equalsIgnoreCase("계단 이용하기")) {
                    Intent authIntent = new Intent(getApplicationContext(), SteppingAuthActivity.class);
                    authIntent.putExtra("title", intent.getExtras().getString("title"));
                    authIntent.putExtra("badgeCriteria", intent.getExtras().getString("badgeCriteria"));
                    authIntent.putExtra("badgeNum", intent.getExtras().getString("badgeNum"));
                    authIntent.putExtra("carbonReduction", intent.getExtras().getString("carbonReduction"));
                    startActivity(authIntent);
                }
                // 나머지 사진 인증하는 활동인 경우
                else {
                    Intent authIntent = new Intent(getApplicationContext(), PhotoAuthActivity.class);
                    authIntent.putExtra("title", intent.getExtras().getString("title"));
                    authIntent.putExtra("badgeNum", intent.getExtras().getString("badgeNum"));
                    authIntent.putExtra("carbonReduction", intent.getExtras().getString("carbonReduction"));
                    startActivity(authIntent);
                }

            }
        });

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