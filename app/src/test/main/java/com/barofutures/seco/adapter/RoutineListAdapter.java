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
