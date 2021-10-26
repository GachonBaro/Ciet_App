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
package com.barofutures.seco.adapter.recipeTab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.RecipeDetailActivity;
import com.barofutures.seco.firebase.RecipeGeneralData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecipeGeneralAdapter extends RecyclerView.Adapter<RecipeGeneralAdapter.ViewHolder>{
    Context mContext;
    ArrayList<String> itemTitle;        // 레시피 이름
    ArrayList<RecipeGeneralData> itemList;      // 레시피 데이터
    String category;                            // 카테고리 (한식, 일식, 중식, 디저트, ...)

    public RecipeGeneralAdapter(ArrayList<String> itemTitle, ArrayList<RecipeGeneralData> itemList, String category) {
        this.itemTitle = itemTitle;
        this.itemList = itemList;
        this.category = category;
        Log.d("RecipeGeneralAdapter", "itemTitle = " + itemTitle.get(itemTitle.size() - 1));
    }

    @NonNull
    @Override
    public RecipeGeneralAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_recipe_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecipeGeneralAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // cardview click event
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레시피 상세 페이지 activity
                Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("category", "일반식");
                intent.putExtra("category_general", category);
                mContext.startActivity(intent);

            }
        });

        // 음식 사진
        Glide.with(mContext)
                .load(Uri.parse(itemList.get(position).ATT_FILE_NO_MAIN))
                .centerCrop()
                .into(holder.image);

        // 레시피 이름
        holder.title.setText(itemTitle.get(position));

        // 칼로리
        holder.calories.setText(String.valueOf(itemList.get(position).INFO_ENG) + " Kcal");

        // color (light, normal, heavy)
        if (itemList.get(position).COLOR.equalsIgnoreCase("light")) {
            holder.color.setTextColor(ContextCompat.getColor(holder.color.getContext(), R.color.recipe_color_text_green));
        }
        else if (itemList.get(position).COLOR.equalsIgnoreCase("normal")) {
            holder.color.setTextColor(ContextCompat.getColor(holder.color.getContext(), R.color.recipe_color_text_yellow));
        }
        else if (itemList.get(position).COLOR.equalsIgnoreCase("heavy")) {
            holder.color.setTextColor(ContextCompat.getColor(holder.color.getContext(), R.color.recipe_color_text_red));
        }
        else {
            holder.color.setTextColor(R.color.black);
        }
        holder.color.setText(itemList.get(position).COLOR);
    }


    @Override
    public int getItemCount() {
        return itemTitle.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;      // cardView
        ImageView image;        // 음식 사진
        TextView title;         // 레시피 이름
        TextView calories;      // 칼로리
        TextView color;         // color (light, normal, heavy)

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_recipe_list_card_view);

            image = itemView.findViewById(R.id.item_recipe_list_image);
            title = itemView.findViewById(R.id.item_recipe_list_title_text);
            calories = itemView.findViewById(R.id.item_recipe_list_calories_text);
            color = itemView.findViewById(R.id.item_recipe_list_color_text);
        }
    }

}
