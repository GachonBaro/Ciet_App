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

public class DonationHistoryListAdapter extends RecyclerView.Adapter<DonationHistoryListAdapter.ViewHolder>{
    Context mContext;
    ArrayList<Map<String, Object>> data;


    public DonationHistoryListAdapter(ArrayList<Map<String, Object>> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public DonationHistoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_donation_history_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationHistoryListAdapter.ViewHolder holder, int position) {

        holder.donationDate.setText(data.get(position).get("date").toString().substring(0, 10));
        holder.time.setText(data.get(position).get("date").toString().substring(11, 13) + ":"
                + data.get(position).get("date").toString().substring(13, 15));
        holder.badgeNum.setText("배지 " + data.get(position).get("badgeNum").toString() + " 개 기부");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView donationDate;
        TextView time;
        TextView badgeNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            donationDate = (TextView) itemView.findViewById(R.id.item_donation_history_list_start_date_text_view);
            time = (TextView) itemView.findViewById(R.id.item_donation_history_list_end_time_text_view);
            badgeNum = (TextView) itemView.findViewById(R.id.item_donation_history_list_badge_num_data_text_view);
        }
    }
}
