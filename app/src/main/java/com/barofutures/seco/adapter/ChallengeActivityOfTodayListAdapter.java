package com.barofutures.seco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class ChallengeActivityOfTodayListAdapter extends RecyclerView.Adapter<ChallengeActivityOfTodayListAdapter.ViewHolder>{
    Context mContext;
    ArrayList<String> activityData;
    ArrayList<Boolean> completionData;

    public ChallengeActivityOfTodayListAdapter(ArrayList<String> activityData, ArrayList<Boolean> completionData) {
        this.activityData = activityData;
        this.completionData = completionData;
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
        holder.name.setText(activityData.get(position));

        if (completionData.get(position)) {
            holder.auth_check_text.setText("인증 완료");
            holder.auth_check_image.setImageResource(R.drawable.ic_button_setiq_checked);
            holder.layout.setClickable(false);
        } else {
            holder.auth_check_text.setText("인증 미완료");
            holder.auth_check_image.setImageResource(R.drawable.ic_button_setiq_unchecked);
            holder.layout.setClickable(true);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 클릭하면 해당 활동 인증 activity로 이동

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return activityData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;        // 레이아웃
        TextView name;                     // 오늘의 활동 이름
        ImageView auth_check_image;       // 인증 완료/미완료 표시 이미지
        TextView auth_check_text;      // 인증 완료/미완료 표시 텍스트

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (ConstraintLayout) itemView.findViewById(R.id.item_activity_of_today_layout);
            name = (TextView) itemView.findViewById(R.id.item_activity_of_today_name_text_view);
            auth_check_image = (ImageView) itemView.findViewById(R.id.item_activity_of_today_auth_completion_image_view);
            auth_check_text = (TextView) itemView.findViewById(R.id.item_activity_of_today_auth_completion_text_view);
        }
    }
}
