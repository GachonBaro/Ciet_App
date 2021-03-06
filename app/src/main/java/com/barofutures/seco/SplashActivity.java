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
package com.barofutures.seco;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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


        Intent splashIntent = getIntent();
        String nextActivity = splashIntent.getStringExtra("nextActivity");        // 다음으로 넘어갈 Activity
        String email = splashIntent.getStringExtra("email");        // email
        switch (nextActivity) {
            case "InitialSurveyIntroActivity":      // InitialSurveyIntroActivity.java로 전환
                // TEST
//                Toast.makeText(getApplicationContext(), "case InitialSurveyIntroActivity", Toast.LENGTH_SHORT).show();

                Intent initSurveyIntent = new Intent(getApplicationContext(), InitialSurveyIntroActivity.class);
                initSurveyIntent.putExtra("email", email);
                startActivity(initSurveyIntent);
                this.finish();
                break;
            case "MainActivity":        // MainActivity.java로 전환
                // TEST
//                Toast.makeText(getApplicationContext(), "case MainActivity", Toast.LENGTH_SHORT).show();

                Log.d("SplashActivity", "email = " + email);

                Intent MainIntent = new Intent(getApplicationContext(), MainActivity.class);
                MainIntent.putExtra("email", email);
                startActivity(MainIntent);
                this.finish();
                break;
            default:
                Toast.makeText(getApplicationContext(), "ERROR: 앱 종류 후 다시 실행시켜주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onBackPressed() {
    }

}







