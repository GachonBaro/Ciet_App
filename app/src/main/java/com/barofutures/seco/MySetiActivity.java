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

        // ????????? ?????? ??????
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar ??????
        toolbar=findViewById(R.id.activity_myseti_material_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Status Bar ???????????? Padding ??????
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        // View ?????? ??? ?????????

        progressViewKnowledge = (ProgressView) findViewById(R.id.activity_myseti_knowledge_progressbar);
        progressViewStatus = (ProgressView) findViewById(R.id.activity_myseti_status_progressbar);
        progressViewWillingness = (ProgressView) findViewById(R.id.activity_myseti_willingness_progress_bar);
        typeDescription = (TextView) findViewById(R.id.activity_myseti_type_description);
        setiSummary = findViewById(R.id.activity_myseti_type);
        retestButton = findViewById(R.id.activity_myseti_retest_button);

        // SETI ???????????? ?????? ????????? ?????????
        retestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SetiSurveyIntroActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        // Firestore?????? SETI ?????? ?????? ???????????? ??????
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
                        // SETI ??????????????? ????????? ??????????????? ??????
                        retestButton.setText("SETI ????????????");
                        setiSummary.setText("SETI ???????????? ????????????.");
                        typeDescription.setText("SETI ???????????? ????????????.\nSETI ???????????? ???????????????.");
                    }
                } else {
                    Log.d("MySetiActivity", "get failed with ", task.getException());
                }
            }
        });

    }

    // update UI
    public void updateUI(String type, long understanding, long practice, long intent) {
        displayResult=new SpannableString("????????? ????????? "+ type +" ?????????.");
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

    // ?????? ???????????? ??????
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar??? back??? ????????? ??? ??????
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("email", email);
                // ????????? ??????: ?????? ??????????????? ??????????????? ????????? onCreate??? ???????????? ?????? onNewIntent??? ???????????? ?????? ??????
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                return true;
            }
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