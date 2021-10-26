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
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class SetiSurveyIntroActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private Button button;
    private SpannableString spannableString;
    private TextView textView;

    // email
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seti_survey_intro);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // View 초기화 및 연결
        constraintLayout=findViewById(R.id.activity_seti_survey_intro_layout);
        button=findViewById(R.id.activity_seti_survey_intro_button_start);
        textView=findViewById(R.id.activity_seti_survey_intro_title);

        // Spannable String
        spannableString=new SpannableString("Sustainable\nEco\nType\nIndicator");
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.seco_deepgreen)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.seco_deepgreen)), 8, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.seco_deepgreen)), 12, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.seco_deepgreen)), 17, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 8, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 12, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 17, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);



        // 시작 버튼 이벤트 리스너
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SetiSurveyActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });
    }
}