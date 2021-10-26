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

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.barofutures.seco.model.SetiQnA;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.progressview.ProgressView;

import java.util.Map;

public class MySetiActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String setiResult;
    private SpannableString displayResult;
    private TextView setiSummary;
    private Button retestButton;
    private ProgressView progressViewKnowledge;
    private ProgressView progressViewStatus;
    private ProgressView progressViewWillingness;
    private TextView typeDescription;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myseti);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

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

        progressViewKnowledge = (ProgressView) findViewById(R.id.activity_myseti_knowledge_progressbar);
        progressViewStatus = (ProgressView) findViewById(R.id.activity_myseti_status_progressbar);
        progressViewWillingness = (ProgressView) findViewById(R.id.activity_myseti_willingness_progress_bar);
        typeDescription = (TextView) findViewById(R.id.activity_myseti_type_description);
        setiSummary = findViewById(R.id.activity_myseti_type);
        retestButton = findViewById(R.id.activity_myseti_retest_button);

        // SETI 다시하기 버튼 이벤트 리스너
        retestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SetiSurveyIntroActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        // Firestore에서 SETI 결과 정보 받아와서 띄움
        loadSETIResultData(email);
    }

    // load SETI result data
    public void loadSETIResultData(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");
        DocumentReference docRef = usersRef.document(email).collection("user_info").document("seti");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("MySetiActivity", "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> setiData = document.getData();
                        updateUI(setiData.get("type").toString(), Long.valueOf(setiData.get("understanding").toString()), Long.valueOf(setiData.get("practice").toString()), Long.valueOf(setiData.get("intent").toString()));
                    } else {
                        Log.d("MySetiActivity", "No such document");
                        // SETI 다시하기가 아니라 검사하기로 표시
                        retestButton.setText("SETI 검사하기");
                        setiSummary.setText("SETI 데이터가 없습니다.");
                        typeDescription.setText("SETI 데이터가 없습니다.\nSETI 테스트를 진행하세요.");
                    }
                } else {
                    Log.d("MySetiActivity", "get failed with ", task.getException());
                }
            }
        });

    }

    // update UI
    public void updateUI(String type, long understanding, long practice, long intent) {
        displayResult=new SpannableString("당신의 유형은 "+ type +" 입니다.");
        displayResult.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.seco_green)), 8, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        displayResult.setSpan(new StyleSpan(Typeface.BOLD), 8, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setiSummary.setText(displayResult);

        progressViewKnowledge.setProgress(understanding * 2);
        progressViewKnowledge.setLabelText(String.valueOf(understanding * 2) + "%");
        progressViewStatus.setProgress(practice * 2);
        progressViewStatus.setLabelText(String.valueOf(practice * 2) + "%");
        progressViewWillingness.setProgress(intent * 2);
        progressViewWillingness.setLabelText(String.valueOf(intent * 2) + "%");

        SetiQnA setiQnA = new SetiQnA();
        typeDescription.setText(SetiQnA.typeDescription.get(type));
    }

    // 툴바 뒤로가기 버튼
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("email", email);
                // 플래그 지정: 같은 액티비티가 재사용되기 때문에 onCreate가 호출되지 않고 onNewIntent가 실행되는 것에 주의
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
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