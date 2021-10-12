package com.barofutures.seco.fragments.initsurvey;

import static com.barofutures.seco.InitialSurveyActivity.nextButton;
import static com.barofutures.seco.InitialSurveyActivity.setAnswered;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.barofutures.seco.R;
import com.barofutures.seco.model.InitQnA;

public class Fragment_initQ_7 extends Fragment {
    private TextView question, nickName;
    public static boolean isAnswered=false;

    public Fragment_initQ_7() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 뷰 초기화 및 연결
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_initq_7, container, false);
        nickName = viewGroup.findViewById(R.id.init_nickname);
        question = viewGroup.findViewById(R.id.init_question7);

        // 문제 표시
        question.setText(InitQnA.Q7);

        nickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(nickName.getText().length()==0){
                    // 문제 답할 시에만 버튼 활성화 되도록
                    nextButton.setBackgroundResource(R.drawable.button_initq_unchecked);
                    nextButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.seco_deepgray));
                    isAnswered=false;

                }else{
                    InitQnA.A7 = nickName.getText().toString();
                    setAnswered(getActivity());
                    isAnswered=true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return viewGroup;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        InitQnA.A7 = nickName.getText().toString();
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