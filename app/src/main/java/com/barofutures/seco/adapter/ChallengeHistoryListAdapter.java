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

public class ChallengeHistoryListAdapter extends RecyclerView.Adapter<ChallengeHistoryListAdapter.ViewHolder>{
    Context mContext;
    ArrayList<Map<String, Object>> data;


    public ChallengeHistoryListAdapter(ArrayList<Map<String, Object>> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ChallengeHistoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_challenge_history_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeHistoryListAdapter.ViewHolder holder, int position) {
        holder.startDate.setText(data.get(position).get("startDate").toString());
        holder.endDate.setText(data.get(position).get("endDate").toString());
        holder.succeed.setText(data.get(position).get("succeed").toString());
        holder.badgeNum.setText(data.get(position).get("additionalBadgeNum").toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView startDate;
        TextView endDate;
        TextView succeed;
        TextView badgeNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startDate = (TextView) itemView.findViewById(R.id.item_challenge_history_list_start_date_text_view);
            endDate = (TextView) itemView.findViewById(R.id.item_challenge_history_list_end_date_text_view);
            succeed = (TextView) itemView.findViewById(R.id.item_challenge_history_list_success_failure_data_text_view);
            badgeNum = (TextView) itemView.findViewById(R.id.item_challenge_history_list_badge_num_data_text_view);
        }
    }
}
