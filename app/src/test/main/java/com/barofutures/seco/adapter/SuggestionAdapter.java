package com.barofutures.seco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.MainActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.model.SuggestItemClass;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.crypto.spec.PSource;

public class SuggestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private ArrayList<SuggestItemClass> suggestList = new ArrayList<>();

    // 생성자에서 데이터 리스트 객체를 전달받음
    public SuggestionAdapter() {
        SuggestItemClass itemClass = new SuggestItemClass();
    }

    public SuggestionAdapter(ArrayList<SuggestItemClass> items) {
        SuggestItemClass itemClass = new SuggestItemClass();
        itemClass.setItems(items);
    }

    // interface
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggestion, parent, false);
            return new ViewHolder(view);
        }else{
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_progress, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            SuggestItemClass item = suggestList.get(position);
            Glide.with(holder.itemView.getContext())
                    .load(item.getImageUrl())
                    .into(((ViewHolder) holder).suggestItemImage);
            ((ViewHolder) holder).suggestItemName.setText(item.getName());
            ((ViewHolder) holder).suggestItemCalories.setText(item.getCalories());
        }else if(holder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder)holder, position);
        }
    }

    @Override
    public int getItemViewType(int position){
        return suggestList.get(position)==null?VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    private void showLoadingView(LoadingViewHolder holder, int position){

    }

    private void populateItemRows(ViewHolder holder, int position){

    }

    // return the SuggestItemClass instance
    public SuggestItemClass getSuggetItemInstance(int position) {
        return suggestList.get(position);
    }

    // 전체 아이템 개수 리턴
    @Override
    public int getItemCount() {
        return suggestList==null?0:suggestList.size();
    }

    // 아이템을 저장할 ViewHolder 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView suggestItemImage;
        public TextView suggestItemName, suggestItemCalories;

        // ViewHolder 정의
        ViewHolder(View itemView) {
            super(itemView);

            suggestItemImage = (ImageView) itemView.findViewById(R.id.suggestion_cardview_image);
            suggestItemName = (TextView) itemView.findViewById(R.id.suggestion_cardview_text_name);
            suggestItemCalories = (TextView) itemView.findViewById(R.id.suggestion_cardview_text_calories);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getBindingAdapterPosition(); // 웹은 getAdapterPosition()으로 함
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }

                }
            });
        }
    }

    //  로딩 애니메이션을 위한 클래스
    private class LoadingViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView){
            super(itemView);
            progressBar=itemView.findViewById(R.id.progressbar);
        }

    }

    public void setItems(ArrayList<SuggestItemClass> items) {
        this.suggestList = items;
    }

    public void clearItems() {
        this.suggestList.clear();
    }
}