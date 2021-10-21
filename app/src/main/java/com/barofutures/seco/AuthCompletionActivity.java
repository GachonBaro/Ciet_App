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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private FirebaseFirestore db;

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

        db = FirebaseFirestore.getInstance();

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
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

        CollectionReference usersRef = db.collection("users");
        DocumentReference docRef = usersRef.document(userEmail).collection("activity_auth")
                .document(today + "-" + endTime.replace(":", ""));

        docRef.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("AuthCompletionActivity", "DocumentSnapshot successfully written!");
                updateBadgeNumAndCarbonReductionAmount();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("AuthCompletionActivity", "Error writing document", e);
            }
        });
    }

    // 획득한 배지 수, 탄소 저감량 업데이트
    private void updateBadgeNumAndCarbonReductionAmount() {
        WriteBatch batch = db.batch();
        CollectionReference ref = db.collection("users").document(userEmail).collection("user_info");
        DocumentReference currentRef = ref.document("current");

        batch.update(currentRef, "badgeNum", FieldValue.increment(Long.parseLong(badgeNum)));
        batch.update(currentRef, "carbonReductionAmount", FieldValue.increment(Double.parseDouble(carbonReduction.replace("kg", ""))));

        // Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("ChallengeSettingActivity", "init survey update completed!!");
                checkChallengeMode();
            }
        });
    }

    // 챌린지 중인지 확인
    private void checkChallengeMode() {
        CollectionReference ref = db.collection("users").document(userEmail).collection("user_info");
        DocumentReference currentRef = ref.document("current");

        currentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("ChallengeFragment", "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> currentData = document.getData();
                        if ((Boolean) currentData.get("challengeMode")) {       // challenge mode 이면
                            Log.d("ChallengeFragment", "true: " + currentData.get("challengeMode"));

                            // 해당 유저의 챌린지 데이터를 업데이트
                            searchChallengeData();
                        } else {            // challenge mode 가 아니면
                            Log.d("ChallengeFragment", "false: " + currentData.get("challengeMode"));
                            // 버튼 UI 업데이트
                            updateButtonUI();
                        }
                    } else {
                        Log.d("ChallengeFragment", "No such document");
                    }
                } else {
                    Log.d("ChallengeFragment", "get failed with ", task.getException());
                }
            }
        });
    }

    // 진행 중인 challenge 데이터 위치 찾음
    private void searchChallengeData() {
        CollectionReference ref = db.collection("users").document(userEmail).collection("challenge");
        ref.orderBy("startDate", Query.Direction.DESCENDING).limit(1)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                DocumentSnapshot dataDoc = queryDocumentSnapshots.getDocuments().get(0);
                Map<String, Object> data = dataDoc.getData();
                Log.d("AuthCompletionActivity", "data===" + data.toString());
                Log.d("AuthCompletionActivity", "id===" + dataDoc.getId());
                // 진행률이 90% 이상이면 succeed 업데이트
                long currentBadgeNum = (long) data.get("currentBadgeNum");
                long maxBadgeNum = (long) data.get("maxBadgeNum");
                double progressValue = (double) currentBadgeNum / maxBadgeNum;
                Log.d("123123123", "progressValue = " + progressValue);
                if (progressValue >= 0.9) {
                    Log.d("123123123", "progressValue = " + progressValue);
                    updateChallengeSucceed(dataDoc.getId());
                }

                // 배지 수 업데이트
                updateChallengeData(dataDoc.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("AuthCompletionActivity", e.toString());
            }
        });
    }

    /*
     *챌린지 중인 경우, 배지 수, 미션 완료(mission_completion) 업데이트 ( 진행률이 90% 이상이면 succeed 업데이트)
     */

    // 챌린지 성공 여부 (succeed) 업데이트
    private void updateChallengeSucceed(String key) {
        WriteBatch batch = db.batch();

        CollectionReference ref = db.collection("users").document(userEmail).collection("challenge");
        DocumentReference listRef = ref.document(key);

        batch.update(listRef, "succeed", true);
        // Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("AuthCompletionActivity", "challenge data(succeed) update completed!!");
            }
        });
    }

    // challenge - currentBadgeNum 업데이트
    private void updateChallengeData(String key) {

        WriteBatch batch = db.batch();

        CollectionReference ref = db.collection("users").document(userEmail).collection("challenge");
        DocumentReference listRef = ref.document(key);

        batch.update(listRef, "currentBadgeNum", FieldValue.increment(Long.parseLong(badgeNum)));
        // Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("AuthCompletionActivity", "challenge data update completed!!");
                // 미션 완료(mission_completion) 업데이트
                checkChallengeActivityByDay(key);
            }
        });

    }

    // 챌린지 - 오늘의 활동(activityByDay)에 인증한 활동이 있는지 체크
    private void checkChallengeActivityByDay(String key) {
        WriteBatch batch = db.batch();

        CollectionReference ref = db.collection("users").document(userEmail).collection("challenge");
        CollectionReference listRef = ref.document(key).collection("activity_list");
        listRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> reference = task.getResult().getDocuments();
                Map<String, Object> activityData;
                Map<String, Object> completionData;
                if (reference.get(0).getId().equalsIgnoreCase("activity_by_day")) {
                    activityData = reference.get(0).getData();
                    completionData = reference.get(1).getData();
                } else {
                    completionData = reference.get(0).getData();
                    activityData = reference.get(1).getData();
                }
                ArrayList<String> todayActivityData = (ArrayList<String>) activityData.get(getDayOfToday());
                ArrayList<Boolean> todayCompletionData = (ArrayList<Boolean>) completionData.get(getDate("store"));

                // 챌린지 오늘의 활동에 인증한 활동이 있다면
                int index = 0;
                if (todayActivityData.contains(title)) {
                    index = todayActivityData.indexOf(title);
                    updateChallengeMissionCompletion(key, index, todayCompletionData);
                } else {
                    // 버튼 UI 업데이트
                    updateButtonUI();
                }

            }
        });
    }

    // 미션 완료(mission_completion) 업데이트
    private void updateChallengeMissionCompletion(String key, int index, ArrayList<Boolean> todayCompletionData) {
        ArrayList<Boolean> updateData = (ArrayList<Boolean>) todayCompletionData.clone();
        updateData.set(index, true);

        WriteBatch batch = db.batch();

        CollectionReference ref = db.collection("users").document(userEmail).collection("challenge");
        DocumentReference listRef = ref.document(key).collection("activity_list").document("mission_completion");

        batch.update(listRef, getDate("store"), updateData);
        // Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("AuthCompletionActivity", "challenge - mission_completion data update completed!!");
                // 버튼 UI 업데이트
                updateButtonUI();
            }
        });
    }

    // 오늘 요일 반환
    private String getDayOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String day = "";
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                day = "일";
                break;
            case 2:
                day = "월";
                break;
            case 3:
                day = "화";
                break;
            case 4:
                day = "수";
                break;
            case 5:
                day = "목";
                break;
            case 6:
                day = "금";
                break;
            case 7:
                day = "토";
                break;
        }
        return day;
    }

}