package com.barofutures.seco.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;

import org.w3c.dom.Text;

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
        } else if (position == 1) {     // '중급'
            temp = data.get("중급").toString();
            text += "3주 동안 매주\n";
            holder.title.setText("중급");
        } else if (position == 2) {     // '고급'
            temp = data.get("고급").toString();
            text += "4주 동안 매주\n";
            holder.title.setText("고급");
        }

//        Log.d("ChallengeRecommendationListAdapter", temp);
        String[] tempArr = temp.split("\\|");
//        for (int i = 0; i < tempArr.length; i++ ) {
//            Log.d("ChallengeRecommendationListAdapter", tempArr[i]);
//        }

        int rsc = Integer.parseInt(tempArr[0]);
//        Log.d("ChallengeRecommendationListAdapter", String.valueOf(rsc));
        holder.representativeImage.setImageResource(rsc);

        for (int i = 1; i < tempArr.length; i++) {
            String[] strArr = tempArr[i].split("_");
            text += "- " + strArr[0] + " " + strArr[1] + "회\n";
        }
        holder.activityListTextView.setText(text);

        // Click Event
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 챌린지 설정으로 이동
                Toast.makeText(mContext, "챌린지 설정으로 이동", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, );
//                intent.putExtra("recommend_activity_list", temp);
//                mContext.startActivity(intent);
            }
        });
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
