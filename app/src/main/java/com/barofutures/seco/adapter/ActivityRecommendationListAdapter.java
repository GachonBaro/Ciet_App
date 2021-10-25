package com.barofutures.seco.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class ActivityRecommendationListAdapter extends RecyclerView.Adapter<ActivityRecommendationListAdapter.ViewHolder>{
    Context mContext;
    ArrayList<String> data;

    // 활동 리스트
    private ContentsDetailData activityListData;

    public ActivityRecommendationListAdapter(ArrayList<String> data) {
        this.data = data;
        activityListData = new ContentsDetailData();
        activityListData.setMeal();
        activityListData.setActivity();
        activityListData.setQuest();
    }

    @NonNull
    @Override
    public ActivityRecommendationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_activity_recommendation_list, parent, false);
        return new ActivityRecommendationListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityRecommendationListAdapter.ViewHolder holder, int position) {
        Log.d("ActivityRecommendationListAdapter", data.get(position));

        int indexOfList = activityListData.title.indexOf(data.get(position));

        holder.layout.setClickable(true);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 걷기, 자전거로 출퇴근, 플로깅인 경우
                if (data.get(position).equalsIgnoreCase("걷기")
                        ||data.get(position).equalsIgnoreCase("자전거로 출퇴근")
                        ||data.get(position).equalsIgnoreCase("플로깅")){
                    Intent authIntent = new Intent(mContext, WalkingAuthActivity.class);
                    authIntent.putExtra("title", data.get(position));
                    authIntent.putExtra("badgeCriteria", activityListData.activityNum.get(indexOfList));
                    authIntent.putExtra("badgeNum", activityListData.badgeNum.get(indexOfList));
                    authIntent.putExtra("carbonReduction", activityListData.carbonReduction.get(indexOfList));
                    mContext.startActivity(authIntent);
                }
                // 계단 이용하기 인 경우
                else if (data.get(position).equalsIgnoreCase("계단 이용하기")) {
                    Intent authIntent = new Intent(mContext, SteppingAuthActivity.class);
                    authIntent.putExtra("title", data.get(position));
                    authIntent.putExtra("badgeCriteria", activityListData.activityNum.get(indexOfList));
                    authIntent.putExtra("badgeNum", activityListData.badgeNum.get(indexOfList));
                    authIntent.putExtra("carbonReduction", activityListData.carbonReduction.get(indexOfList));
                    mContext.startActivity(authIntent);
                }
                // 나머지 사진 인증하는 활동인 경우
                else {
                    Intent authIntent = new Intent(mContext, PhotoAuthActivity.class);
                    authIntent.putExtra("title", data.get(position));
                    authIntent.putExtra("badgeNum", activityListData.badgeNum.get(indexOfList));
                    authIntent.putExtra("carbonReduction", activityListData.carbonReduction.get(indexOfList));
                    mContext.startActivity(authIntent);
                }
            }
        });

        holder.activityImage.setImageResource(activityListData.image.get(indexOfList));
        holder.title.setText(data.get(position));
        holder.carbonReductionText.setText(activityListData.carbonReduction.get(indexOfList));
        holder.badgeNumText.setText("배지 " + activityListData.badgeNum.get(indexOfList) + "개");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;
        ImageView activityImage;       // 대표 활동 사진
        TextView title;                     // 활동 이름
        TextView carbonReductionText;      // 탄소 저감량
        TextView badgeNumText;          // 배지 개수

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (ConstraintLayout) itemView.findViewById(R.id.item_activity_recommendation_list_layout);
            activityImage = (ImageView) itemView.findViewById(R.id.item_activity_recommendation_list_image_view);
            title = (TextView) itemView.findViewById(R.id.item_activity_recommendation_list_title_text_view);
            carbonReductionText = (TextView) itemView.findViewById(R.id.item_activity_recommendation_list_carbon_reduction_text_view);
            badgeNumText = (TextView) itemView.findViewById(R.id.item_activity_recommendation_list_badge_num_text_view);
        }
    }
}
