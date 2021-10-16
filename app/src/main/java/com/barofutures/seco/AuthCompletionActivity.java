package com.barofutures.seco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.barofutures.seco.firebase.firestore.ActivityAuthData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthCompletionActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView badgeNumTextView;
    private Button authCompletionButton;

    // Intent values
    private String title;
    private String badgeNum;
    private String startTime;
    private String endTime;
    private String carbonReduction;

    // 구글 로그인 정보
    private FirebaseUser currentUser;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_completion);

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        titleTextView = findViewById(R.id.activity_auth_completion_title_text_view);
        dateTextView = findViewById(R.id.activity_auth_completion_date_text_view);
        timeTextView = findViewById(R.id.activity_auth_completion_time_text_view);
        badgeNumTextView = findViewById(R.id.activity_auth_completion_badge_num_text_view);
        authCompletionButton = findViewById(R.id.activity_auth_completion_upload_button);

        // 구글 로그인 정보 가져오기
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = currentUser.getEmail();

        // intent 값 받아오기
        Intent authIntent = getIntent();
        badgeNum = authIntent.getExtras().getString("badgeNum");
        title = authIntent.getExtras().getString("title");
        endTime = authIntent.getExtras().getString("endTime");
        carbonReduction = authIntent.getExtras().getString("carbonReduction");
        if (title.equalsIgnoreCase("걷기") || title.equalsIgnoreCase("자전거로 출퇴근")
            || title.equalsIgnoreCase("플로깅")|| title.equalsIgnoreCase("계단 이용하기")) {
            startTime = authIntent.getExtras().getString("startTime");
            timeTextView.setText("인증 시간 " + startTime + " - " + endTime);
        }
        else {
            timeTextView.setText("인증 시간 " + endTime);
        }

        titleTextView.setText(title + " 인증 완료!");
        dateTextView.setText(getDate("display"));

        badgeNumTextView.setText("X " + badgeNum);

        authCompletionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 인증 데이터 저장
        storeAuthData();
    }

    // get today's date
    private String getDate(String usage) {
        Date date = new Date();
        SimpleDateFormat dateFormat = null;

        if (usage.equalsIgnoreCase("display"))
            dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        else if (usage.equalsIgnoreCase("store"))
            dateFormat = new SimpleDateFormat("yyyyMMdd");

        String getDate = dateFormat.format(date);
        return getDate;
    }

    // switch button state
    private void updateButtonUI() {
        authCompletionButton.setBackgroundResource(R.drawable.button_rounded_green);
        authCompletionButton.setText("인증 완료하기");
        authCompletionButton.setEnabled(true);
    }

    // store Auth data
    private void storeAuthData() {
        String today = getDate("store");
        ActivityAuthData data = new ActivityAuthData(today, endTime, title, Long.parseLong(badgeNum), Double.parseDouble(carbonReduction.replace("kg", "")));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");
        DocumentReference docRef = usersRef.document(userEmail).collection("activity_auth")
                .document(today + endTime.replace(":", ""));

        docRef.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("AuthCompletionActivity", "DocumentSnapshot successfully written!");
                updateButtonUI();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("AuthCompletionActivity", "Error writing document", e);
            }
        });


    }
}