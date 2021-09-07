package com.barofutures.seco;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.barofutures.seco.model.InitQnA;
import com.barofutures.seco.model.SetiQnA;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // 초기 설문, SETI 설문 했는지에 따라 시작 화면 결정
        if(!InitQnA.isAsked){
            Intent intent = new Intent(getApplicationContext(), InitialSurveyIntroActivity.class);
            startActivity(intent);
            this.finish();
        }else if (SetiQnA.mySETI.length()==0&&!SetiQnA.testLater){
            Intent intent = new Intent(getApplicationContext(), SetiSurveyActivity.class);
            startActivity(intent);
            this.finish();
        }else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    public void onBackPressed(){
    }

}







