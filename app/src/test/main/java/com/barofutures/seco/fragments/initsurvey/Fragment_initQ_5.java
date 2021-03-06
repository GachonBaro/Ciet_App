package com.barofutures.seco.fragments.initsurvey;

import static com.barofutures.seco.InitialSurveyActivity.setAnswered;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.barofutures.seco.R;
import com.barofutures.seco.model.InitQnA;

public class Fragment_initQ_5 extends Fragment {
    private TextView question;
    private RadioGroup radioGroup;
    public static boolean isAnswered=false;

    public Fragment_initQ_5() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 뷰 초기화 및 연결
        ViewGroup viewGroup=(ViewGroup)inflater.inflate(R.layout.fragment_initq_5, container, false);
        question=viewGroup.findViewById(R.id.init_question5);
        radioGroup=viewGroup.findViewById(R.id.q5_radiogroup);

        // 문제 표시
        question.setText(InitQnA.Q5);

        // 라디오 버튼들의 텍스트 설정
        for(int i=0; i<radioGroup.getChildCount(); i++) {
            ((RadioButton) radioGroup.getChildAt(i)).setText(InitQnA.C458[i]);
        }

        // 정답 입력
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.a5:
                        InitQnA.A5=InitQnA.C458[0];
                        // 문제 답할 시에만 버튼 활성화 되도록
                        setAnswered(getActivity());
                        isAnswered=true;
                        break;
                    case R.id.b5:
                        InitQnA.A5=InitQnA.C458[1];
                        // 문제 답할 시에만 버튼 활성화 되도록
                        setAnswered(getActivity());
                        isAnswered=true;
                        break;
                }
            }
        });

        return viewGroup;
    }

    // 다시 돌아왔을 때 답변이 체크되어 있으면 버튼 활성화
    @Override
    public void onResume() {
        super.onResume();
        if(isAnswered){
            setAnswered(getActivity());
        }
    }
}