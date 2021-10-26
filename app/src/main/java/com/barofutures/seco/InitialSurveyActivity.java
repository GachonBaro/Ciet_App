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

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.barofutures.seco.adapter.InitQnaAdapter;
import com.barofutures.seco.model.InitQnA;
import com.google.android.gms.dynamic.SupportFragmentWrapper;

public class InitialSurveyActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private InitQnaAdapter adapter;
    private ProgressBar progressBar;
    private ObjectAnimator progressAnimator;
    private int previousProgressValue = 0;
    private ImageButton backButton;
    public static Button nextButton;
    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    private Toast toast;
    // 답변 체크 확인
    public static boolean isAnswered = false;

    // email
    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialsurvey);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // 키보드가 EditText 가림 해결
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // View 연결
        progressBar = findViewById(R.id.activity_initsurvey_progressbar);
        viewPager2 = findViewById(R.id.activity_initsurvey_viewpager2);
        backButton = findViewById(R.id.activity_initsurvey_button_back);
        nextButton = findViewById(R.id.activity_initsurvey_button_next);


        // 어댑터 생성 및 연결
        adapter = new InitQnaAdapter(this);
        viewPager2.setAdapter(adapter);
        // 스와이프 넘기기 비활성화
        viewPager2.setUserInputEnabled(false);

        // 프로그레스바
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                int progress = (int) ((double) (position + 1) / (double) adapter.getItemCount() * 100.0);

                progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", previousProgressValue, progress);
                previousProgressValue = progress;
                progressAnimator.setDuration(200);
                progressAnimator.start();

                // 오버스크롤 비활성화
                View child = viewPager2.getChildAt(position);
                if (child instanceof RecyclerView) {
                    child.setOverScrollMode(View.OVER_SCROLL_NEVER);
                }

                // 다음 페이지로 넘어가면 다시 버튼 비활성화하고 마지막 페이지에서는 버튼 안보이도록
                if (position == 8) {
                    nextButton.setVisibility(View.INVISIBLE);
                } else {
                    nextButton.setVisibility(View.VISIBLE);
                }

                if (isAnswered){
                    nextButton.setBackgroundResource(R.drawable.button_rounded_gray);
                    nextButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.seco_deepgray));
                }
            }
        });

        // 이전 문제 버튼
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem() != 0) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                }
            }
        });

        // 다음 문제 버튼
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnswered) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                    isAnswered=false;
                }
            }
        });
    }

    /*
     * back button click event
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            // 전부 Kill
            finishAffinity();
            toast.cancel();
        }
    }

    // 답변 체크
    public static void setAnswered(Context context) {
        nextButton.setBackgroundResource(R.drawable.button_rounded_green);
        nextButton.setTextColor(ContextCompat.getColor(context, R.color.white));
        InitialSurveyActivity.isAnswered = true;
        Log.d("truth", String.valueOf(isAnswered));
    }

}