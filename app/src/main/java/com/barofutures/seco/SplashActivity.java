package com.barofutures.seco;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

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


        ///////////////////////////////////////////////////////////////////////////////////////////
        Intent splashIntent = getIntent();
        String nextActivity = splashIntent.getStringExtra("nextActivity");        // 다음으로 넘어갈 Activity
        String email = splashIntent.getStringExtra("email");        // email
        switch (nextActivity) {
            case "InitialSurveyIntroActivity":      // InitialSurveyIntroActivity.java로 전환
                // TEST
                Toast.makeText(getApplicationContext(), "case InitialSurveyIntroActivity", Toast.LENGTH_SHORT).show();

                Intent initSurveyIntent = new Intent(getApplicationContext(), InitialSurveyIntroActivity.class);
                initSurveyIntent.putExtra("email", email);
                startActivity(initSurveyIntent);
                this.finish();
                break;
            case "MainActivity":        // MainActivity.java로 전환
                // TODO: 전환하기 전에 firestore에서 user_info 데이터 받아오기

                // TODO: 데이터 받아오는 동안 애니메이션 재생
                // TEST
                Toast.makeText(getApplicationContext(), "case MainActivity", Toast.LENGTH_SHORT).show();


                Intent MainIntent = new Intent(getApplicationContext(), MainActivity.class);
                MainIntent.putExtra("email", email);
                startActivity(MainIntent);
                this.finish();
                break;
            default:
                Toast.makeText(getApplicationContext(), "ERROR: 앱 종류 후 다시 실행시켜주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                break;
        }


        ///////////////////////////////////////////////////////////////////////////////////////////

//        // 초기 설문, SETI 설문 했는지에 따라 시작 화면 결정
//        if (!InitQnA.isAsked) {
//            Intent intent = new Intent(getApplicationContext(), InitialSurveyIntroActivity.class);
//            startActivity(intent);
//            this.finish();
//        } else if (SetiQnA.mySETI.length() == 0 && !SetiQnA.testLater) {
//            Intent intent = new Intent(getApplicationContext(), SetiSurveyActivity.class);
//            startActivity(intent);
//            this.finish();
//        } else {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            this.finish();
//        }
    }

    @Override
    public void onBackPressed() {
    }

}







