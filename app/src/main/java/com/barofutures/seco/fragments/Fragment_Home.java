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
package com.barofutures.seco.fragments;

import android.content.Context;
import android.content.Intent;
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

import com.barofutures.seco.MainActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.SplashActivity;
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
import com.google.firebase.firestore.Query;
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

    // ?????? ??????
    private TextView holdingBadgeNumTextView;
    private TextView carbonReductionTextView;
    private TextView donationBadgeNumTextView;
    private TextView mySetiType;

    // AI ?????? ??????
    private RecyclerView aiRecommendationRecyclerView;
    private ActivityRecommendationListAdapter aiRecommendationListAdapter;
    private TextView aiNullDescriptionText;

    // ___ ?????? ?????? ??????
    private TextView setiRecommendationTextView;
    private RecyclerView setiRecommendationRecyclerView;
    private TextView setiNullDescriptionText;

    private ActivityRecommendationListAdapter setiRecommendationListAdapter;

    // ?????? ????????????
    private EditText donationInputEditText;
    private ConstraintLayout donationProgressButton;
    private ProgressBar donationProgressBar;
    private TextView donationButtonTextView;

    public Fragment_Home() {
//        db = FirebaseFirestore.getInstance();
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        email = user.getEmail();
    }

    // Instance ?????? ?????????
    public static Fragment_Home newInstance() {
        return new Fragment_Home();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = FirebaseFirestore.getInstance();
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        email = user.getEmail();
        email = MainActivity.userEmail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // View ????????? ??? ??????
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
        aiNullDescriptionText = (TextView) view.findViewById(R.id.fragment_home_ai_recommendation_activity_null_text);

        setiRecommendationTextView = (TextView) view.findViewById(R.id.fragment_home_seti_recommendation_activity_title_text_view);
        setiRecommendationRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_seti_recommendation_activity_recycler_view);
        setiNullDescriptionText = (TextView) view.findViewById(R.id.fragment_home_seti_recommendation_activity_null_text);

        donationInputEditText = (EditText) view.findViewById(R.id.fragment_home_donation_input_edit_text);
        donationProgressButton = (ConstraintLayout) view.findViewById(R.id.fragment_home_donation_progress_button);
        donationProgressBar = (ProgressBar) view.findViewById(R.id.fragment_home_donation_progress_bar);
        donationButtonTextView = (TextView) view.findViewById(R.id.fragment_home_donation_progress_button_text_view);

        // ???????????? ??????
        donationProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = donationInputEditText.getText().toString();
                // check null
                if (inputText.equals("") || inputText == null) {
                    Toast.makeText(getContext(), "?????? ??????????????????.", Toast.LENGTH_SHORT).show();
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

    // ?????? ???????????? ???????????? ????????? ?????????
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

                        // ?????? ?????? UI ????????????
                        updateMyActivityUI(currentData.get("badgeNum"), currentData.get("carbonReductionAmount"),
                                currentData.get("donationBadgeNum"), currentData.get("seti"));

                        Log.d("Fragment_Home", String.valueOf(currentData.get("seti").toString().equalsIgnoreCase("SETI_NULL")));

                        // SETI ????????? ?????? ?????? ????????????
                        if (!currentData.get("seti").toString().equalsIgnoreCase("SETI_NULL")) {

                            updateSetiRecommendationUI(currentData.get("seti"));
                        }

                        // AI ?????? ?????? ????????????
                        checkAiRecommendationActivityData();

                    } else {
                        Log.d("Fragment_Home", "No such document");
                    }
                } else {
                    Log.d("Fragment_Home", "get failed with ", task.getException());
                }
            }
        });

    }

    // AI ?????? ?????? ???????????? (?????? ???????????? ?????? ???????????? ???????????? ?????????
    private void checkAiRecommendationActivityData() {
        CollectionReference ref = db.collection("users").document(email).collection("ai_recommendation");
        DocumentReference docRef = ref.document("ai_recommendation_list");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Fragment_Home", "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> data = document.getData();
                        ArrayList<String> aiRecommendData = (ArrayList<String>) data.get("list");

                        // UI ????????????
                        aiNullDescriptionText.setVisibility(View.GONE);
                        aiRecommendationRecyclerView.setVisibility(View.VISIBLE);

                        aiRecommendationListAdapter = new ActivityRecommendationListAdapter(aiRecommendData);
                        aiRecommendationRecyclerView.setAdapter(aiRecommendationListAdapter);
                        aiRecommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        aiRecommendationListAdapter.notifyDataSetChanged();

                    } else {
                        Log.d("Fragment_Home", "No such document");
                    }
                } else {
                    Log.d("Fragment_Home", "get failed with ", task.getException());
                    Toast.makeText(getContext(), "ERROR: ?????? ???????????? ?????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // ?????? ?????? UI ????????????
    private void updateMyActivityUI(Object badgeNum, Object carbonReduction, Object donationBadgeNum, Object setiType) {
        holdingBadgeNumTextView.setText(badgeNum.toString() + "???");
        carbonReductionTextView.setText(String.format("%.4f", Double.parseDouble(carbonReduction.toString())) + "kg");
        donationBadgeNumTextView.setText(donationBadgeNum + "???");

        if (setiType.toString().equalsIgnoreCase("SETI_NULL")) {
            mySetiType.setText("?????? ??????");
            setiRecommendationTextView.setText("SETI ????????? ?????? ??????");
            setiNullDescriptionText.setVisibility(View.VISIBLE);
            setiRecommendationRecyclerView.setVisibility(View.GONE);
        } else {
            mySetiType.setText(setiType.toString());
            setiRecommendationTextView.setText(setiType.toString() + " ?????? ?????? ??????");
            setiNullDescriptionText.setVisibility(View.GONE);
            setiRecommendationRecyclerView.setVisibility(View.VISIBLE);
        }

    }

    // SETI ????????? ?????? ?????? ????????????
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
                Log.e("Fragment_Home", "SETI ????????? ?????? ?????? ????????? ???????????? ??????");
            }
        });
    }


    // ?????? ?????? ??? <= ?????? ?????? ??? ?????? ??????
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
                            Toast.makeText(getContext(), "?????? ?????? ?????? ???????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show();
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

    // ?????? ????????? ????????????
    private void updateDonationCurrentData(String donation) {
        Long plus = Long.parseLong(donation);
        Long minus = 0 - plus;

        // ?????? ?????? ??????
        buttonActivated();

        // ????????? ????????????
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

    // ???????????? ?????? ?????? ????????????
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
        donationButtonTextView.setText("?????? ???");
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
        donationButtonTextView.setText("??????");
    }
}