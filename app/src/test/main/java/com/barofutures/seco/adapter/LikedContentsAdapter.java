package com.barofutures.seco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.model.LikedContentsListData;

public class LikedContentsAdapter extends RecyclerView.Adapter<LikedContentsAdapter.ViewHolder>{
    // Constructor
    public LikedContentsAdapter(){}

    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        Button delete;

        public ViewHolder(View itemView){
            super(itemView);

            name=itemView.findViewById(R.id.item_liked_contents_name);
            delete=itemView.findViewById(R.id.item_liked_contents_button_delete);
        }
    }

    @Override
    public LikedContentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liked_contents, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.name.setText(LikedContentsListData.likedContentsData[position]);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 삭제되고 리사이클러뷰 업데이트
            }
        });
    }

    @Override
    public int getItemCount(){ return LikedContentsListData.likedContentsData.length; }

}
