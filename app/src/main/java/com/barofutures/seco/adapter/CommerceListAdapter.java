package com.barofutures.seco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.model.CommerceItem;
import com.bumptech.glide.Glide;

public class CommerceListAdapter extends RecyclerView.Adapter<CommerceListAdapter.ViewHolder> {
    // Constructor
    public CommerceListAdapter() {
    }

    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemBrand, itemName, itemPrice, itemDiscount, itemPoint;

        public ViewHolder(View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.commerce_item_image);
            itemBrand = itemView.findViewById(R.id.commerce_item_brand);
            itemName = itemView.findViewById(R.id.commerce_item_name);
            itemPrice = itemView.findViewById(R.id.commerce_item_price);
            itemDiscount = itemView.findViewById(R.id.commerce_item_discount);
            itemPoint = itemView.findViewById(R.id.commerce_item_point);
        }
    }

    @Override
    public CommerceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commerce, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CommerceListAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemImage.getContext())
                .load(CommerceItem.commerceItemList[position][0])
                .into(holder.itemImage);
        holder.itemBrand.setText(CommerceItem.commerceItemList[position][1]);
        holder.itemName.setText(CommerceItem.commerceItemList[position][2]);
        holder.itemPrice.setText(CommerceItem.commerceItemList[position][3]);
        holder.itemDiscount.setText(CommerceItem.commerceItemList[position][4]);
        holder.itemPoint.setText(CommerceItem.commerceItemList[position][5]);
    }

    @Override
    public int getItemCount() {
        return CommerceItem.commerceItemList.length;
    }
}
