package com.barofutures.seco.progress;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.barofutures.seco.R;

public class ProgressButton {

    private ConstraintLayout layout;
    private ProgressBar progressBar;
    private TextView textView;

    Animation fade_in;

    public ProgressButton(Context mContext, View view) {
        this.layout = view.findViewById(R.id.progress_button);
        this.progressBar = view.findViewById(R.id.progress_bar);
        this.textView = view.findViewById(R.id.progress_button_text);
    }

    public void buttonActivated(boolean isSucceed) {
        layout.setBackgroundResource(R.drawable.button_rounded_gray);
        progressBar.setVisibility(View.VISIBLE);
        if (isSucceed) {
            textView.setText("배지 지급 중...");
        } else {
            textView.setText("데이터 업로드 중...");
        }

    }

    public void buttonFinished() {
        layout.setBackgroundResource(R.drawable.button_rounded_green);
        progressBar.setVisibility(View.GONE);
        textView.setText("완료하기");
    }
}
