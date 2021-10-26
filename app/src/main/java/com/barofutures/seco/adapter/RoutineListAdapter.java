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

import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.model.RoutineItem;

public class RoutineListAdapter extends RecyclerView.Adapter<RoutineListAdapter.ViewHolder> {
    // Constructor
    public RoutineListAdapter(){}

    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, daysLeft, day;

        public ViewHolder(View itemView){
            super(itemView);

            name=itemView.findViewById(R.id.routine_list_text_name);
            daysLeft=itemView.findViewById(R.id.routine_list_text_days_left);
            day=itemView.findViewById(R.id.routine_list_text_day);
        }

    }

    @Override
    public RoutineListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_routine_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(RoutineItem.RoutineItemList[position][0]);
        holder.daysLeft.setText("남은일수 "+RoutineItem.RoutineItemList[position][1]+"일");
        holder.day.setText(RoutineItem.RoutineItemList[position][2]);
    }

    @Override
    public int getItemCount(){return RoutineItem.RoutineItemList.length;}

}
