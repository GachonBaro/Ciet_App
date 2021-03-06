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
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.barofutures.seco.model.SetiQnA;

public class SetiSurveyResultActivity extends AppCompatActivity {
    private SpannableString summaryString;
    private TextView summary, description, typo1, typo2, typo3;
    private Button detailButton, exitButton;
    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    private String seti;

    private String email;

    public SetiSurveyResultActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setisurveyresult);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // View 초기화 및 연결
        summary = findViewById(R.id.activity_seti_result_text_summary);
        description = findViewById(R.id.activity_seti_result_text_description);
        detailButton = findViewById(R.id.activity_seti_result_button_detail);
        exitButton = findViewById(R.id.activity_seti_result_button_exit);
        typo1=findViewById(R.id.activity_seti_result_typo_1);
        typo2=findViewById(R.id.activity_seti_result_typo_2);
        typo3=findViewById(R.id.activity_seti_result_typo_3);

        //SETI 결과 출력
        SetiQnA.calculateSETI();
        seti=SetiQnA.getMySETI();
        summaryString=new SpannableString("당신의 환경 유형은 "+seti+" 입니다.");
        summaryString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.seco_green)), 11, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        summary.setText(summaryString);
        typo1.setText(String.valueOf(seti.charAt(0)));
        typo2.setText(String.valueOf(seti.charAt(1)));
        typo3.setText(String.valueOf(seti.charAt(2)));

        SetiQnA setiQnA = new SetiQnA();
        description.setText(SetiQnA.typeDescription.get(seti));

        // SETI 결과 저장
        SetiQnA.storeSETIResult(email);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // 더 자세히 알아보기 클릭 시 이벤트
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MySetiActivity.class);
                intent.putExtra("email", email);
                // 플래그 지정: 같은 액티비티가 재사용되기 때문에 onCreate가 호출되지 않고 onNewIntent가 실행되는 것에 주의
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        // 나가기 버튼 클릭 시 홈으로 이동
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("email", email);
                // 플래그 지정: 같은 액티비티가 재사용되기 때문에 onCreate가 호출되지 않고 onNewIntent가 실행되는 것에 주의
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

}