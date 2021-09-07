package com.barofutures.seco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.bumptech.glide.Glide;

public class HomeBannerAdapter extends RecyclerView.Adapter<HomeBannerAdapter.ViewHolder> {
    private String[] bannerImagesUrlList;

    public HomeBannerAdapter() {
    }

    public HomeBannerAdapter(String[] bannerImagesUrlList) {
        this.bannerImagesUrlList = bannerImagesUrlList;
    }

    @NonNull
    @Override
    public HomeBannerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_banner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context=holder.itemView.getContext();
        Glide.with(context)
                .load(bannerImagesUrlList[position])
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bannerImagesUrlList.length;
    }

    public void clearItems() {
        this.bannerImagesUrlList = null;
    }

    public void setItems(String[] bannerImages) {
        this.bannerImagesUrlList = bannerImages;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.home_banner);
        }
    }
}