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
package com.barofutures.seco.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.SetiSurveyActivity;
import com.barofutures.seco.model.SetiQnA;


public class SetiSurveyAdapter extends RecyclerView.Adapter<SetiSurveyAdapter.ViewHolder> {
    private int pageNum;
    private int answeredCount=0;

    public SetiSurveyAdapter (int pageNum) { this.pageNum=pageNum; }

    @NonNull
    @Override
    public SetiSurveyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seti_survey_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetiSurveyAdapter.ViewHolder holder, int position) {
        holder.question.setText(SetiQnA.question[position+6*pageNum]);
        holder.questionNum.setText(String.format("%02d. ", position + 1+6*pageNum));
        holder.range1.setText(SetiQnA.choice[position+6*pageNum][0]);
        holder.range2.setText(SetiQnA.choice[position+6*pageNum][1]);
        holder.range3.setText(SetiQnA.choice[position+6*pageNum][2]);
        holder.range4.setText(SetiQnA.choice[position+6*pageNum][3]);
        holder.range5.setText(SetiQnA.choice[position+6*pageNum][4]);

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                answeredCount++;
                Log.d("tagtag", String.valueOf(answeredCount));
                // 한 페이지의 마지막 문제 (5번째 문제) 까지 확인
                if(position==5){
                    if(answeredCount==6){
                        SetiSurveyActivity.isComplete[pageNum]=true;
                        ChangeButtonState changeButtonState=(ChangeButtonState) group.getContext();
                        if(changeButtonState!=null){
                            // 마지막 페이지일 경우
                            if(pageNum==4){
                                changeButtonState.activateFinishButton();
                            }else{
                                changeButtonState.activateNextButton();
                            }
                        }
                        answeredCount=0;
                    }
                }
                switch(checkedId){
                    case R.id.setiq_radio_0:
                        SetiQnA.answer[position+6*pageNum]=0;
                        break;
                    case R.id.setiq_radio_1:
                        SetiQnA.answer[position+6*pageNum]=1;
                        break;
                    case R.id.setiq_radio_2:
                        SetiQnA.answer[position+6*pageNum]=2;
                        break;
                    case R.id.setiq_radio_3:
                        SetiQnA.answer[position+6*pageNum]=3;
                        break;
                    case R.id.setiq_radio_4:
                        SetiQnA.answer[position+6*pageNum]=4;
                        break;
                }
            }
        });
    }

    // 프래그먼트의 버튼 활성화를 위한 인터페이스
    public interface ChangeButtonState{
        void activateNextButton();
        void deactivateNextButton();
        void activateFinishButton();
        void deactivateFinishButton();
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView questionNum;
        TextView range1;
        TextView range2;
        TextView range3;
        TextView range4;
        TextView range5;
        RadioGroup radioGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.question_text);
            questionNum = (TextView) itemView.findViewById(R.id.question_num);
            range1 = (TextView) itemView.findViewById(R.id.range_text1);
            range2 = (TextView) itemView.findViewById(R.id.range_text2);
            range3 = (TextView) itemView.findViewById(R.id.range_text3);
            range4 = (TextView) itemView.findViewById(R.id.range_text4);
            range5 = (TextView) itemView.findViewById(R.id.range_text5);
            radioGroup=(RadioGroup)itemView.findViewById(R.id.setiq_radiogroup);
        }
    }


}
