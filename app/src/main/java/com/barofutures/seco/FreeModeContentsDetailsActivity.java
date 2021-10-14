package com.barofutures.seco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class FreeModeContentsDetailsActivity extends AppCompatActivity {
    private int pageNum;                // viewPager에서 몇 번째 page인지 (0 - meal, 1 - activity, 2 - quest, 3 - favorites)
    private int recyclerPosition;       // recycler view item 중에 몇 번째 item인지

    private ScrollView scrollView;
    private ConstraintLayout mealConstraintLayout;
    private ConstraintLayout activityConstraintLayout;
    private ImageView background;
    private ImageButton backButton;
//    private ImageButton commerceButton;
    private TextView title;
    private TextView summary;
    private TextView contents;
    private TextView badgeCriteria;
    private TextView carbonReduction;
    // eco-activity만 해당
    private TextView caloriesConsumption;

    private Button enableButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_mode_contents_details);

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // intent의 값 전달 받기
        Intent intent = getIntent();
        pageNum = intent.getExtras().getInt("page_number");
        recyclerPosition = intent.getExtras().getInt("recycler_position");

        // View 초기화 및 연결
        scrollView = findViewById(R.id.free_mode_contents_linear_layout);
        enableButton = findViewById(R.id.contents_details_enable_button);
        backButton = findViewById(R.id.contents_details_back_button);
