package com.barofutures.seco.fragments.initsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.barofutures.seco.MainActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.SetiSurveyActivity;
import com.barofutures.seco.SetiSurveyIntroActivity;
import com.barofutures.seco.firebase.firestore.UserInfoData;
import com.barofutures.seco.model.InitQnA;
import com.barofutures.seco.model.SetiQnA;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
                Intent intent=new Intent(getActivity(), SetiSurveyIntroActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // 나중에 할 때
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitQnA.A8=InitQnA.C458[1];
                InitQnA.isAsked=true;
                SetiQnA.testLater=true;
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return viewGroup;
    }

    // Firestore에 UserInfo 저장
    private void storeUserInfo() {
//        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(UserInfoData.getUID());

    }
}