package com.barofutures.seco.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.FreeModeContentsDetailsActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.model.ContentsDetailData;

public class FreeModeContentsListAdapter extends RecyclerView.Adapter<FreeModeContentsListAdapter.ViewHolder> {
    // Tab - 1(meal), 2(activity), 3(quest), 4(favorites)
    private int pageNum;        // the page # of viewPager2
    private ContentsDetailData data;
    private Context mContext;

    public FreeModeContentsListAdapter(int pageNum, ContentsDetailData data) {
        this.pageNum = pageNum;
        this.data = data;
    }

    @NonNull
    @Override
    public FreeModeContentsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_contents_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FreeModeContentsListAdapter.ViewHolder holder, int position) {
        // 배경 사진 설정
        holder.background.setImageResource(data.image.get(position));

        // 제목
        holder.title.setText(data.title.get(position));

        // 활동 횟수
        holder.numOfActivities.setText(data.activityNum.get(position) + " 완료 시");

        // 뱃지 개수
        holder.numOfBadge.setText(data.badgeNum.get(position) + "개 지급");

        holder.readMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FreeModeContentsDetailsActivity.class);
                intent.putExtra("background", data.image.get(position));
                intent.putExtra("title", data.title.get(position));
                intent.putExtra("summary", data.contents1.get(position));
                intent.putExtra("contents", data.contents2.get(position));
                intent.putExtra("badgeCriteria", data.activityNum.get(position));
                intent.putExtra("badgeNum", data.badgeNum.get(position));
                intent.putExtra("carbonReduction", data.carbonReduction.get(position));

                // eco-activity page인 경우
                if (pageNum == 1) {
                    intent.putExtra("caloriesConsumption", data.calorieConsumption.get(position));
                }

                intent.putExtra("page_number", pageNum);
                intent.putExtra("recycler_position", holder.getPosition());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.title.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView background;       // 배경 사진
        TextView title;             // 제목
        TextView numOfActivities;   // 활동 횟수
        TextView numOfBadge;        // 뱃지 개수
        Button readMoreButton;      // 자세히 보기 버튼

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            background = (ImageView) itemView.findViewById(R.id.item_free_mode_contents_image);
            title = (TextView) itemView.findViewById(R.id.contents_title_text_view);
            numOfActivities = (TextView) itemView.findViewById(R.id.num_of_activities_text);
            numOfBadge = (TextView) itemView.findViewById(R.id.num_of_badge_text);
            readMoreButton = (Button) itemView.findViewById(R.id.item_free_mode_read_more_button);

        }
    }

}