//        commerceButton = findViewById(R.id.contents_details_commerce_button);

        // 데이터 띄우기
        background = findViewById(R.id.free_mode_meal_contents_image);
        background.setImageResource(intent.getExtras().getInt("background"));
        title = findViewById(R.id.contents_details_title_text_view);
        title.setText(intent.getExtras().getString("title"));
        summary = findViewById(R.id.contents_details_description1_summary);
        summary.setText(intent.getExtras().getString("summary"));
        contents = findViewById(R.id.contents_details_description1_contents);
        contents.setText(intent.getExtras().getString("contents"));

        badgeCriteria = findViewById(R.id.contents_details_badge_criteria_text_view);
        badgeCriteria.setText(intent.getExtras().getString("badgeCriteria"));
        carbonReduction = findViewById(R.id.contents_details_carbon_reduction_text_view);
        carbonReduction.setText(intent.getExtras().getString("carbonReduction"));

            if (pageNum == 0) {     // eco-meal page인 경우
//                background = findViewById(R.id.free_mode_meal_contents_image);
//                background.setImageResource(ContentsListActivity.contentsDetailDataMeal.image.get(recyclerPosition));
//                title = findViewById(R.id.contents_details_title_text_view);
//                title.setText(ContentsListActivity.contentsDetailDataMeal.title.get(recyclerPosition));
//                summary = findViewById(R.id.contents_details_description1_summary);
//                summary.setText(ContentsListActivity.contentsDetailDataMeal.contents1.get(recyclerPosition));
//                contents = findViewById(R.id.contents_details_description1_contents);
//                contents.setText(ContentsListActivity.contentsDetailDataMeal.contents2.get(recyclerPosition));
//                badgeCriteria = findViewById(R.id.contents_details_badge_criteria_text_view);
//                badgeCriteria.setText(ContentsListActivity.contentsDetailDataMeal.activityNum.get(recyclerPosition));
//                carbonReduction = findViewById(R.id.contents_details_carbon_reduction_text_view);
//                carbonReduction.setText(ContentsListActivity.contentsDetailDataMeal.carbonReduction.get(recyclerPosition));

                mealConstraintLayout = findViewById(R.id.amount_of_carbon_reduction);
                activityConstraintLayout = findViewById(R.id.amount_of_carbon_reduction_calories_consumption);
                mealConstraintLayout.setVisibility(View.VISIBLE);
                activityConstraintLayout.setVisibility(View.INVISIBLE);
            }
            else if (pageNum == 1) {    // eco-activity page인 경우
//                background = findViewById(R.id.free_mode_meal_contents_image);
//                background.setImageResource(ContentsListActivity.contentsDetailDataActivity.image.get(recyclerPosition));
//                title = findViewById(R.id.contents_details_title_text_view);
//                title.setText(ContentsListActivity.contentsDetailDataActivity.title.get(recyclerPosition));
//                summary = findViewById(R.id.contents_details_description1_summary);
//                summary.setText(ContentsListActivity.contentsDetailDataActivity.contents1.get(recyclerPosition));
//                contents = findViewById(R.id.contents_details_description1_contents);
//                contents.setText(ContentsListActivity.contentsDetailDataActivity.contents2.get(recyclerPosition));
//                badgeCriteria = findViewById(R.id.contents_details_badge_criteria_text_view);
//                badgeCriteria.setText(ContentsListActivity.contentsDetailDataActivity.activityNum.get(recyclerPosition));
//                carbonReduction = findViewById(R.id.contents_details_carbon_reduction_text_view);
//                carbonReduction.setText(ContentsListActivity.contentsDetailDataActivity.carbonReduction.get(recyclerPosition));


                caloriesConsumption = findViewById(R.id.activity_contents_details_calories_consumption_text_view);
//                caloriesConsumption.setText(ContentsListActivity.contentsDetailDataActivity.calorieConsumption.get(recyclerPosition));
                caloriesConsumption.setText(intent.getExtras().getString("caloriesConsumption"));

                mealConstraintLayout = findViewById(R.id.amount_of_carbon_reduction);
                activityConstraintLayout = findViewById(R.id.amount_of_carbon_reduction_calories_consumption);
                mealConstraintLayout.setVisibility(View.INVISIBLE);
                activityConstraintLayout.setVisibility(View.VISIBLE);
            } else if(pageNum==3){
//                background = findViewById(R.id.free_mode_meal_contents_image);
//                background.setImageResource(ContentsListActivity.contentsDetailDataQuest.image.get(recyclerPosition));
//                title = findViewById(R.id.contents_details_title_text_view);
//                title.setText(ContentsListActivity.contentsDetailDataQuest.title.get(recyclerPosition));
//                summary = findViewById(R.id.contents_details_description1_summary);
//                summary.setText(ContentsListActivity.contentsDetailDataQuest.contents1.get(recyclerPosition));
//                contents = findViewById(R.id.contents_details_description1_contents);
//                contents.setText(ContentsListActivity.contentsDetailDataQuest.contents2.get(recyclerPosition));
//                badgeCriteria = findViewById(R.id.contents_details_badge_criteria_text_view);
//                badgeCriteria.setText(ContentsListActivity.contentsDetailDataQuest.activityNum.get(recyclerPosition));
//                carbonReduction = findViewById(R.id.contents_details_carbon_reduction_text_view);
//                carbonReduction.setText(ContentsListActivity.contentsDetailDataQuest.carbonReduction.get(recyclerPosition));

                mealConstraintLayout = findViewById(R.id.amount_of_carbon_reduction);
                activityConstraintLayout = findViewById(R.id.amount_of_carbon_reduction_calories_consumption);
                mealConstraintLayout.setVisibility(View.VISIBLE);
                activityConstraintLayout.setVisibility(View.INVISIBLE);
            } else{
//                background = findViewById(R.id.free_mode_meal_contents_image);
//                background.setImageResource(ContentsListActivity.contentsDetailDataFavorites.image.get(recyclerPosition));
//                title = findViewById(R.id.contents_details_title_text_view);
//                title.setText(ContentsListActivity.contentsDetailDataFavorites.title.get(recyclerPosition));
//                summary = findViewById(R.id.contents_details_description1_summary);
//                summary.setText(ContentsListActivity.contentsDetailDataFavorites.contents1.get(recyclerPosition));
//                contents = findViewById(R.id.contents_details_description1_contents);
//                contents.setText(ContentsListActivity.contentsDetailDataFavorites.contents2.get(recyclerPosition));
//                badgeCriteria = findViewById(R.id.contents_details_badge_criteria_text_view);
//                badgeCriteria.setText(ContentsListActivity.contentsDetailDataFavorites.activityNum.get(recyclerPosition));
//                carbonReduction = findViewById(R.id.contents_details_carbon_reduction_text_view);
//                carbonReduction.setText(ContentsListActivity.contentsDetailDataFavorites.carbonReduction.get(recyclerPosition));

                mealConstraintLayout = findViewById(R.id.amount_of_carbon_reduction);
                activityConstraintLayout = findViewById(R.id.amount_of_carbon_reduction_calories_consumption);
                mealConstraintLayout.setVisibility(View.VISIBLE);
                activityConstraintLayout.setVisibility(View.INVISIBLE);
            }


        // 상태바 높이 만큼 padding 주기
        scrollView.setPadding(0, getStatusBarHeight(), 0, 0);

        // 뒤로가기 버튼 누르면 activity 종료
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        // 커머스 버튼 누르면 커머스로 연결
//        commerceButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "커머스 페이지로 이동", Toast.LENGTH_SHORT).show();
//            }
//        });

        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 활동별로 인증 화면 다르게 뜨게 설정 (걷기/플로깅하기, 자전거타기(이것도 걷기란 같이 가능..?), 계단 이용하기)

                // 걷기, 자전거 타기, 플로깅하기 인 경우
                if (pageNum == 1 && !intent.getExtras().getString("title").equalsIgnoreCase("계단 이용하기")){
                    Intent authIntent = new Intent(getApplicationContext(), WalkingAuthActivity.class);
                    authIntent.putExtra("title", intent.getExtras().getString("title"));
                    authIntent.putExtra("badgeCriteria", intent.getExtras().getString("badgeCriteria"));
                    startActivity(authIntent);
                }
                // 계단 이용하기 인 경우
                else if (intent.getExtras().getString("title").equalsIgnoreCase("계단 이용하기")) {

                }


            }
        });


    }

    //status bar의 높이 계산
    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }


}