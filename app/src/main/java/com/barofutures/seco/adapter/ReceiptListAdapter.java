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
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.model.ReceiptData;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.ViewHolder>{
    // Constructor
    public ReceiptListAdapter(){}

    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView date, name, detail;
        CircleImageView circleImageView;

        public ViewHolder(View itemView){
            super(itemView);
            date=itemView.findViewById(R.id.item_myorder_history_date);
            name=itemView.findViewById(R.id.item_myorder_history_name);
            detail=itemView.findViewById(R.id.item_myorder_history_detail);
            circleImageView=itemView.findViewById(R.id.item_myorder_history_image);
        }
    }

    @Override
    public ReceiptListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myorder_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.circleImageView.getContext())
                .load(ReceiptData.receiptDataList[position][0])
                .into(holder.circleImageView);
        holder.date.setText(ReceiptData.receiptDataList[position][1]);
        holder.name.setText(ReceiptData.receiptDataList[position][2]);
        holder.detail.setText(ReceiptData.receiptDataList[position][3]);

        // 이름은 클릭하면 커머스로 이동
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "커머스로 이동", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount(){return ReceiptData.receiptDataList.length;}
}
