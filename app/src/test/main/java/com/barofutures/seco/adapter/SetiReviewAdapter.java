package com.barofutures.seco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.model.RewardHistory;
import com.barofutures.seco.model.SetiQnA;

public class SetiReviewAdapter extends RecyclerView.Adapter<SetiReviewAdapter.ViewHolder>{
    // Constructor
    public SetiReviewAdapter(){};

    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView emoji;
        TextView question, answer;

        public ViewHolder(View itemView){
            super(itemView);

            emoji=itemView.findViewById(R.id.item_seti_review_emoji);
            question=itemView.findViewById(R.id.item_seti_review_question);
            answer=itemView.findViewById(R.id.item_seti_review_my_answer);
        }
    }

    @Override
    public SetiReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seti_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        if(SetiQnA.answer[position]<1){
            holder.emoji.setImageResource(R.drawable.image_emoji_sad);
        }else if(SetiQnA.answer[position]>=1&&SetiQnA.answer[position]<=2){
            holder.emoji.setImageResource(R.drawable.image_emoji_normal);
        }else{
            holder.emoji.setImageResource(R.drawable.image_emoji_smile);
        }
        holder.question.setText((position+1)+". "+SetiQnA.question[position]);
        holder.answer.setText(SetiQnA.choice[position][SetiQnA.answer[position]]+"로 답하셨습니다.  ");
    }

    @Override
    public int getItemCount(){ return SetiQnA.question.length; }
}
