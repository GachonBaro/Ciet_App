package com.barofutures.seco.progress;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

import com.barofutures.seco.R;

public class ProgressDialog extends Dialog {

    public ProgressDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);        // 다이얼 로그 제목을 안보이게 함
        setContentView(R.layout.dialog_progress);
    }
}
