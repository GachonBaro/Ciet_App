package com.barofutures.seco.fragments.freemode;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.ChallengeSettingActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.adapter.ChallengeActivityOfTodayListAdapter;
import com.barofutures.seco.adapter.ChallengeRecommendationListAdapter;
import com.barofutures.seco.firebase.firestore.UserInfoData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.skydoves.progressview.ProgressView;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChallengeFragment extends Fragment {
    // Firebase and user data
    FirebaseFirestore db;
    FirebaseUser user;
    String email;

    /*
     * Challenge OFF
     */
    private ConstraintLayout offProgressLayout;

    private RecyclerView offRecommendationRecyclerView;
    private ChallengeRecommendationListAdapter recommendationListAdapter;

    /*
     * Challenge ON
     */

    private ConstraintLayout onProgressLayout;

    private ProgressView challengeProgressView;
    private TextView progressDegreeTextView;
    private TextView obtainedBadgeNumTextView;

    private RecyclerView onActivityOfTodayRecyclerView;
    private ChallengeActivityOfTodayListAdapter activityOfTodayListAdapter;


    public ChallengeFragment() {
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();
    }

    @Override
    public void onStart() {
        super.onStart();
        setLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_challenge, container, false);

        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
         * Challenge OFF
         */

        offProgressLayout = view.findViewById(R.id.fragment_challenge_off_main_layout);
        offRecommendationRecyclerView = view.findViewById(R.id.fragment_challenge_off_recommendation_recycler_view);

        offProgressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "챌린지 생성 화면으로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ChallengeSettingActivity.class);
                startActivityForResult(intent, 2222);
            }
        });

        loadChallengeRecommendationData();

        /*
         * Challenge ON
         */

        onProgressLayout = view.findViewById(R.id.fragment_challenge_on_main_layout);
        challengeProgressView = view.findViewById(R.id.fragment_challenge_on_progress_progress_view);
        progressDegreeTextView = view.findViewById(R.id.fragment_challenge_on_progress_degree_text_view);
        obtainedBadgeNumTextView = view.findViewById(R.id.fragment_challenge_on_progress_degree_obtained_badge_num_text_view);
        onActivityOfTodayRecyclerView = view.findViewById(R.id.fragment_challenge_on_activity_of_today_recycler_view);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2222:
                    if (data.getExtras().getBoolean("challengeMode")) {
//                        offProgressLayout.setVisibility(View.GONE);
//                        onProgressLayout.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }

    // 유저의 챌린지 데이터를 받아옴
    private void getChallengeData() {
        CollectionReference ref = db.collection("users").document(email).collection("challenge");
        ref.orderBy("startDate", Query.Direction.DESCENDING).limit(1)
        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                DocumentSnapshot dataDoc = queryDocumentSnapshots.getDocuments().get(0);
                Map<String, Object> data = dataDoc.getData();
                Log.d("ChallengeFragment", "data===" + data.toString());
                Log.d("ChallengeFragment", "id===" + dataDoc.getId());
                getActivityOfTodayData(dataDoc.getId());
                updateProgressUI(Float.valueOf(data.get("currentBadgeNum").toString()), Float.valueOf(data.get("maxBadgeNum").toString()));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ChallengeFragment", e.toString());
            }
        });
    }

    // 진행률 계산
    private float getProgressValue(float currentNum, float maxNum) {
        return currentNum/maxNum * 100;
    }

    // '챌린지 진행률' UI 업데이트
    private void updateProgressUI(float currentNum, float maxNum) {
        float progressValue = getProgressValue(currentNum, maxNum);
        progressDegreeTextView.setText(String.format("%.1f", progressValue) + "%");
        challengeProgressView.setProgress(progressValue);
        challengeProgressView.setLabelText(progressValue + "%");
        obtainedBadgeNumTextView.setText("획득한 배지 " + String.format("%d", (int) currentNum) + "개");
    }

    // 오늘의 활동 데이터를 받아옴 (activity_by_day, mission_completion)
    private void getActivityOfTodayData(String key) {
        CollectionReference ref = db.collection("users").document(email).collection("challenge");
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
                ArrayList<Boolean> todayCompletionData = (ArrayList<Boolean>) completionData.get(getDateOfToday());

                activityOfTodayListAdapter = new ChallengeActivityOfTodayListAdapter(todayActivityData, todayCompletionData);
                onActivityOfTodayRecyclerView.setAdapter(activityOfTodayListAdapter);
                onActivityOfTodayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                activityOfTodayListAdapter.notifyDataSetChanged();
            }
        });

//        listRef.document("activity_by_day").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                Map<String, Object> data = task.getResult().getData();
//                Log.d("ChallengeFragment", "activity_by_day, data===" + data.toString());
//
//                // TODO: 받아온 데이터로 UI 업데이트
//            }
//        });
//
//        listRef.document("mission_completion").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                Map<String, Object> data = task.getResult().getData();
//                Log.d("ChallengeFragment", "mission_completion, data===" + data.toString());
//                // TODO: 받아온 데이터로 UI 업데이트
//
//            }
//        });
    }

    // 오늘 날짜 반환
    private String getDateOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());
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

    // 현재 챌린지 모드를 실행 중인지 데이터를 가져와서 챌린지 모드 On/off에 맞게 화면 보여줌
    private void setLayout() {
        CollectionReference ref = db.collection("users").document(email).collection("user_info");
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
                            offProgressLayout.setVisibility(View.INVISIBLE);
                            onProgressLayout.setVisibility(View.VISIBLE);

                            // 해당 유저의 챌린지 데이터를 받아옴
                            getChallengeData();
                        } else {            // challenge mode 가 아니면
                            Log.d("ChallengeFragment", "false: " + currentData.get("challengeMode"));
                            offProgressLayout.setVisibility(View.VISIBLE);
                            onProgressLayout.setVisibility(View.INVISIBLE);
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

    // Firebase(realtime)에서 난이도별 추천 챌린지 데이터 받아오기
    private void loadChallengeRecommendationData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("challenge").child("recommendations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> data = new HashMap<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    data.put(dataSnapshot.getKey(), dataSnapshot.getValue());
                }
                Log.d("ChallengeFragment", data.get("초급").toString());

                recommendationListAdapter = new ChallengeRecommendationListAdapter(data);
                offRecommendationRecyclerView.setAdapter(recommendationListAdapter);
                offRecommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recommendationListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChallengeFragment", "추천 챌린지 데이터 불러오기 실패");
            }
        });
    }
}