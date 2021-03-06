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

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;

import java.util.Map;

public class ChallengeRecommendationListAdapter extends RecyclerView.Adapter<ChallengeRecommendationListAdapter.ViewHolder>  {
    Context mContext;
    Map<String, Object> data;

    public ChallengeRecommendationListAdapter(Map<String, Object> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ChallengeRecommendationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_challenge_recommendation_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ChallengeRecommendationListAdapter.ViewHolder holder, int position) {
        String temp = "";
        String text = "";
        if (position == 0) {        // '초급'
            temp = data.get("초급").toString();
            text += "2주 동안 매주\n";
            holder.title.setText("초급");
            holder.representativeImage.setImageResource(R.drawable.walking);
        } else if (position == 1) {     // '중급'
            temp = data.get("중급").toString();
            text += "3주 동안 매주\n";
            holder.title.setText("중급");
            holder.representativeImage.setImageResource(R.drawable.stepping);
        } else if (position == 2) {     // '고급'
            temp = data.get("고급").toString();
            text += "4주 동안 매주\n";
            holder.title.setText("고급");
            holder.representativeImage.setImageResource(R.drawable.cycling);
        }

        String[] tempArr = temp.split("\\|");

        int rsc = Integer.parseInt(tempArr[0]);
//        holder.representativeImage.setImageResource(rsc);
//        Log.d("ChallengeRecommendationListAdapter", String.valueOf(rsc));

        for (int i = 1; i < tempArr.length; i++) {
            String[] strArr = tempArr[i].split("_");
            text += "- " + strArr[0] + " " + strArr[1] + "회\n";
        }
        holder.activityListTextView.setText(text);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        TextView title;                     // 초급, 중급, 고급
        ImageView representativeImage;       // 대표 활동 사진
        TextView activityListTextView;      // 활동 리스트

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.item_challenge_recommendation_list_layout);
            representativeImage = (ImageView) itemView.findViewById(R.id.item_challenge_recommendation_list_image_view);
            activityListTextView = (TextView) itemView.findViewById(R.id.item_challenge_recommendation_list_activity_list_text_view);
            title = (TextView) itemView.findViewById(R.id.item_challenge_recommendation_list_title);
        }
    }
}
