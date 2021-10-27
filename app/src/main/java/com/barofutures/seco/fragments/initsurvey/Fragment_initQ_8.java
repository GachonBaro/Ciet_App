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
package com.barofutures.seco.fragments.initsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.barofutures.seco.InitialSurveyActivity;
import com.barofutures.seco.MainActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.SetiSurveyIntroActivity;
import com.barofutures.seco.model.InitQnA;
import com.barofutures.seco.model.SetiQnA;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

public class Fragment_initQ_8 extends Fragment {
    private TextView text;
    private Button b1, b2;



    public Fragment_initQ_8() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 뷰 초기화 및 연결
        ViewGroup viewGroup=(ViewGroup)inflater.inflate(R.layout.fragment_initq_8, container, false);
        text=viewGroup.findViewById(R.id.init_text_ask_SETI);
        b1=viewGroup.findViewById(R.id.init_button_gotoSETI);
        b2=viewGroup.findViewById(R.id.init_button_gotoHome);

        text.setText(InitQnA.Q8);
        b1.setText(InitQnA.C458[0]);
        b2.setText(InitQnA.C458[1]);

        // SETI 설문에 응할 때
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitQnA.A8=InitQnA.C458[0];
                InitQnA.isAsked=true;
                storeUserInfoAndGoSETI();
            }
        });

        // 나중에 할 때
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitQnA.A8=InitQnA.C458[1];
                InitQnA.isAsked=true;
                SetiQnA.testLater=true;
                storeUserInfoAndGoMain();
            }
        });

        return viewGroup;
    }

    // Firestore에 UserInfo 저장하고 환경유형검사로 이동
    private void storeUserInfoAndGoSETI() {
        // Get a new write batch
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        DocumentReference docRef = db.collection("users").document(InitialSurveyActivity.email).collection("user_info").document("current");
        // update user info
        batch.update(docRef, "job", InitQnA.A0);
        batch.update(docRef, "gender", InitQnA.A1);
        batch.update(docRef, "routineGoal", InitQnA.A2);
        batch.update(docRef, "interestActivity", InitQnA.A3);
        batch.update(docRef, "vegan", InitQnA.A4);
        batch.update(docRef, "carOwner", InitQnA.A5);
        batch.update(docRef, "activityDay", InitQnA.A6);
        batch.update(docRef, "nickname", InitQnA.A7);

        // Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("Fragment_initQ_8", "init survey update completed!!");
                Intent intent=new Intent(getActivity(), SetiSurveyIntroActivity.class);
                intent.putExtra("email", InitialSurveyActivity.email);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    // Firestore에 UserInfo 저장하고 메인화면으로 이동
    private void storeUserInfoAndGoMain() {

        // Get a new write batch
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        DocumentReference docRef = db.collection("users").document(InitialSurveyActivity.email).collection("user_info").document("current");
        // update user info
        batch.update(docRef, "job", InitQnA.A0);
        batch.update(docRef, "gender", InitQnA.A1);
        batch.update(docRef, "routineGoal", InitQnA.A2);
        batch.update(docRef, "interestActivity", InitQnA.A3);
        batch.update(docRef, "isVegan", InitQnA.A4);
        batch.update(docRef, "isCarOwner", InitQnA.A5);
        batch.update(docRef, "activityDay", InitQnA.A6);
        batch.update(docRef, "nickname", InitQnA.A7);

        // Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("Fragment_initQ_8", "init survey update completed!!");
                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.putExtra("email", InitialSurveyActivity.email);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}