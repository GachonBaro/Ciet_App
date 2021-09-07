package com.barofutures.seco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.barofutures.seco.model.SetiQnA;

public class MySetiActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String setiResult;
    private SpannableString displayResult;
    private TextView setiSummary;
    private Button retestButton, reviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myseti);

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar 설정
        toolbar=findViewById(R.id.activity_myseti_material_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Status Bar 높이만큼 Padding 부여
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        // View 연결 및 초기화
        setiSummary =findViewById(R.id.activity_myseti_type);
        retestButton =findViewById(R.id.activity_myseti_retest_button);
        reviewButton=findViewById(R.id.activity_myseti_button_review);

        // SETI 다시하기 버튼 이벤트 리스너
        retestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SetiSurveyActivity.class);
                startActivity(intent);
            }
        });

        // SETI 결과 다시보기 버튼 이벤트 리스너
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SetiAnswerReviewActivity.class);
                startActivity(intent);
            }
        });

        // SETI 결과 텍스트
        if(SetiQnA.mySETI.length()==0){
            setiSummary.setText("아직 SETI 검사를 하지 않았습니다.");
            // 내 답변 확인하기 버튼 숨김
            reviewButton.setVisibility(View.INVISIBLE);
            // SETI 다시하기가 아니라 검사하기로 표시
            retestButton.setText("SETI 검사하기");
        }else{
            displayResult=new SpannableString("당신의 유형은 "+ SetiQnA.getMySETI()+" 입니다.");
            displayResult.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.seco_green)), 8, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayResult.setSpan(new StyleSpan(Typeface.BOLD), 8, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setiSummary.setText(displayResult);

            // 내 답변 확인하기 버튼 표시
            reviewButton.setVisibility(View.VISIBLE);
        }

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