package com.barofutures.seco.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.FreeModeContentsDetailsActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.fragments.Fragment_Home;
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

        // 좋아요 표시 아이콘 설정
        if (data.isFavorites.get(position))         // 사용자가 좋아요 표시
            holder.likeButton.setImageResource(R.drawable.ic_filled_favorites);       // filled icon으로
        else                                        // 사용자가 좋아요 안누름
            holder.likeButton.setImageResource(R.drawable.ic_unfilled_favorites);     // unfilled icon으로

        // 제목
        holder.title.setText(data.title.get(position));

        // 활동 횟수
        holder.numOfActivities.setText("활동 횟수 " + data.activityNum.get(position));

        holder.readMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FreeModeContentsDetailsActivity.class);
                intent.putExtra("page_number", pageNum);
                intent.putExtra("recycler_position", holder.getPosition());
                mContext.startActivity(intent);
            }
        });

        // 모드에 따라 활성화 하기, 컨텐츠 담기 설정
        if(Fragment_Home.isChallenge){
            holder.readMoreButton.setText("활성화 하기");
        }else{
            holder.readMoreButton.setText("컨텐츠 담기");
        }
    }

    @Override
    public int getItemCount() {
        return data.title.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView background;       // 배경 사진
        ImageButton likeButton;     // 좋아요 아이콘
        TextView title;             // 제목
        TextView numOfActivities;   // 활동 횟수
        Button readMoreButton;      // 자세히 보기 버튼

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            background = (ImageView) itemView.findViewById(R.id.item_free_mode_contents_image);
            likeButton = (ImageButton) itemView.findViewById(R.id.item_free_mode_like_button);
            title = (TextView) itemView.findViewById(R.id.contents_title_text_view);
            numOfActivities = (TextView) itemView.findViewById(R.id.num_of_activities_text);
            readMoreButton = (Button) itemView.findViewById(R.id.item_free_mode_read_more_button);

        }
    }

}
