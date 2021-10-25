package com.barofutures.seco.adapter.recipeTab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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
        Log.d("12341234", "itemTitle = " + itemTitle.get(itemTitle.size() - 1));
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
                Toast.makeText(mContext, itemTitle.get(position) + " 눌림", Toast.LENGTH_SHORT).show();

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
//        holder.calories.setText(String.valueOf(itemList.get(position).INFO_CAR).split(".")[0] + " Kcal");
        holder.calories.setText(String.valueOf(itemList.get(position).INFO_ENG) + " Kcal");


//        // TODO: 좋아요 데이터 반영 필요함
//        // 좋아요 버튼
//        if (((int)(Math.random() * 10)) % 2 == 0)         // 사용자가 좋아요 표시
//            holder.likeButton.setImageResource(R.drawable.ic_filled_favorites);       // filled icon으로
//        else                                        // 사용자가 좋아요 안누름
//            holder.likeButton.setImageResource(R.drawable.ic_unfilled_favorites);     // unfilled icon으로


        // TODO: 좋아요 버튼 클릭 이벤트 추가 (+ db 연동)

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
//        ImageButton likeButton; // 좋아요 버튼
        ImageView seafoodImage; // 해산물 아이콘

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_recipe_list_card_view);

            image = itemView.findViewById(R.id.item_recipe_list_image);
            title = itemView.findViewById(R.id.item_recipe_list_title_text);
            calories = itemView.findViewById(R.id.item_recipe_list_calories_text);
//            likeButton = itemView.findViewById(R.id.item_recipe_list_like_button);

            seafoodImage = itemView.findViewById(R.id.item_recipe_list_seafood_image_view);
        }
    }

}
