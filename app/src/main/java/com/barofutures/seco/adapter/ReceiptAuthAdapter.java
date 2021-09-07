package com.barofutures.seco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.model.ReceiptData;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReceiptAuthAdapter extends RecyclerView.Adapter<ReceiptAuthAdapter.ViewHolder>{
    // Constructor
    public ReceiptAuthAdapter(){}

    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView date, name, detail;
        CircleImageView circleImageView;

        public ViewHolder(View itemView){
            super(itemView);
            date=itemView.findViewById(R.id.authactivity_receipt_item_date);
            name=itemView.findViewById(R.id.authactivity_receipt_item_name);
            detail=itemView.findViewById(R.id.authactivity_receipt_item_detail);
            circleImageView=itemView.findViewById(R.id.authactivity_receipt_item_image);
        }
    }

    @Override
    public ReceiptAuthAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.circleImageView.getContext())
                .load(ReceiptData.receiptDataList[position][0])
                .into(holder.circleImageView);
        holder.date.setText(ReceiptData.receiptDataList[position][1]);
        holder.name.setText(ReceiptData.receiptDataList[position][2]);
        holder.detail.setText(ReceiptData.receiptDataList[position][3]);

        // 이름은 클릭하면 커머스로 이동
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "커머스로 이동", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount(){return ReceiptData.receiptDataList.length;}
}
