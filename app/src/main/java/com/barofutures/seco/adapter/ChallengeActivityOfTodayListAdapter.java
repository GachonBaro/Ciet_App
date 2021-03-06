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

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.PhotoAuthActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.SteppingAuthActivity;
import com.barofutures.seco.WalkingAuthActivity;
import com.barofutures.seco.model.ContentsDetailData;

import java.util.ArrayList;

public class ChallengeActivityOfTodayListAdapter extends RecyclerView.Adapter<ChallengeActivityOfTodayListAdapter.ViewHolder>{
    Context mContext;
    ArrayList<String> activityData;
    ArrayList<Boolean> completionData;
    ContentsDetailData activityListData;

    public ChallengeActivityOfTodayListAdapter(ArrayList<String> activityData, ArrayList<Boolean> completionData) {
        this.activityData = activityData;
        this.completionData = completionData;
        activityListData = new ContentsDetailData();
        activityListData.setMeal();
        activityListData.setActivity();
        activityListData.setQuest();
    }

    @NonNull
    @Override
    public ChallengeActivityOfTodayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_activity_of_today, parent, false);
        return new ChallengeActivityOfTodayListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeActivityOfTodayListAdapter.ViewHolder holder, int position) {
        int indexOfList = activityListData.title.indexOf(activityData.get(position));

        holder.name.setText(activityData.get(position));

        if (completionData.get(position)) {
            holder.auth_check_text.setText("?????? ??????");
            holder.auth_check_image.setImageResource(R.drawable.ic_button_setiq_checked);
            holder.layout.setClickable(false);
        } else {
            holder.auth_check_text.setText("?????? ?????????");
            holder.auth_check_image.setImageResource(R.drawable.ic_button_setiq_unchecked);
            holder.layout.setClickable(true);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ??????, ???????????? ?????????, ???????????? ??????
                    if (activityData.get(position).equalsIgnoreCase("??????")
                        ||activityData.get(position).equalsIgnoreCase("???????????? ?????????")
                        ||activityData.get(position).equalsIgnoreCase("?????????")){
                        Intent authIntent = new Intent(mContext, WalkingAuthActivity.class);
                        authIntent.putExtra("title", activityData.get(position));
                        authIntent.putExtra("badgeCriteria", activityListData.activityNum.get(indexOfList));
                        authIntent.putExtra("badgeNum", activityListData.badgeNum.get(indexOfList));
                        authIntent.putExtra("carbonReduction", activityListData.carbonReduction.get(indexOfList));
                        mContext.startActivity(authIntent);
                    }
                    // ?????? ???????????? ??? ??????
                    else if (activityData.get(position).equalsIgnoreCase("?????? ????????????")) {
                        Intent authIntent = new Intent(mContext, SteppingAuthActivity.class);
                        authIntent.putExtra("title", activityData.get(position));
                        authIntent.putExtra("badgeCriteria", activityListData.activityNum.get(indexOfList));
                        authIntent.putExtra("badgeNum", activityListData.badgeNum.get(indexOfList));
                        authIntent.putExtra("carbonReduction", activityListData.carbonReduction.get(indexOfList));
                        mContext.startActivity(authIntent);
                    }
                    // ????????? ?????? ???????????? ????????? ??????
                    else {
                        Intent authIntent = new Intent(mContext, PhotoAuthActivity.class);
                        authIntent.putExtra("title", activityData.get(position));
                        authIntent.putExtra("badgeNum", activityListData.badgeNum.get(indexOfList));
                        authIntent.putExtra("carbonReduction", activityListData.carbonReduction.get(indexOfList));
                        mContext.startActivity(authIntent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (completionData == null)
            return 0;
        return completionData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;        // ????????????
        TextView name;                     // ????????? ?????? ??????
        ImageView auth_check_image;       // ?????? ??????/????????? ?????? ?????????
        TextView auth_check_text;      // ?????? ??????/????????? ?????? ?????????

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (ConstraintLayout) itemView.findViewById(R.id.item_activity_of_today_layout);
            name = (TextView) itemView.findViewById(R.id.item_activity_of_today_name_text_view);
            auth_check_image = (ImageView) itemView.findViewById(R.id.item_activity_of_today_auth_completion_image_view);
            auth_check_text = (TextView) itemView.findViewById(R.id.item_activity_of_today_auth_completion_text_view);
        }
    }
}
