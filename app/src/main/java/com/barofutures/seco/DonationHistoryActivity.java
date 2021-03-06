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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.barofutures.seco.adapter.DonationHistoryListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class DonationHistoryActivity extends AppCompatActivity {

    // user data
    String email;

    private Toolbar toolbar;
    private RecyclerView donationHistoryRecyclerView;
    private DonationHistoryListAdapter donationHistoryListAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        setLayout();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_history);

        // get intent
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        // ????????? ?????? ??????
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar ??????
        toolbar = findViewById(R.id.activity_donation_history_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Status Bar ???????????? Padding ??????
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        donationHistoryRecyclerView = findViewById(R.id.activity_donation_history_recycler_view);
    }

    // ?????? ?????? ????????? firebase?????? ???????????? ????????? ??????
    private void setLayout() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ref = db.collection("users").document(email).collection("donation");
        ref.orderBy("date", Query.Direction.ASCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Map<String, Object>> dataList = new ArrayList<>();
                for (DocumentSnapshot dataDoc : queryDocumentSnapshots.getDocuments()) {
                    Map<String, Object> data = dataDoc.getData();
                    dataList.add(data);
                }

                donationHistoryListAdapter = new DonationHistoryListAdapter(dataList);
                donationHistoryRecyclerView.setAdapter(donationHistoryListAdapter);
                donationHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                donationHistoryListAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("DonationHistoryActivity", e.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:     // toolbar??? ???????????? ????????? ????????? ???
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //status bar??? ?????? ??????
    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }
}