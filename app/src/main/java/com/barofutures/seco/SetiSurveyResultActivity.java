package com.barofutures.seco;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.barofutures.seco.model.SetiQnA;

public class SetiSurveyResultActivity extends AppCompatActivity {
    private SpannableString summaryString;
    private TextView summary, description, typo1, typo2, typo3;
    private Button detailButton, exitButton;
    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    private Toast toast;
    private String seti;

    private String email;

    public SetiSurveyResultActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setisurveyresult);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // View 초기화 및 연결
        summary = findViewById(R.id.activity_seti_result_text_summary);
        description = findViewById(R.id.activity_seti_result_text_description);
        detailButton = findViewById(R.id.activity_seti_result_button_detail);
        exitButton = findViewById(R.id.activity_seti_result_button_exit);
        typo1=findViewById(R.id.activity_seti_result_typo_1);
        typo2=findViewById(R.id.activity_seti_result_typo_2);
        typo3=findViewById(R.id.activity_seti_result_typo_3);

        //SETI 결과 출력
        SetiQnA.calculateSETI();
        seti=SetiQnA.getMySETI();
        summaryString=new SpannableString("당신의 환경 유형은 "+seti+" 입니다.");
        summaryString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.seco_green)), 11, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        summary.setText(summaryString);
        typo1.setText(String.valueOf(seti.charAt(0)));
        typo2.setText(String.valueOf(seti.charAt(1)));
        typo3.setText(String.valueOf(seti.charAt(2)));

        description.setText("친환경 활동에 참여할 의향과 환경에 대한 이해도는\n" +
                "크지만 실천도가 낮은 타입입니다.");

        // SETI 결과 저장
        SetiQnA.storeSETIResult(email);

//        // 더 자세히 알아보기 클릭 시 이벤트
//        detailButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), MySetiActivity.class);
//                intent.putExtra("email", email);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        // 나가기 버튼 클릭 시 홈으로 이동
//        exitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.putExtra("email", email);
//                // 플래그 지정: 같은 액티비티가 재사용되기 때문에 onCreate가 호출되지 않고 onNewIntent가 실행되는 것에 주의
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 더 자세히 알아보기 클릭 시 이벤트
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MySetiActivity.class);
                intent.putExtra("email", email);
                // 플래그 지정: 같은 액티비티가 재사용되기 때문에 onCreate가 호출되지 않고 onNewIntent가 실행되는 것에 주의
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        // 나가기 버튼 클릭 시 홈으로 이동
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("email", email);
                // 플래그 지정: 같은 액티비티가 재사용되기 때문에 onCreate가 호출되지 않고 onNewIntent가 실행되는 것에 주의
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    /*
     * back button click event
     */
//    @Override
//    public void onBackPressed() {
//        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
//            backKeyPressedTime = System.currentTimeMillis();
//            toast = Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
//            toast.show();
//            return;
//        }
//        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
//            // 전부 Kill
//            finishAffinity();
//            toast.cancel();
//        }
//    }
}