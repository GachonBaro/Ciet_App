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
    private ImageButton commerceButton;
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
        commerceButton = findViewById(R.id.contents_details_commerce_button);

        // 데이터 띄우기

            if (pageNum == 0) {     // eco-meal page인 경우
                background = findViewById(R.id.free_mode_meal_contents_image);
                background.setImageResource(FreeModeActivity.freeModeContentsListDataMeal.image.get(recyclerPosition));
                title = findViewById(R.id.contents_details_title_text_view);
                title.setText(FreeModeActivity.freeModeContentsListDataMeal.title.get(recyclerPosition));
                summary = findViewById(R.id.contents_details_description1_summary);
                summary.setText(FreeModeActivity.freeModeContentsListDataMeal.contents1.get(recyclerPosition));
                contents = findViewById(R.id.contents_details_description1_contents);
                contents.setText(FreeModeActivity.freeModeContentsListDataMeal.contents2.get(recyclerPosition));
                badgeCriteria = findViewById(R.id.contents_details_badge_criteria_text_view);
                badgeCriteria.setText(FreeModeActivity.freeModeContentsListDataMeal.activityNum.get(recyclerPosition));
                carbonReduction = findViewById(R.id.contents_details_carbon_reduction_text_view);
                carbonReduction.setText(FreeModeActivity.freeModeContentsListDataMeal.carbonReduction.get(recyclerPosition));

                mealConstraintLayout = findViewById(R.id.amount_of_carbon_reduction);
                activityConstraintLayout = findViewById(R.id.amount_of_carbon_reduction_calories_consumption);
                mealConstraintLayout.setVisibility(View.VISIBLE);
                activityConstraintLayout.setVisibility(View.INVISIBLE);
            }
            else if (pageNum == 1) {    // eco-activity page인 경우
                background = findViewById(R.id.free_mode_meal_contents_image);
                background.setImageResource(FreeModeActivity.freeModeContentsListDataActivity.image.get(recyclerPosition));
                title = findViewById(R.id.contents_details_title_text_view);
                title.setText(FreeModeActivity.freeModeContentsListDataActivity.title.get(recyclerPosition));
                summary = findViewById(R.id.contents_details_description1_summary);
                summary.setText(FreeModeActivity.freeModeContentsListDataActivity.contents1.get(recyclerPosition));
                contents = findViewById(R.id.contents_details_description1_contents);
                contents.setText(FreeModeActivity.freeModeContentsListDataActivity.contents2.get(recyclerPosition));
                badgeCriteria = findViewById(R.id.contents_details_badge_criteria_text_view);
                badgeCriteria.setText(FreeModeActivity.freeModeContentsListDataActivity.activityNum.get(recyclerPosition));
                carbonReduction = findViewById(R.id.contents_details_carbon_reduction_text_view);
                carbonReduction.setText(FreeModeActivity.freeModeContentsListDataActivity.carbonReduction.get(recyclerPosition));


                caloriesConsumption = findViewById(R.id.activity_contents_details_calories_consumption_text_view);
                caloriesConsumption.setText(FreeModeActivity.freeModeContentsListDataActivity.calorieConsumption.get(recyclerPosition));

                mealConstraintLayout = findViewById(R.id.amount_of_carbon_reduction);
                activityConstraintLayout = findViewById(R.id.amount_of_carbon_reduction_calories_consumption);
                mealConstraintLayout.setVisibility(View.INVISIBLE);
                activityConstraintLayout.setVisibility(View.VISIBLE);
            } else if(pageNum==3){
                background = findViewById(R.id.free_mode_meal_contents_image);
                background.setImageResource(FreeModeActivity.freeModeContentsListDataQuest.image.get(recyclerPosition));
                title = findViewById(R.id.contents_details_title_text_view);
                title.setText(FreeModeActivity.freeModeContentsListDataQuest.title.get(recyclerPosition));
                summary = findViewById(R.id.contents_details_description1_summary);
                summary.setText(FreeModeActivity.freeModeContentsListDataQuest.contents1.get(recyclerPosition));
                contents = findViewById(R.id.contents_details_description1_contents);
                contents.setText(FreeModeActivity.freeModeContentsListDataQuest.contents2.get(recyclerPosition));
                badgeCriteria = findViewById(R.id.contents_details_badge_criteria_text_view);
                badgeCriteria.setText(FreeModeActivity.freeModeContentsListDataQuest.activityNum.get(recyclerPosition));
                carbonReduction = findViewById(R.id.contents_details_carbon_reduction_text_view);
                carbonReduction.setText(FreeModeActivity.freeModeContentsListDataQuest.carbonReduction.get(recyclerPosition));

                // 퀘스트는 컨텐츠 담기가 아닌 활성화 하기
                enableButton.setText("활성화 하기");

                mealConstraintLayout = findViewById(R.id.amount_of_carbon_reduction);
                activityConstraintLayout = findViewById(R.id.amount_of_carbon_reduction_calories_consumption);
                mealConstraintLayout.setVisibility(View.VISIBLE);
                activityConstraintLayout.setVisibility(View.INVISIBLE);
            } else{
                background = findViewById(R.id.free_mode_meal_contents_image);
                background.setImageResource(FreeModeActivity.freeModeContentsListDataFavorites.image.get(recyclerPosition));
                title = findViewById(R.id.contents_details_title_text_view);
                title.setText(FreeModeActivity.freeModeContentsListDataFavorites.title.get(recyclerPosition));
                summary = findViewById(R.id.contents_details_description1_summary);
                summary.setText(FreeModeActivity.freeModeContentsListDataFavorites.contents1.get(recyclerPosition));
                contents = findViewById(R.id.contents_details_description1_contents);
                contents.setText(FreeModeActivity.freeModeContentsListDataFavorites.contents2.get(recyclerPosition));
                badgeCriteria = findViewById(R.id.contents_details_badge_criteria_text_view);
                badgeCriteria.setText(FreeModeActivity.freeModeContentsListDataFavorites.activityNum.get(recyclerPosition));
                carbonReduction = findViewById(R.id.contents_details_carbon_reduction_text_view);
                carbonReduction.setText(FreeModeActivity.freeModeContentsListDataFavorites.carbonReduction.get(recyclerPosition));

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

        // 커머스 버튼 누르면 커머스로 연결
        commerceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "커머스 페이지로 이동", Toast.LENGTH_SHORT).show();
            }
        });

        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "활성화 하기 버튼 눌림", Toast.LENGTH_SHORT).show();
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