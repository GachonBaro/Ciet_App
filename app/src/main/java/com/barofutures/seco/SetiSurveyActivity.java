package com.barofutures.seco;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.barofutures.seco.adapter.SetiPageAdapter;
import com.barofutures.seco.adapter.SetiSurveyAdapter;

public class SetiSurveyActivity extends AppCompatActivity implements SetiSurveyAdapter.ChangeButtonState {
    private SetiPageAdapter setiPageAdapter;
    private ViewPager2 viewPager2;
    private ImageButton previousButton;
    private Button nextButton;
    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    private Toast toast;
    // 페이지 이동과 홈 연결 동작의 구분을 위해 nextButton에 두개의 Listener 선언
    private View.OnClickListener lastPageListener, pageMoveListener;
    // 답변 체크 확인을 위한 boolean
    public static boolean[] isComplete = {false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seti_survey);

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // View 연결
        viewPager2 = findViewById(R.id.seti_survey_viewPager);
        previousButton = findViewById(R.id.seti_survey_back_button);
        nextButton = findViewById(R.id.seti_survey_button_next);

        // adapter 설정
        setiPageAdapter = new SetiPageAdapter(this);
        viewPager2.setAdapter(setiPageAdapter);

        // 스크롤 막기
        viewPager2.setUserInputEnabled(false);

        // get id of back button
        previousButton = (ImageButton) findViewById(R.id.seti_survey_back_button);

        // 프로그레스바, 이전/다음 버튼 동작 구현
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 마지막 페이지에서는 '다음'을 '완료'로 변경
                if (position == 4) {
                    if (isComplete[position]) {
                        activateFinishButton();
                    } else {
                        deactivateFinishButton();
                    }
                } else {
                    if(isComplete[position]){
                        activateNextButton();
                    }else{
                        deactivateNextButton();
                    }
                }
            }
        });

        // 다음버튼 Listener
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem() != 0) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                }
            }
        });

        pageMoveListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem() != 4) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                }
            }
        };

        // 완료 시 SETI 결과로 이동
        lastPageListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetiSurveyResultActivity.class);
                startActivity(intent);
                finish();
            }
        };

    }

    /*
     *   Adapter에 정의한 버튼 활성화 인터페이스의 메서드 구현
     */

    @Override
    public void activateNextButton() {
        nextButton.setBackgroundResource(R.drawable.button_rounded_green);
        nextButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        nextButton.setText("다음");
        nextButton.setOnClickListener(null);
        nextButton.setOnClickListener(pageMoveListener);
    }

    @Override
    public void deactivateNextButton() {
        nextButton.setBackgroundResource(R.drawable.button_rounded_gray);
        nextButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.seco_deepgray));
        nextButton.setOnClickListener(null);
    }

    @Override
    public void activateFinishButton() {
        nextButton.setBackgroundResource(R.drawable.button_rounded_green);
        nextButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        nextButton.setText("완료");
        nextButton.setOnClickListener(null);
        nextButton.setOnClickListener(lastPageListener);
    }

    @Override
    public void deactivateFinishButton() {
        nextButton.setBackgroundResource(R.drawable.button_rounded_gray);
        nextButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.seco_deepgray));
        nextButton.setOnClickListener(null);
    }

    /*
     * back button click event
     */
//    @Override
//    public void onBackPressed() {
//        if (viewPager2.getCurrentItem() != 5) {
//            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
//                backKeyPressedTime = System.currentTimeMillis();
//                toast = Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
//                toast.show();
//                return;
//            }
//            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
//                // 전부 Kill
//                finishAffinity();
//                toast.cancel();
//            }
//        } else {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            this.finish();
//        }
//
//    }

}