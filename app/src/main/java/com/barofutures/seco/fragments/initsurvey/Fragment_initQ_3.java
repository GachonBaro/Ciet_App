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

public class Fragment_initQ_3 extends Fragment {
    private TextView question;
    private ToggleButton b1, b2, b3, b4, b5, b6;
    public static boolean isAnswered=false;

    public Fragment_initQ_3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 뷰 초기화 및 연결
        ViewGroup viewGroup=(ViewGroup)inflater.inflate(R.layout.fragment_initq_3, container, false);
        question=viewGroup.findViewById(R.id.init_question3);
        b1=viewGroup.findViewById(R.id.a3);
        b2=viewGroup.findViewById(R.id.b3);
        b3=viewGroup.findViewById(R.id.c3);
        b4=viewGroup.findViewById(R.id.d3);
        b5=viewGroup.findViewById(R.id.e3);
        b6=viewGroup.findViewById(R.id.f3);


        // 버튼들의 텍스트 설정
        b1.setText(InitQnA.C3[0]);
        b1.setTextOn(InitQnA.C3[0]);
        b1.setTextOff(InitQnA.C3[0]);

        b2.setText(InitQnA.C3[1]);
        b2.setTextOn(InitQnA.C3[1]);
        b2.setTextOff(InitQnA.C3[1]);

        b3.setText(InitQnA.C3[2]);
        b3.setTextOn(InitQnA.C3[2]);
        b3.setTextOff(InitQnA.C3[2]);

        b4.setText(InitQnA.C3[3]);
        b4.setTextOn(InitQnA.C3[3]);
        b4.setTextOff(InitQnA.C3[3]);

        b5.setText(InitQnA.C3[4]);
        b5.setTextOn(InitQnA.C3[4]);
        b5.setTextOff(InitQnA.C3[4]);

        b6.setText(InitQnA.C3[5]);
        b6.setTextOn(InitQnA.C3[5]);
        b6.setTextOff(InitQnA.C3[5]);

        // 문제 표시
        question.setText(InitQnA.Q3);

        // 정답 입력
        b1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && InitQnA.A3.indexOf(InitQnA.C3[0])==-1){
                    InitQnA.A3.add(InitQnA.C3[0]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
                else if(!isChecked && InitQnA.A3.indexOf(InitQnA.C3[0])!=-1) {
                    InitQnA.A3.remove(InitQnA.C3[0]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
            }
        });

        b2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && InitQnA.A3.indexOf(InitQnA.C3[1])==-1){
                    InitQnA.A3.add(InitQnA.C3[1]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
                else if(!isChecked && InitQnA.A3.indexOf(InitQnA.C3[1])!=-1) {
                    InitQnA.A3.remove(InitQnA.C3[1]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
            }
        });

        b3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && InitQnA.A3.indexOf(InitQnA.C3[2])==-1){
                    InitQnA.A3.add(InitQnA.C3[2]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
                else if(!isChecked && InitQnA.A3.indexOf(InitQnA.C3[2])!=-1) {
                    InitQnA.A3.remove(InitQnA.C3[2]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
            }
        });

        b4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && InitQnA.A3.indexOf(InitQnA.C3[3])==-1){
                    InitQnA.A3.add(InitQnA.C3[3]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
                else if(!isChecked && InitQnA.A3.indexOf(InitQnA.C3[3])!=-1) {
                    InitQnA.A3.remove(InitQnA.C3[3]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
            }
        });

        b5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && InitQnA.A3.indexOf(InitQnA.C3[4])==-1){
                    InitQnA.A3.add(InitQnA.C3[4]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
                else if(!isChecked && InitQnA.A3.indexOf(InitQnA.C3[4])!=-1) {
                    InitQnA.A3.remove(InitQnA.C3[4]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
            }
        });

        b6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && InitQnA.A3.indexOf(InitQnA.C3[5])==-1){
                    InitQnA.A3.add(InitQnA.C3[5]);
                    // 문제 답할 시에만 버튼 활성화 되도록
                    setAnswered(getActivity());
                    isAnswered=true;
                }
                else if(!isChecked && InitQnA.A3.indexOf(InitQnA.C3[5])!=-1) {
                    InitQnA.A3.remove(InitQnA.C3[5]);
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