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
                    // 걷기, 자전거로 출퇴근, 플로깅인 경우
                    if (activityData.get(position).equalsIgnoreCase("걷기")
                        ||activityData.get(position).equalsIgnoreCase("자전거로 출퇴근")
                        ||activityData.get(position).equalsIgnoreCase("플로깅")){
                        Intent authIntent = new Intent(mContext, WalkingAuthActivity.class);
                        authIntent.putExtra("title", activityData.get(position));
                        authIntent.putExtra("badgeCriteria", activityListData.activityNum.get(indexOfList));
                        authIntent.putExtra("badgeNum", activityListData.badgeNum.get(indexOfList));
                        authIntent.putExtra("carbonReduction", activityListData.carbonReduction.get(indexOfList));
                        mContext.startActivity(authIntent);
                    }
                    // 계단 이용하기 인 경우
                    else if (activityData.get(position).equalsIgnoreCase("계단 이용하기")) {
                        Intent authIntent = new Intent(mContext, SteppingAuthActivity.class);
                        authIntent.putExtra("title", activityData.get(position));
                        authIntent.putExtra("badgeCriteria", activityListData.activityNum.get(indexOfList));
                        authIntent.putExtra("badgeNum", activityListData.badgeNum.get(indexOfList));
                        authIntent.putExtra("carbonReduction", activityListData.carbonReduction.get(indexOfList));
                        mContext.startActivity(authIntent);
                    }
                    // 나머지 사진 인증하는 활동인 경우
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
