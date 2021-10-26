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

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.model.ContentsDetailData;
import com.bumptech.glide.Glide;

public class MyCmiActivitySuggestAdapter extends RecyclerView.Adapter<MyCmiActivitySuggestAdapter.ViewHolder> {
    // FreeModeContentsListData 데이터 임시로 활용
    private ContentsDetailData data;
    private SpannableString spannableString;

    // Constructor
    public MyCmiActivitySuggestAdapter(){

    }

    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView type, name, carbon;

        public ViewHolder(View itemView){
            super(itemView);

            image=itemView.findViewById(R.id.item_mycmi_activity_suggest_image);
            type=itemView.findViewById(R.id.item_mycmi_activity_suggest_type);
            name=itemView.findViewById(R.id.item_mycmi_activity_suggest_name);
            carbon=itemView.findViewById(R.id.item_mycmi_activity_suggest_carbon_amount);
        }
    }

    @Override
    public MyCmiActivitySuggestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mycmi_activity_suggest, parent, false);

        data = new ContentsDetailData();
        data.setActivity();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Glide.with(holder.image.getContext())
                .load(data.image.get(position))
                .into(holder.image);
        holder.type.setText("운동");
        holder.name.setText(data.title.get(position));
        spannableString=new SpannableString("CO"+"\u2082 저감량 "+data.carbonReduction.get(position));
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#D81010")), 8,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.carbon.setText(spannableString);
    }

    @Override
    public int getItemCount(){return 4;}
}
