package com.barofutures.seco.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.adapter.ActivityRecommendationListAdapter;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Home extends Fragment {
    // Firebase and user data
    FirebaseFirestore db;
    FirebaseUser user;
    String email;

    // 나의 활동
    private TextView holdingBadgeNumTextView;
    private TextView carbonReductionTextView;
    private TextView donationBadgeNumTextView;
    private TextView mySetiType;

    // AI 추천 활동
    private RecyclerView aiRecommendationRecyclerView;
    private ActivityRecommendationListAdapter aiRecommendationListAdapter;

    // ___ 유형 추천 활동
    TextView setiRecommendationTextView;
    private RecyclerView setiRecommendationRecyclerView;
    private TextView setiNullDescriptionText;

    private ActivityRecommendationListAdapter setiRecommendationListAdapter;

    // 배지 기부하기
    private EditText donationInputEditText;
    private ConstraintLayout donationProgressButton;
    private ProgressBar donationProgressBar;
    private TextView donationButtonTextView;

    public Fragment_Home() {
//        db = FirebaseFirestore.getInstance();
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        email = user.getEmail();
    }

    // Instance 반환 메소드
    public static Fragment_Home newInstance() {
        return new Fragment_Home();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // View 초기화 및 연결
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        holdingBadgeNumTextView = (TextView) view.findViewById(R.id.fragment_home_my_activity_holding_badge_num_value_text_view);
        carbonReductionTextView = (TextView) view.findViewById(R.id.fragment_home_my_activity_carbon_reduction_value_text_view);
        donationBadgeNumTextView = (TextView) view.findViewById(R.id.fragment_home_my_activity_donation_badge_num_value_text_view);
        mySetiType = (TextView) view.findViewById(R.id.fragment_home_my_activity_my_seti_type_value_text_view);

        aiRecommendationRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_ai_recommendation_activity_recycler_view);

        setiRecommendationTextView = (TextView) view.findViewById(R.id.fragment_home_seti_recommendation_activity_title_text_view);
        setiRecommendationRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_seti_recommendation_activity_recycler_view);
        setiNullDescriptionText = (TextView) view.findViewById(R.id.fragment_home_seti_recommendation_activity_null_text);

        donationInputEditText = (EditText) view.findViewById(R.id.fragment_home_donation_input_edit_text);
        donationProgressButton = (ConstraintLayout) view.findViewById(R.id.fragment_home_donation_progress_button);
        donationProgressBar = (ProgressBar) view.findViewById(R.id.fragment_home_donation_progress_bar);
        donationButtonTextView = (TextView) view.findViewById(R.id.fragment_home_donation_progress_button_text_view);

        // 기부하기 버튼
        donationProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = donationInputEditText.getText().toString();
                // check null
                if (inputText.equals("") || inputText == null) {
                    Toast.makeText(getContext(), "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    checkBadgeNum(inputText);
                    donationInputEditText.getText().clear();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onStart() {
        super.onStart();
        setLayout();
    }

    // 유저 데이터를 가져와서 화면에 보여줌
    private void setLayout() {
        Log.d("Fragment_Home", "email = " + email);
        CollectionReference ref = db.collection("users").document(email).collection("user_info");
        DocumentReference currentRef = ref.document("current");

        currentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Fragment_Home", "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> currentData = document.getData();

                        // 나의 활동 UI 업데이트
                        updateMyActivityUI(currentData.get("badgeNum"), currentData.get("carbonReductionAmount"),
                                currentData.get("donationBadgeNum"), currentData.get("seti"));

                        Log.d("Fragment_Home", String.valueOf(currentData.get("seti").toString().equalsIgnoreCase("SETI_NULL")));

                        // SETI 유형별 추천 활동 업데이트
                        if (!currentData.get("seti").toString().equalsIgnoreCase("SETI_NULL")) {

                            updateSetiRecommendationUI(currentData.get("seti"));
                        }


                        // TODO: AI 추천 활동 업데이트

                    } else {
                        Log.d("Fragment_Home", "No such document");
                    }
                } else {
                    Log.d("Fragment_Home", "get failed with ", task.getException());
                }
            }
        });

    }

    // 나의 활동 UI 업데이트
    private void updateMyActivityUI(Object badgeNum, Object carbonReduction, Object donationBadgeNum, Object setiType) {
        holdingBadgeNumTextView.setText(badgeNum.toString() + "개");
        carbonReductionTextView.setText(String.format("%.4f", Double.parseDouble(carbonReduction.toString())) + "kg");
        donationBadgeNumTextView.setText(donationBadgeNum + "개");

        if (setiType.toString().equalsIgnoreCase("SETI_NULL")) {
            mySetiType.setText("결과 없음");
            setiRecommendationTextView.setText("SETI 유형별 추천 활동");
            setiNullDescriptionText.setVisibility(View.VISIBLE);
            setiRecommendationRecyclerView.setVisibility(View.GONE);
        } else {
            mySetiType.setText(setiType.toString());
            setiRecommendationTextView.setText(setiType.toString() + " 유형 추천 활동");
            setiNullDescriptionText.setVisibility(View.GONE);
            setiRecommendationRecyclerView.setVisibility(View.VISIBLE);
        }

    }

    // SETI 유형별 추천 활동 업데이트
    private void updateSetiRecommendationUI(Object setiType) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("seti").child("recommended_activity").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> data = new HashMap<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    data.put(dataSnapshot.getKey(), dataSnapshot.getValue());
                }
                Log.d("Fragment_Home", data.get(setiType.toString().toLowerCase()).toString());

                ArrayList<String> setiRecommendData = new ArrayList<>();
                String[] temp = data.get(setiType.toString().toLowerCase()).toString().split("\\|");
                for (String item : temp) {
                    setiRecommendData.add(item);
                }

                setiRecommendationListAdapter = new ActivityRecommendationListAdapter(setiRecommendData);
                setiRecommendationRecyclerView.setAdapter(setiRecommendationListAdapter);
                setiRecommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                setiRecommendationListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Fragment_Home", "SETI 유형별 추천 활동 데이터 불러오기 실패");
            }
        });
    }


    // 기부 배지 수 <= 보유 배지 수 인지 확인
    private void checkBadgeNum(String donation) {
        CollectionReference ref = db.collection("users").document(email).collection("user_info");
        DocumentReference currentRef = ref.document("current");

        currentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Fragment_Home", "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> currentData = document.getData();

                        if (Long.parseLong(currentData.get("badgeNum").toString()) < Long.parseLong(donation)) {
                            Toast.makeText(getContext(), "보유 배지 수를 초과하여 기부할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            updateDonationCurrentData(donation);
                        }
                    } else {
                        Log.d("Fragment_Home", "No such document");
                    }
                } else {
                    Log.d("Fragment_Home", "get failed with ", task.getException());
                }
            }
        });
    }

    // 기부 데이터 업데이트
    private void updateDonationCurrentData(String donation) {
        Long plus = Long.parseLong(donation);
        Long minus = 0 - plus;

        // 버튼 상태 변경
        buttonActivated();

        // 데이터 업데이트
        WriteBatch batch = db.batch();
        CollectionReference ref = db.collection("users").document(email).collection("user_info");
        DocumentReference currentRef = ref.document("current");

        batch.update(currentRef, "badgeNum", FieldValue.increment(minus));
        batch.update(currentRef, "donationBadgeNum", FieldValue.increment(plus));

        // Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("ChallengeFragment", "updateDonationData() update completed!!");

                updateUserDonationHistory(plus);
            }
        });

    }

    // 사용자의 기부 기록 업데이트
    private void updateUserDonationHistory(long value) {
        Map<String, Object> data = new HashMap<>();
        String date = getDate() + "-" + getTime();
        data.put("date", date);
        data.put("badgeNum", value);

        CollectionReference usersRef = db.collection("users");
        DocumentReference docRef = usersRef.document(email).collection("donation")
                .document(date);

        docRef.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("ChallengeFragment", "DocumentSnapshot successfully written!");
                // update layout
                setLayout();
                buttonFinished();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ChallengeFragment", "Error writing document", e);
            }
        });
    }



    // progressButton activated
    public void buttonActivated() {
        donationProgressButton.setBackgroundResource(R.drawable.button_rounded_gray);
        donationProgressBar.setVisibility(View.VISIBLE);
        donationButtonTextView.setText("진행 중");
    }

    // get today's date
    private String getDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String getDate = dateFormat.format(date);
        return getDate;
    }

    // get current time
    private String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hhmm");

        String getTime = dateFormat.format(date);
        return getTime;
    }

    // progressButton finished
    public void buttonFinished() {
        donationProgressButton.setBackgroundResource(R.drawable.button_rounded_green);
        donationProgressBar.setVisibility(View.GONE);
        donationButtonTextView.setText("확인");
    }
}