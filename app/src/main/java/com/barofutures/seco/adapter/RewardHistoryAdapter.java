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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.model.RewardHistory;
import com.bumptech.glide.Glide;
import com.skydoves.progressview.ProgressView;

public class RewardHistoryAdapter extends RecyclerView.Adapter<RewardHistoryAdapter.ViewHolder> {
    // Constructor
    public RewardHistoryAdapter(){};

    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, uploadDate, rewardDate, status, progress1, progress2, progress3;
        ImageView image;
        ProgressView progressView;

        public ViewHolder(View itemView){
            super(itemView);

            status=itemView.findViewById(R.id.activity_reward_history_title_status);
            name=itemView.findViewById(R.id.activity_reward_history_name);
            uploadDate=itemView.findViewById(R.id.activity_reward_upload_date);
            rewardDate=itemView.findViewById(R.id.activity_reward_date);
            image=itemView.findViewById(R.id.activity_reward_history_image);
            progressView=itemView.findViewById(R.id.activity_reward_progressbar);
            progress1=itemView.findViewById(R.id.activity_reward_progress_text_1);
            progress2=itemView.findViewById(R.id.activity_reward_progress_text_2);
            progress3=itemView.findViewById(R.id.activity_reward_progress_text_3);
        }
    }

    @Override
    public RewardHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reward_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.status.setText(RewardHistory.RewardHistoryData[position][0]);
        Glide.with(holder.image.getContext())
                .load(RewardHistory.RewardHistoryData[position][1])
                .into(holder.image);
        holder.name.setText(RewardHistory.RewardHistoryData[position][2]);
        holder.uploadDate.setText(RewardHistory.RewardHistoryData[position][3]);
        holder.rewardDate.setText(RewardHistory.RewardHistoryData[position][4]);
        holder.progressView.setProgress(Integer.parseInt(RewardHistory.RewardHistoryData[position][5]));
        switch(Integer.parseInt(RewardHistory.RewardHistoryData[position][5])){
            case 0:
                holder.progress1.setBackgroundResource(R.drawable.button_initq_checked);
                holder.progress1.setTextColor(holder.progress1.getContext().getResources().getColor(R.color.white));

                holder.progress2.setBackgroundResource(R.drawable.button_initq_unchecked);
                holder.progress2.setTextColor(holder.progress1.getContext().getResources().getColor(R.color.seco_deepgray));

                holder.progress3.setBackgroundResource(R.drawable.button_initq_unchecked);
                holder.progress3.setTextColor(holder.progress1.getContext().getResources().getColor(R.color.seco_deepgray));
                break;
            case 50:
                holder.progress1.setBackgroundResource(R.drawable.button_initq_unchecked);
                holder.progress1.setTextColor(holder.progress1.getContext().getResources().getColor(R.color.seco_deepgray));

                holder.progress2.setBackgroundResource(R.drawable.button_initq_checked);
                holder.progress2.setTextColor(holder.progress1.getContext().getResources().getColor(R.color.white));

                holder.progress3.setBackgroundResource(R.drawable.button_initq_unchecked);
                holder.progress3.setTextColor(holder.progress1.getContext().getResources().getColor(R.color.seco_deepgray));
                break;
            case 100:
                holder.progress1.setBackgroundResource(R.drawable.button_initq_unchecked);
                holder.progress1.setTextColor(holder.progress1.getContext().getResources().getColor(R.color.seco_deepgray));

                holder.progress2.setBackgroundResource(R.drawable.button_initq_unchecked);
                holder.progress2.setTextColor(holder.progress1.getContext().getResources().getColor(R.color.seco_deepgray));

                holder.progress3.setBackgroundResource(R.drawable.button_initq_checked);
                holder.progress3.setTextColor(holder.progress1.getContext().getResources().getColor(R.color.white));
                break;
        }
    }

    @Override
    public int getItemCount(){ return RewardHistory.RewardHistoryData.length; }
}
