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
