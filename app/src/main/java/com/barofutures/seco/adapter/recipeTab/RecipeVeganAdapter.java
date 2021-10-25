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
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.RecipeDetailActivity;
import com.barofutures.seco.firebase.RecipeVeganData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecipeVeganAdapter extends RecyclerView.Adapter<RecipeVeganAdapter.ViewHolder>{
    Context mContext;
    ArrayList<String> itemTitle;        // 레시피 이름
    ArrayList<RecipeVeganData> itemList;      // 레시피 데이터

    public RecipeVeganAdapter(ArrayList<String> itemTitle, ArrayList<RecipeVeganData> itemList) {
        this.itemTitle = itemTitle;
        this.itemList = itemList;
        Log.d("RecipeVeganAdapter", "itemTitle = " + itemTitle.get(itemTitle.size() - 1));
    }

    @NonNull
    @Override
    public RecipeVeganAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_recipe_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecipeVeganAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // cardview click event
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레시피 상세 페이지 activity
                Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("category_general", "비건");
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

        // 해산물 아이콘
        if (itemList.get(position).SEA_FOOD.equalsIgnoreCase("O")) {
            holder.seafoodImage.setImageResource(R.drawable.ic_seafood);
        }
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
        ImageView seafoodImage; // 해산물 아이콘

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_recipe_list_card_view);

            image = itemView.findViewById(R.id.item_recipe_list_image);
            title = itemView.findViewById(R.id.item_recipe_list_title_text);
            calories = itemView.findViewById(R.id.item_recipe_list_calories_text);

            seafoodImage = itemView.findViewById(R.id.item_recipe_list_seafood_image_view);
        }
    }

}
