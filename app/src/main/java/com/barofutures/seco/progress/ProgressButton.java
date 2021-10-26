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
