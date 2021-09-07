package com.barofutures.seco;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class InitialSurveyIntroActivity extends AppCompatActivity {
    private Button startInitSurvey;
    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    private Toast toast;
    private TextView title;
    private SpannableString spannableString;

    // Anim
    private VideoView videoView;
    private Uri uri;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialsurveyintro);
        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // View 초기화 및 연결
        startInitSurvey = findViewById(R.id.activity_initialsurvey_intro_button_start);
        title=findViewById(R.id.activity_initialsurvey_intro_title);
        videoView=findViewById(R.id.activity_initialsurvey_intro_anim);
        uri=Uri.parse("android.resource://com.barofutures.seco/"+getResources().getIdentifier("earth", "raw", getPackageName()));

        // 지구 영상 세팅
        videoView.setVideoURI(uri);
        videoView.start();
        // 반복 재생
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });

        // 문구 설정
        spannableString=new SpannableString("SECO와 함께 '탄소 다이어트' 를\n 할 준비가 되었나요?");
        spannableString.setSpan(new BackgroundColorSpan(ContextCompat.getColor(this, R.color.seco_green)), 9, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.white)), 9, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setText(spannableString);

        // 시작 버튼 이벤트 리스너
        startInitSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InitialSurveyActivity.class);
                startActivity(intent);
                finishAfterTransition();
            }
        });
    }

    /*
     * back button click event
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            // 전부 Kill
            finishAffinity();
            toast.cancel();
        }
    }
}