package com.barofutures.seco.temp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.model.InitQnA;

public class ViewHolderPage extends RecyclerView.ViewHolder {

    private TextView question;

    InitQnA data;

    public ViewHolderPage(View itemView) {
        super(itemView);
        question = itemView.findViewById(R.id.initsurvey_text_question);
    }

    public void onBind(InitQnA data){
        this.data = data;
//        question.setText(data.getContents());
    }
}