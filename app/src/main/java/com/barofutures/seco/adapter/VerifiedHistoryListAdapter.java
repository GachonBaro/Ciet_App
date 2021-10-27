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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;

import java.util.ArrayList;
import java.util.Map;

public class VerifiedHistoryListAdapter extends RecyclerView.Adapter<VerifiedHistoryListAdapter.ViewHolder>{
    Context mContext;
    ArrayList<Map<String, Object>> data;


    public VerifiedHistoryListAdapter(ArrayList<Map<String, Object>> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_verified_history_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.authDate.setText(data.get(position).get("authDate").toString());
        holder.endTime.setText(data.get(position).get("endTime").toString());
        holder.activityName.setText(data.get(position).get("activityName").toString());
        holder.carbonReduction.setText(data.get(position).get("carbonReductionAmount").toString());
        holder.badgeNum.setText(data.get(position).get("earnedBadgeNum").toString());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView authDate;
        TextView endTime;
        TextView activityName;
        TextView carbonReduction;
        TextView badgeNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            authDate = (TextView) itemView.findViewById(R.id.item_verified_history_list_auth_date_text_view);
            endTime = (TextView) itemView.findViewById(R.id.item_verified_history_list_end_time_text_view);
            activityName = (TextView) itemView.findViewById(R.id.item_verified_history_list_activity_name_text_view);
            carbonReduction = (TextView) itemView.findViewById(R.id.item_verified_history_list_carbon_reduction_data_text_view);
            badgeNum = (TextView) itemView.findViewById(R.id.item_verified_history_list_badge_num_data_text_view);
        }
    }
}
