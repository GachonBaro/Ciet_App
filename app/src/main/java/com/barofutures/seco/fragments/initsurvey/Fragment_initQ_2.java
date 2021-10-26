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

import static com.barofutures.seco.InitialSurveyActivity.setAnswered;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.barofutures.seco.R;
import com.barofutures.seco.model.InitQnA;

public class Fragment_initQ_2 extends Fragment {
    private TextView question;
    private ToggleButton b1, b2, b3, b4, b5;
    public static boolean isAnswered=false;

    public Fragment_initQ_2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 뷰 초기화 및 연결
        ViewGroup viewGroup=(ViewGroup)inflater.inflate(R.layout.fragment_initq_2, container, false);
        question=viewGroup.findViewById(R.id.init_question2);
        b1=viewGroup.findViewById(R.id.a2);
        b2=viewGroup.findViewById(R.id.b2);
        b3=viewGroup.findViewById(R.id.c2);
        b4=viewGroup.findViewById(R.id.d2);
        b5=viewGroup.findViewById(R.id.e2);

        // 버튼들의 텍스트 설정
        b1.setText(InitQnA.C2[0]);
        b1.setTextOn(InitQnA.C2[0]);
        b1.setTextOff(InitQnA.C2[0]);

        b2.setText(InitQnA.C2[1]);
        b2.setTextOn(InitQnA.C2[1]);
        b2.setTextOff(InitQnA.C2[1]);

        b3.setText(InitQnA.C2[2]);
        b3.setTextOn(InitQnA.C2[2]);
        b3.setTextOff(InitQnA.C2[2]);

        b4.setText(InitQnA.C2[3]);
        b4.setTextOn(InitQnA.C2[3]);
        b4.setTextOff(InitQnA.C2[3]);

        b5.setText(InitQnA.C2[4]);
        b5.setTextOn(InitQnA.C2[4]);
        b5.setTextOff(InitQnA.C2[4]);

        // 문제 표시
        question.setText(InitQnA.Q2);

        // 정답 입력
        b1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && InitQnA.A2.indexOf(InitQnA.C2[0])==-1){
                        InitQnA.A2.add(InitQnA.C2[0]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                    }
                else if(!isChecked && InitQnA.A2.indexOf(InitQnA.C2[0])!=-1) {
                    InitQnA.A2.remove(InitQnA.C2[0]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
            }
        });

        b2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && InitQnA.A2.indexOf(InitQnA.C2[1])==-1){
                    InitQnA.A2.add(InitQnA.C2[1]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
                else if(!isChecked && InitQnA.A2.indexOf(InitQnA.C2[1])!=-1) {
                    InitQnA.A2.remove(InitQnA.C2[1]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
            }
        });

        b3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && InitQnA.A2.indexOf(InitQnA.C2[2])==-1){
                    InitQnA.A2.add(InitQnA.C2[2]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
                else if(!isChecked && InitQnA.A2.indexOf(InitQnA.C2[2])!=-1) {
                    InitQnA.A2.remove(InitQnA.C2[2]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
            }
        });

        b4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && InitQnA.A2.indexOf(InitQnA.C2[3])==-1){
                    InitQnA.A2.add(InitQnA.C2[3]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
                else if(!isChecked && InitQnA.A2.indexOf(InitQnA.C2[3])!=-1) {
                    InitQnA.A2.remove(InitQnA.C2[3]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
            }
        });

        b5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && InitQnA.A2.indexOf(InitQnA.C2[4])==-1){
                    InitQnA.A2.add(InitQnA.C2[4]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
                else if(!isChecked && InitQnA.A2.indexOf(InitQnA.C2[4])!=-1) {
                    InitQnA.A2.remove(InitQnA.C2[4]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
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