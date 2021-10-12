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
import com.barofutures.seco.SetiSurveyActivity;
import com.barofutures.seco.SetiSurveyIntroActivity;
import com.barofutures.seco.firebase.firestore.UserInfoData;
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
//                Intent intent=new Intent(getActivity(), SetiSurveyIntroActivity.class);
//                startActivity(intent);
//                getActivity().finish();
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
//                Intent intent=new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//                getActivity().finish();
            }
        });

        return viewGroup;
    }

    // Firestore에 UserInfo 저장하고 환경유형검사로 이동
    private void storeUserInfoAndGoSETI() {

        //TODO: 여기 하는중 - 닉네임 등 업데이
        // Get a new write batch
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        DocumentReference docRef = db.collection("users").document(InitialSurveyActivity.uid).collection("user_info").document("current");
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
                Intent intent=new Intent(getActivity(), SetiSurveyIntroActivity.class);
                intent.putExtra("UID", InitialSurveyActivity.uid);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    // Firestore에 UserInfo 저장하고 메인화면으로 이동
    private void storeUserInfoAndGoMain() {

        //TODO: 여기 하는중 - 닉네임 등 업데이

        // Get a new write batch
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        DocumentReference docRef = db.collection("users").document(InitialSurveyActivity.uid).collection("user_info").document("current");
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
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}