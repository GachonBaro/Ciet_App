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

    // ?????? ????????? ??????
    private FirebaseUser currentUser;
    private String userEmail;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_completion);

        // ????????? ?????? ??????
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        titleTextView = findViewById(R.id.activity_auth_completion_title_text_view);
        dateTextView = findViewById(R.id.activity_auth_completion_date_text_view);
        timeTextView = findViewById(R.id.activity_auth_completion_time_text_view);
        badgeNumTextView = findViewById(R.id.activity_auth_completion_badge_num_text_view);
        authCompletionButton = findViewById(R.id.activity_auth_completion_upload_button);

        // ?????? ????????? ?????? ????????????
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = currentUser.getEmail();

        db = FirebaseFirestore.getInstance();

        // intent ??? ????????????
        Intent authIntent = getIntent();
        badgeNum = authIntent.getExtras().getString("badgeNum");
        title = authIntent.getExtras().getString("title");
        endTime = authIntent.getExtras().getString("endTime");
        carbonReduction = authIntent.getExtras().getString("carbonReduction");
        if (title.equalsIgnoreCase("??????") || title.equalsIgnoreCase("???????????? ?????????")
            || title.equalsIgnoreCase("?????????")|| title.equalsIgnoreCase("?????? ????????????")) {
            startTime = authIntent.getExtras().getString("startTime");
            timeTextView.setText("?????? ?????? " + startTime + " - " + endTime);
        }
        else {
            timeTextView.setText("?????? ?????? " + endTime);
        }

        titleTextView.setText(title + " ?????? ??????!");
        dateTextView.setText(getDate("display"));

        badgeNumTextView.setText("X " + badgeNum);

        authCompletionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // ?????? ????????? ??????
        storeAuthData();
    }

    // get today's date
    private String getDate(String usage) {
        Date date = new Date();
        SimpleDateFormat dateFormat = null;

        if (usage.equalsIgnoreCase("display"))
            dateFormat = new SimpleDateFormat("yyyy??? MM??? dd???");
        else if (usage.equalsIgnoreCase("store"))
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String getDate = dateFormat.format(date);
        return getDate;
    }

    // switch button state
    private void updateButtonUI() {
        authCompletionButton.setBackgroundResource(R.drawable.button_rounded_green);
        authCompletionButton.setText("?????? ????????????");
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

    // ????????? ?????? ???, ?????? ????????? ????????????
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

    // ????????? ????????? ??????
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
                        if ((Boolean) currentData.get("challengeMode")) {       // challenge mode ??????
                            Log.d("ChallengeFragment", "true: " + currentData.get("challengeMode"));

                            // ?????? ????????? ????????? ???????????? ????????????
                            searchChallengeData();
                        } else {            // challenge mode ??? ?????????
                            Log.d("ChallengeFragment", "false: " + currentData.get("challengeMode"));
                            // ?????? UI ????????????
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

    // ?????? ?????? challenge ????????? ?????? ??????
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
                // ???????????? 90% ???????????? succeed ????????????
                long currentBadgeNum = (long) data.get("currentBadgeNum") + Long.parseLong(badgeNum);
                long maxBadgeNum = (long) data.get("maxBadgeNum");
                double progressValue = (double) currentBadgeNum / maxBadgeNum;
                Log.d("AuthCompletionActivity", "progressValue = " + progressValue);
                if (progressValue >= 0.9) {
                    Log.d("AuthCompletionActivity", "progressValue = " + progressValue);
                    updateChallengeSucceed(dataDoc.getId());
                }

                // ?????? ??? ????????????
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
     *????????? ?????? ??????, ?????? ???, ?????? ??????(mission_completion) ???????????? ( ???????????? 90% ???????????? succeed ????????????)
     */

    // ????????? ?????? ?????? (succeed) ????????????
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

    // challenge - currentBadgeNum ????????????
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
                // ?????? ??????(mission_completion) ????????????
                checkChallengeActivityByDay(key);
            }
        });

    }

    // ????????? - ????????? ??????(activityByDay)??? ????????? ????????? ????????? ??????
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

                // ????????? ????????? ????????? ????????? ????????? ?????????
                int index = 0;
                if (todayActivityData.contains(title)) {
                    index = todayActivityData.indexOf(title);
                    updateChallengeMissionCompletion(key, index, todayCompletionData);
                } else {
                    // ?????? UI ????????????
                    updateButtonUI();
                }

            }
        });
    }

    // ?????? ??????(mission_completion) ????????????
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
                // ?????? UI ????????????
                updateButtonUI();
            }
        });
    }

    // ?????? ?????? ??????
    private String getDayOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String day = "";
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                day = "???";
                break;
            case 2:
                day = "???";
                break;
            case 3:
                day = "???";
                break;
            case 4:
                day = "???";
                break;
            case 5:
                day = "???";
                break;
            case 6:
                day = "???";
                break;
            case 7:
                day = "???";
                break;
        }
        return day;
    }

}