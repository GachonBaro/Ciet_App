package com.barofutures.seco.fragments.initsurvey;

import static com.barofutures.seco.InitialSurveyActivity.nextButton;
import static com.barofutures.seco.InitialSurveyActivity.setAnswered;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.barofutures.seco.InitialSurveyActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.model.InitQnA;

public class Fragment_initQ_0 extends Fragment {
    private TextView question;
    private RadioGroup radioGroup1, radioGroup2;
    public static boolean isAnswered=false;

    public Fragment_initQ_0() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 뷰 초기화 및 연결
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_initq_0, container, false);
        question = viewGroup.findViewById(R.id.init_question0);
        radioGroup1 = viewGroup.findViewById(R.id.q0_radiogroup1);
        radioGroup2 = viewGroup.findViewById(R.id.q0_radiogroup2);
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        radioGroup1.setOnCheckedChangeListener(listener1);
        radioGroup2.setOnCheckedChangeListener(listener2);

        // 문제 표시
        question.setText(InitQnA.Q0);

        // 라디오 버튼들의 텍스트 설정
        for (int i = 0; i < radioGroup1.getChildCount(); i++) {
            ((RadioButton) radioGroup1.getChildAt(i)).setText(InitQnA.C0[i]);
        }
        for (int i = 0; i < radioGroup2.getChildCount(); i++) {
            ((RadioButton) radioGroup2.getChildAt(i)).setText(InitQnA.C0[radioGroup1.getChildCount() + i]);
        }
        return viewGroup;
    }



    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                radioGroup2.setOnCheckedChangeListener(null);
                radioGroup2.clearCheck();
                radioGroup2.setOnCheckedChangeListener(listener2);
                isAnswered=true;
                switch (checkedId) {
                    case R.id.a0:
                        InitQnA.A0 = InitQnA.C0[0];
                        // 문제 답할 시에만 버튼 활성화 되도록
                        setAnswered(getActivity());
                        break;
                    case R.id.b0:
                        InitQnA.A0 = InitQnA.C0[1];
                        setAnswered(getActivity());
                        break;
                    case R.id.c0:
                        InitQnA.A0 = InitQnA.C0[2];
                        setAnswered(getActivity());
                        break;
                    case R.id.d0:
                        InitQnA.A0 = InitQnA.C0[3];
                        setAnswered(getActivity());
                        break;
                    case R.id.e0:
                        InitQnA.A0 = InitQnA.C0[4];
                        setAnswered(getActivity());
                        break;

                    case R.id.f0:
                        InitQnA.A0 = InitQnA.C0[5];
                        setAnswered(getActivity());
                        break;
                }
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                radioGroup1.setOnCheckedChangeListener(null);
                radioGroup1.clearCheck();
                radioGroup1.setOnCheckedChangeListener(listener1);

                switch (checkedId) {
                    case R.id.g0:
                        InitQnA.A0 = InitQnA.C0[6];
                        setAnswered(getActivity());
                        break;
                    case R.id.h0:
                        InitQnA.A0 = InitQnA.C0[7];
                        setAnswered(getActivity());
                        break;
                    case R.id.i0:
                        InitQnA.A0 = InitQnA.C0[8];
                        setAnswered(getActivity());
                        break;
                    case R.id.j0:
                        InitQnA.A0 = InitQnA.C0[9];
                        setAnswered(getActivity());
                        break;
                    case R.id.k0:
                        InitQnA.A0 = InitQnA.C0[10];
                        setAnswered(getActivity());
                        break;
                }
            }
        }
    };

    // 다시 돌아왔을 때 답변이 체크되어 있으면 버튼 활성화
    @Override
    public void onResume() {
        super.onResume();
        if(isAnswered){
            setAnswered(getActivity());
        }
    }
}