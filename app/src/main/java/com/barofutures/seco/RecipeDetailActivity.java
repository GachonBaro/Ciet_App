package com.barofutures.seco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.services.cognitoidentityprovider.model.TooManyFailedAttemptsException;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralAsianFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralDessertFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralBunsikFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralChineseFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralFusionFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralJapaneseFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralKoreanFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralSaladFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralWesternFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeVeganFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private int recyclerItemPosition;           // recyclerview에서 몇 번째 item인지 위치식
//    private String category;                    // 레시피 카테고리 (일반식, 비건)
    private String categoryGeneral;             // 레시피 카테고리 (한식, 일식, 중식, 디저트, ...)

    private ImageView image;                    // 음식 사진
    private TextView caloriesText;              // 칼로리
    private TextView proteinText;               // 단백질
    private TextView fatText;                   // 지방
    private TextView sodiumText;                // 소금
    private TextView foodNameText;              // 레시피 이름

//    private TextView[] manualText = new TextView[18];     // 메뉴얼 1 ~ 18

    private TextView manualText1;               // 메뉴얼 1
    private TextView manualText2;               // 메뉴얼 2
    private TextView manualText3;               // 메뉴얼 3
    private TextView manualText4;               // 메뉴얼 4
    private TextView manualText5;               // 메뉴얼 5
    private TextView manualText6;               // 메뉴얼 6
    private TextView manualText7;               // 메뉴얼 7
    private TextView manualText8;               // 메뉴얼 8
    private TextView manualText9;               // 메뉴얼 9
    private TextView manualText10;               // 메뉴얼 10
    private TextView manualText11;               // 메뉴얼 11
    private TextView manualText12;               // 메뉴얼 12
    private TextView manualText13;               // 메뉴얼 13
    private TextView manualText14;               // 메뉴얼 14
    private TextView manualText15;               // 메뉴얼 15
    private TextView manualText16;               // 메뉴얼 16
    private TextView manualText17;               // 메뉴얼 17
    private TextView manualText18;               // 메뉴얼 18
    private TextView ingredientText;             // 재료 목록           //TODO: 재료 목록 관련 코드 추가

    private ConstraintLayout manualLayout;       // 레시피 메뉴얼 constraint layout
    private Button readMoreButton;               // 레시피 더보기 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        recyclerItemPosition = intent.getExtras().getInt("position");
//        category = intent.getExtras().getString("category");
        categoryGeneral = intent.getExtras().getString("category_general");

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar 설정
        toolbar=findViewById(R.id.activity_recipe_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Status Bar 높이만큼 Padding 부여
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        // get id
        image = findViewById(R.id.activity_recipe_detail_image);
        caloriesText = findViewById(R.id.recipe_calories_text_view);
        proteinText = findViewById(R.id.recipe_protein_text_view);
        fatText = findViewById(R.id.recipe_fat_text_view);
        sodiumText = findViewById(R.id.recipe_sodium_text_view);
        foodNameText = findViewById(R.id.activity_recipe_detail_food_name);

//        manualText[0] = findViewById(R.id.activity_recipe_detail_manual_1_contents);
//        manualText[1] = findViewById(R.id.activity_recipe_detail_manual_2_contents);
//        manualText[2] = findViewById(R.id.activity_recipe_detail_manual_3_contents);
//        manualText[3] = findViewById(R.id.activity_recipe_detail_manual_4_contents);
//        manualText[4] = findViewById(R.id.activity_recipe_detail_manual_5_contents);
//        manualText[5] = findViewById(R.id.activity_recipe_detail_manual_6_contents);
//        manualText[6] = findViewById(R.id.activity_recipe_detail_manual_7_contents);
//        manualText[7] = findViewById(R.id.activity_recipe_detail_manual_8_contents);
//        manualText[8] = findViewById(R.id.activity_recipe_detail_manual_9_contents);
//        manualText[9] = findViewById(R.id.activity_recipe_detail_manual_10_contents);
//        manualText[10] = findViewById(R.id.activity_recipe_detail_manual_11_contents);
//        manualText[11] = findViewById(R.id.activity_recipe_detail_manual_12_contents);
//        manualText[12] = findViewById(R.id.activity_recipe_detail_manual_13_contents);
//        manualText[13] = findViewById(R.id.activity_recipe_detail_manual_14_contents);
//        manualText[14] = findViewById(R.id.activity_recipe_detail_manual_15_contents);
//        manualText[15] = findViewById(R.id.activity_recipe_detail_manual_16_contents);
//        manualText[16] = findViewById(R.id.activity_recipe_detail_manual_17_contents);
//        manualText[17] = findViewById(R.id.activity_recipe_detail_manual_18_contents);

        manualText1 = findViewById(R.id.activity_recipe_detail_manual_1_contents);
        manualText2 = findViewById(R.id.activity_recipe_detail_manual_2_contents);
        manualText3 = findViewById(R.id.activity_recipe_detail_manual_3_contents);
        manualText4 = findViewById(R.id.activity_recipe_detail_manual_4_contents);
        manualText5 = findViewById(R.id.activity_recipe_detail_manual_5_contents);
        manualText6 = findViewById(R.id.activity_recipe_detail_manual_6_contents);
        manualText7 = findViewById(R.id.activity_recipe_detail_manual_7_contents);
        manualText8 = findViewById(R.id.activity_recipe_detail_manual_8_contents);
        manualText9 = findViewById(R.id.activity_recipe_detail_manual_9_contents);
        manualText10 = findViewById(R.id.activity_recipe_detail_manual_10_contents);
        manualText11 = findViewById(R.id.activity_recipe_detail_manual_11_contents);
        manualText12 = findViewById(R.id.activity_recipe_detail_manual_12_contents);
        manualText13 = findViewById(R.id.activity_recipe_detail_manual_13_contents);
        manualText14 = findViewById(R.id.activity_recipe_detail_manual_14_contents);
        manualText15 = findViewById(R.id.activity_recipe_detail_manual_15_contents);
        manualText16 = findViewById(R.id.activity_recipe_detail_manual_16_contents);
        manualText17 = findViewById(R.id.activity_recipe_detail_manual_17_contents);
        manualText18 = findViewById(R.id.activity_recipe_detail_manual_18_contents);

        ingredientText = findViewById(R.id.activity_recipe_ingredient_contents);

        // 레시피 메뉴얼 constraint layout
        manualLayout = findViewById(R.id.activity_recipe_detail_manual_constraint_layout);

        // 레시피 더보기 버튼
        readMoreButton = findViewById(R.id.activity_recipe_detail_manual_read_more_button);

        /* 일반식의 경우 */
        if (categoryGeneral.equalsIgnoreCase("한식")) {
            // 음식 사진
            Glide.with(getApplicationContext())
                    .load(Uri.parse(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).ATT_FILE_NO_MAIN))
                    .centerCrop()
                    .into(image);

            // 칼로리
            caloriesText.setText(String.valueOf(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).INFO_ENG) + "Kcal");

            // 단백질
            proteinText.setText(String.valueOf(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).INFO_PRO) + "g");

            // 지방
            fatText.setText(String.valueOf(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).INFO_FAT) + "g");

            // 나트륨
            sodiumText.setText(String.valueOf(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).INFO_NA) + "g");

            // 음식 이름
            foodNameText.setText(RecipeGeneralKoreanFragment.itemTitle.get(recyclerItemPosition));

            // 재료
            ingredientText.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).RCP_PARTS_DTLS);

            // 메뉴얼 1 ~ 18
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL01.equalsIgnoreCase("nan")) {
                manualText1.setHeight(0);
                manualText1.setVisibility(View.GONE);
            } else {
                manualText1.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL01);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL02.equalsIgnoreCase("nan")) {
                manualText2.setHeight(0);
                manualText2.setVisibility(View.GONE);
            } else {
                manualText2.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL02);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL03.equalsIgnoreCase("nan")) {
                manualText3.setHeight(0);
                manualText3.setVisibility(View.GONE);
            } else {
                manualText3.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL03);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL04.equalsIgnoreCase("nan")) {
                manualText4.setHeight(0);
                manualText4.setVisibility(View.GONE);
            } else {
                manualText4.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL04);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL05.equalsIgnoreCase("nan")) {
                manualText5.setHeight(0);
                manualText5.setVisibility(View.GONE);
            } else {
                manualText5.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL05);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL06.equalsIgnoreCase("nan")) {
                manualText6.setHeight(0);
                manualText6.setVisibility(View.GONE);
            } else {
                manualText6.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL06);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL07.equalsIgnoreCase("nan")) {
                manualText7.setHeight(0);
                manualText7.setVisibility(View.GONE);
            } else {
                manualText7.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL07);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL08.equalsIgnoreCase("nan")) {
                manualText8.setHeight(0);
                manualText8.setVisibility(View.GONE);
            } else {
                manualText8.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL08);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL09.equalsIgnoreCase("nan")) {
                manualText9.setHeight(0);
                manualText9.setVisibility(View.GONE);
            } else {
                manualText9.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL09);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL10.equalsIgnoreCase("nan")) {
                manualText10.setHeight(0);
                manualText10.setVisibility(View.GONE);
            } else {
                manualText10.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL10);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL11.equalsIgnoreCase("nan")) {
                manualText11.setHeight(0);
                manualText11.setVisibility(View.GONE);
            } else {
                manualText11.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL11);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL12.equalsIgnoreCase("nan")) {
                manualText12.setHeight(0);
                manualText12.setVisibility(View.GONE);
            } else {
                manualText12.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL12);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL13.equalsIgnoreCase("nan")) {
                manualText13.setHeight(0);
                manualText13.setVisibility(View.GONE);
            } else {
                manualText13.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL13);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL14.equalsIgnoreCase("nan")) {
                manualText14.setHeight(0);
                manualText14.setVisibility(View.GONE);
            } else {
                manualText14.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL14);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL15.equalsIgnoreCase("nan")) {
                manualText15.setHeight(0);
                manualText15.setVisibility(View.GONE);
            } else {
                manualText15.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL15);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL16.equalsIgnoreCase("nan")) {
                manualText16.setHeight(0);
                manualText16.setVisibility(View.GONE);
            } else {
                manualText16.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL16);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL17.equalsIgnoreCase("nan")) {
                manualText17.setHeight(0);
                manualText17.setVisibility(View.GONE);
            } else {
                manualText17.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL17);
            }
            if (RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL18.equalsIgnoreCase("nan")) {
                manualText18.setHeight(0);
                manualText18.setVisibility(View.GONE);
            } else {
                manualText18.setText(RecipeGeneralKoreanFragment.itemList.get(recyclerItemPosition).MANUAL18);
            }
        } else if (categoryGeneral.equalsIgnoreCase("양식")) {
            // 음식 사진
            Glide.with(getApplicationContext())
                    .load(Uri.parse(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).ATT_FILE_NO_MAIN))
                    .centerCrop()
                    .into(image);

            // 칼로리
            caloriesText.setText(String.valueOf(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).INFO_ENG) + "Kcal");

            // 단백질
            proteinText.setText(String.valueOf(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).INFO_PRO) + "g");

            // 지방
            fatText.setText(String.valueOf(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).INFO_FAT) + "g");

            // 나트륨
            sodiumText.setText(String.valueOf(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).INFO_NA) + "g");

            // 음식 이름
            foodNameText.setText(RecipeGeneralWesternFragment.itemTitle.get(recyclerItemPosition));

            // 재료
            ingredientText.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).RCP_PARTS_DTLS);

            // 메뉴얼 1 ~ 18
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL01.equalsIgnoreCase("nan")) {
                manualText1.setHeight(0);
                manualText1.setVisibility(View.GONE);
            } else {
                manualText1.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL01);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL02.equalsIgnoreCase("nan")) {
                manualText2.setHeight(0);
                manualText2.setVisibility(View.GONE);
            } else {
                manualText2.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL02);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL03.equalsIgnoreCase("nan")) {
                manualText3.setHeight(0);
                manualText3.setVisibility(View.GONE);
            } else {
                manualText3.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL03);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL04.equalsIgnoreCase("nan")) {
                manualText4.setHeight(0);
                manualText4.setVisibility(View.GONE);
            } else {
                manualText4.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL04);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL05.equalsIgnoreCase("nan")) {
                manualText5.setHeight(0);
                manualText5.setVisibility(View.GONE);
            } else {
                manualText5.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL05);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL06.equalsIgnoreCase("nan")) {
                manualText6.setHeight(0);
                manualText6.setVisibility(View.GONE);
            } else {
                manualText6.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL06);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL07.equalsIgnoreCase("nan")) {
                manualText7.setHeight(0);
                manualText7.setVisibility(View.GONE);
            } else {
                manualText7.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL07);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL08.equalsIgnoreCase("nan")) {
                manualText8.setHeight(0);
                manualText8.setVisibility(View.GONE);
            } else {
                manualText8.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL08);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL09.equalsIgnoreCase("nan")) {
                manualText9.setHeight(0);
                manualText9.setVisibility(View.GONE);
            } else {
                manualText9.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL09);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL10.equalsIgnoreCase("nan")) {
                manualText10.setHeight(0);
                manualText10.setVisibility(View.GONE);
            } else {
                manualText10.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL10);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL11.equalsIgnoreCase("nan")) {
                manualText11.setHeight(0);
                manualText11.setVisibility(View.GONE);
            } else {
                manualText11.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL11);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL12.equalsIgnoreCase("nan")) {
                manualText12.setHeight(0);
                manualText12.setVisibility(View.GONE);
            } else {
                manualText12.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL12);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL13.equalsIgnoreCase("nan")) {
                manualText13.setHeight(0);
                manualText13.setVisibility(View.GONE);
            } else {
                manualText13.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL13);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL14.equalsIgnoreCase("nan")) {
                manualText14.setHeight(0);
                manualText14.setVisibility(View.GONE);
            } else {
                manualText14.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL14);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL15.equalsIgnoreCase("nan")) {
                manualText15.setHeight(0);
                manualText15.setVisibility(View.GONE);
            } else {
                manualText15.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL15);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL16.equalsIgnoreCase("nan")) {
                manualText16.setHeight(0);
                manualText16.setVisibility(View.GONE);
            } else {
                manualText16.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL16);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL17.equalsIgnoreCase("nan")) {
                manualText17.setHeight(0);
                manualText17.setVisibility(View.GONE);
            } else {
                manualText17.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL17);
            }
            if (RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL18.equalsIgnoreCase("nan")) {
                manualText18.setHeight(0);
                manualText18.setVisibility(View.GONE);
            } else {
                manualText18.setText(RecipeGeneralWesternFragment.itemList.get(recyclerItemPosition).MANUAL18);
            }
        } else if (categoryGeneral.equalsIgnoreCase("일식")) {
            // 음식 사진
            Glide.with(getApplicationContext())
                    .load(Uri.parse(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).ATT_FILE_NO_MAIN))
                    .centerCrop()
                    .into(image);

            // 칼로리
            caloriesText.setText(String.valueOf(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).INFO_ENG) + "Kcal");

            // 단백질
            proteinText.setText(String.valueOf(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).INFO_PRO) + "g");

            // 지방
            fatText.setText(String.valueOf(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).INFO_FAT) + "g");

            // 나트륨
            sodiumText.setText(String.valueOf(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).INFO_NA) + "g");

            // 음식 이름
            foodNameText.setText(RecipeGeneralJapaneseFragment.itemTitle.get(recyclerItemPosition));

            // 재료
            ingredientText.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).RCP_PARTS_DTLS);

            // 메뉴얼 1 ~ 18
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL01.equalsIgnoreCase("nan")) {
                manualText1.setHeight(0);
                manualText1.setVisibility(View.GONE);
            } else {
                manualText1.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL01);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL02.equalsIgnoreCase("nan")) {
                manualText2.setHeight(0);
                manualText2.setVisibility(View.GONE);
            } else {
                manualText2.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL02);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL03.equalsIgnoreCase("nan")) {
                manualText3.setHeight(0);
                manualText3.setVisibility(View.GONE);
            } else {
                manualText3.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL03);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL04.equalsIgnoreCase("nan")) {
                manualText4.setHeight(0);
                manualText4.setVisibility(View.GONE);
            } else {
                manualText4.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL04);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL05.equalsIgnoreCase("nan")) {
                manualText5.setHeight(0);
                manualText5.setVisibility(View.GONE);
            } else {
                manualText5.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL05);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL06.equalsIgnoreCase("nan")) {
                manualText6.setHeight(0);
                manualText6.setVisibility(View.GONE);
            } else {
                manualText6.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL06);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL07.equalsIgnoreCase("nan")) {
                manualText7.setHeight(0);
                manualText7.setVisibility(View.GONE);
            } else {
                manualText7.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL07);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL08.equalsIgnoreCase("nan")) {
                manualText8.setHeight(0);
                manualText8.setVisibility(View.GONE);
            } else {
                manualText8.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL08);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL09.equalsIgnoreCase("nan")) {
                manualText9.setHeight(0);
                manualText9.setVisibility(View.GONE);
            } else {
                manualText9.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL09);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL10.equalsIgnoreCase("nan")) {
                manualText10.setHeight(0);
                manualText10.setVisibility(View.GONE);
            } else {
                manualText10.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL10);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL11.equalsIgnoreCase("nan")) {
                manualText11.setHeight(0);
                manualText11.setVisibility(View.GONE);
            } else {
                manualText11.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL11);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL12.equalsIgnoreCase("nan")) {
                manualText12.setHeight(0);
                manualText12.setVisibility(View.GONE);
            } else {
                manualText12.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL12);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL13.equalsIgnoreCase("nan")) {
                manualText13.setHeight(0);
                manualText13.setVisibility(View.GONE);
            } else {
                manualText13.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL13);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL14.equalsIgnoreCase("nan")) {
                manualText14.setHeight(0);
                manualText14.setVisibility(View.GONE);
            } else {
                manualText14.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL14);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL15.equalsIgnoreCase("nan")) {
                manualText15.setHeight(0);
                manualText15.setVisibility(View.GONE);
            } else {
                manualText15.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL15);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL16.equalsIgnoreCase("nan")) {
                manualText16.setHeight(0);
                manualText16.setVisibility(View.GONE);
            } else {
                manualText16.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL16);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL17.equalsIgnoreCase("nan")) {
                manualText17.setHeight(0);
                manualText17.setVisibility(View.GONE);
            } else {
                manualText17.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL17);
            }
            if (RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL18.equalsIgnoreCase("nan")) {
                manualText18.setHeight(0);
                manualText18.setVisibility(View.GONE);
            } else {
                manualText18.setText(RecipeGeneralJapaneseFragment.itemList.get(recyclerItemPosition).MANUAL18);
            }
        } else if (categoryGeneral.equalsIgnoreCase("중식")) {
            // 음식 사진
            Glide.with(getApplicationContext())
                    .load(Uri.parse(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).ATT_FILE_NO_MAIN))
                    .centerCrop()
                    .into(image);

            // 칼로리
            caloriesText.setText(String.valueOf(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).INFO_ENG) + "Kcal");

            // 단백질
            proteinText.setText(String.valueOf(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).INFO_PRO) + "g");

            // 지방
            fatText.setText(String.valueOf(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).INFO_FAT) + "g");

            // 나트륨
            sodiumText.setText(String.valueOf(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).INFO_NA) + "g");

            // 음식 이름
            foodNameText.setText(RecipeGeneralChineseFragment.itemTitle.get(recyclerItemPosition));

            // 재료
            ingredientText.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).RCP_PARTS_DTLS);

            // 메뉴얼 1 ~ 18
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL01.equalsIgnoreCase("nan")) {
                manualText1.setHeight(0);
                manualText1.setVisibility(View.GONE);
            } else {
                manualText1.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL01);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL02.equalsIgnoreCase("nan")) {
                manualText2.setHeight(0);
                manualText2.setVisibility(View.GONE);
            } else {
                manualText2.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL02);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL03.equalsIgnoreCase("nan")) {
                manualText3.setHeight(0);
                manualText3.setVisibility(View.GONE);
            } else {
                manualText3.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL03);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL04.equalsIgnoreCase("nan")) {
                manualText4.setHeight(0);
                manualText4.setVisibility(View.GONE);
            } else {
                manualText4.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL04);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL05.equalsIgnoreCase("nan")) {
                manualText5.setHeight(0);
                manualText5.setVisibility(View.GONE);
            } else {
                manualText5.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL05);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL06.equalsIgnoreCase("nan")) {
                manualText6.setHeight(0);
                manualText6.setVisibility(View.GONE);
            } else {
                manualText6.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL06);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL07.equalsIgnoreCase("nan")) {
                manualText7.setHeight(0);
                manualText7.setVisibility(View.GONE);
            } else {
                manualText7.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL07);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL08.equalsIgnoreCase("nan")) {
                manualText8.setHeight(0);
                manualText8.setVisibility(View.GONE);
            } else {
                manualText8.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL08);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL09.equalsIgnoreCase("nan")) {
                manualText9.setHeight(0);
                manualText9.setVisibility(View.GONE);
            } else {
                manualText9.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL09);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL10.equalsIgnoreCase("nan")) {
                manualText10.setHeight(0);
                manualText10.setVisibility(View.GONE);
            } else {
                manualText10.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL10);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL11.equalsIgnoreCase("nan")) {
                manualText11.setHeight(0);
                manualText11.setVisibility(View.GONE);
            } else {
                manualText11.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL11);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL12.equalsIgnoreCase("nan")) {
                manualText12.setHeight(0);
                manualText12.setVisibility(View.GONE);
            } else {
                manualText12.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL12);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL13.equalsIgnoreCase("nan")) {
                manualText13.setHeight(0);
                manualText13.setVisibility(View.GONE);
            } else {
                manualText13.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL13);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL14.equalsIgnoreCase("nan")) {
                manualText14.setHeight(0);
                manualText14.setVisibility(View.GONE);
            } else {
                manualText14.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL14);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL15.equalsIgnoreCase("nan")) {
                manualText15.setHeight(0);
                manualText15.setVisibility(View.GONE);
            } else {
                manualText15.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL15);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL16.equalsIgnoreCase("nan")) {
                manualText16.setHeight(0);
                manualText16.setVisibility(View.GONE);
            } else {
                manualText16.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL16);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL17.equalsIgnoreCase("nan")) {
                manualText17.setHeight(0);
                manualText17.setVisibility(View.GONE);
            } else {
                manualText17.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL17);
            }
            if (RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL18.equalsIgnoreCase("nan")) {
                manualText18.setHeight(0);
                manualText18.setVisibility(View.GONE);
            } else {
                manualText18.setText(RecipeGeneralChineseFragment.itemList.get(recyclerItemPosition).MANUAL18);
            }
        } else if (categoryGeneral.equalsIgnoreCase("분식")) {
            // 음식 사진
            Glide.with(getApplicationContext())
                    .load(Uri.parse(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).ATT_FILE_NO_MAIN))
                    .centerCrop()
                    .into(image);

            // 칼로리
            caloriesText.setText(String.valueOf(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).INFO_ENG) + "Kcal");

            // 단백질
            proteinText.setText(String.valueOf(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).INFO_PRO) + "g");

            // 지방
            fatText.setText(String.valueOf(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).INFO_FAT) + "g");

            // 나트륨
            sodiumText.setText(String.valueOf(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).INFO_NA) + "g");

            // 음식 이름
            foodNameText.setText(RecipeGeneralBunsikFragment.itemTitle.get(recyclerItemPosition));

            // 재료
            ingredientText.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).RCP_PARTS_DTLS);

            // 메뉴얼 1 ~ 18
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL01.equalsIgnoreCase("nan")) {
                manualText1.setHeight(0);
                manualText1.setVisibility(View.GONE);
            } else {
                manualText1.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL01);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL02.equalsIgnoreCase("nan")) {
                manualText2.setHeight(0);
                manualText2.setVisibility(View.GONE);
            } else {
                manualText2.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL02);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL03.equalsIgnoreCase("nan")) {
                manualText3.setHeight(0);
                manualText3.setVisibility(View.GONE);
            } else {
                manualText3.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL03);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL04.equalsIgnoreCase("nan")) {
                manualText4.setHeight(0);
                manualText4.setVisibility(View.GONE);
            } else {
                manualText4.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL04);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL05.equalsIgnoreCase("nan")) {
                manualText5.setHeight(0);
                manualText5.setVisibility(View.GONE);
            } else {
                manualText5.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL05);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL06.equalsIgnoreCase("nan")) {
                manualText6.setHeight(0);
                manualText6.setVisibility(View.GONE);
            } else {
                manualText6.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL06);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL07.equalsIgnoreCase("nan")) {
                manualText7.setHeight(0);
                manualText7.setVisibility(View.GONE);
            } else {
                manualText7.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL07);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL08.equalsIgnoreCase("nan")) {
                manualText8.setHeight(0);
                manualText8.setVisibility(View.GONE);
            } else {
                manualText8.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL08);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL09.equalsIgnoreCase("nan")) {
                manualText9.setHeight(0);
                manualText9.setVisibility(View.GONE);
            } else {
                manualText9.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL09);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL10.equalsIgnoreCase("nan")) {
                manualText10.setHeight(0);
                manualText10.setVisibility(View.GONE);
            } else {
                manualText10.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL10);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL11.equalsIgnoreCase("nan")) {
                manualText11.setHeight(0);
                manualText11.setVisibility(View.GONE);
            } else {
                manualText11.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL11);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL12.equalsIgnoreCase("nan")) {
                manualText12.setHeight(0);
                manualText12.setVisibility(View.GONE);
            } else {
                manualText12.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL12);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL13.equalsIgnoreCase("nan")) {
                manualText13.setHeight(0);
                manualText13.setVisibility(View.GONE);
            } else {
                manualText13.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL13);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL14.equalsIgnoreCase("nan")) {
                manualText14.setHeight(0);
                manualText14.setVisibility(View.GONE);
            } else {
                manualText14.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL14);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL15.equalsIgnoreCase("nan")) {
                manualText15.setHeight(0);
                manualText15.setVisibility(View.GONE);
            } else {
                manualText15.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL15);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL16.equalsIgnoreCase("nan")) {
                manualText16.setHeight(0);
                manualText16.setVisibility(View.GONE);
            } else {
                manualText16.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL16);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL17.equalsIgnoreCase("nan")) {
                manualText17.setHeight(0);
                manualText17.setVisibility(View.GONE);
            } else {
                manualText17.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL17);
            }
            if (RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL18.equalsIgnoreCase("nan")) {
                manualText18.setHeight(0);
                manualText18.setVisibility(View.GONE);
            } else {
                manualText18.setText(RecipeGeneralBunsikFragment.itemList.get(recyclerItemPosition).MANUAL18);
            }
        } else if (categoryGeneral.equalsIgnoreCase("퓨전")) {
            // 음식 사진
            Glide.with(getApplicationContext())
                    .load(Uri.parse(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).ATT_FILE_NO_MAIN))
                    .centerCrop()
                    .into(image);

            // 칼로리
            caloriesText.setText(String.valueOf(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).INFO_ENG) + "Kcal");

            // 단백질
            proteinText.setText(String.valueOf(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).INFO_PRO) + "g");

            // 지방
            fatText.setText(String.valueOf(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).INFO_FAT) + "g");

            // 나트륨
            sodiumText.setText(String.valueOf(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).INFO_NA) + "g");

            // 음식 이름
            foodNameText.setText(RecipeGeneralFusionFragment.itemTitle.get(recyclerItemPosition));

            // 재료
            ingredientText.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).RCP_PARTS_DTLS);

            // 메뉴얼 1 ~ 18
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL01.equalsIgnoreCase("nan")) {
                manualText1.setHeight(0);
                manualText1.setVisibility(View.GONE);
            } else {
                manualText1.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL01);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL02.equalsIgnoreCase("nan")) {
                manualText2.setHeight(0);
                manualText2.setVisibility(View.GONE);
            } else {
                manualText2.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL02);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL03.equalsIgnoreCase("nan")) {
                manualText3.setHeight(0);
                manualText3.setVisibility(View.GONE);
            } else {
                manualText3.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL03);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL04.equalsIgnoreCase("nan")) {
                manualText4.setHeight(0);
                manualText4.setVisibility(View.GONE);
            } else {
                manualText4.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL04);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL05.equalsIgnoreCase("nan")) {
                manualText5.setHeight(0);
                manualText5.setVisibility(View.GONE);
            } else {
                manualText5.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL05);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL06.equalsIgnoreCase("nan")) {
                manualText6.setHeight(0);
                manualText6.setVisibility(View.GONE);
            } else {
                manualText6.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL06);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL07.equalsIgnoreCase("nan")) {
                manualText7.setHeight(0);
                manualText7.setVisibility(View.GONE);
            } else {
                manualText7.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL07);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL08.equalsIgnoreCase("nan")) {
                manualText8.setHeight(0);
                manualText8.setVisibility(View.GONE);
            } else {
                manualText8.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL08);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL09.equalsIgnoreCase("nan")) {
                manualText9.setHeight(0);
                manualText9.setVisibility(View.GONE);
            } else {
                manualText9.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL09);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL10.equalsIgnoreCase("nan")) {
                manualText10.setHeight(0);
                manualText10.setVisibility(View.GONE);
            } else {
                manualText10.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL10);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL11.equalsIgnoreCase("nan")) {
                manualText11.setHeight(0);
                manualText11.setVisibility(View.GONE);
            } else {
                manualText11.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL11);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL12.equalsIgnoreCase("nan")) {
                manualText12.setHeight(0);
                manualText12.setVisibility(View.GONE);
            } else {
                manualText12.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL12);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL13.equalsIgnoreCase("nan")) {
                manualText13.setHeight(0);
                manualText13.setVisibility(View.GONE);
            } else {
                manualText13.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL13);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL14.equalsIgnoreCase("nan")) {
                manualText14.setHeight(0);
                manualText14.setVisibility(View.GONE);
            } else {
                manualText14.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL14);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL15.equalsIgnoreCase("nan")) {
                manualText15.setHeight(0);
                manualText15.setVisibility(View.GONE);
            } else {
                manualText15.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL15);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL16.equalsIgnoreCase("nan")) {
                manualText16.setHeight(0);
                manualText16.setVisibility(View.GONE);
            } else {
                manualText16.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL16);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL17.equalsIgnoreCase("nan")) {
                manualText17.setHeight(0);
                manualText17.setVisibility(View.GONE);
            } else {
                manualText17.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL17);
            }
            if (RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL18.equalsIgnoreCase("nan")) {
                manualText18.setHeight(0);
                manualText18.setVisibility(View.GONE);
            } else {
                manualText18.setText(RecipeGeneralFusionFragment.itemList.get(recyclerItemPosition).MANUAL18);
            }
        } else if (categoryGeneral.equalsIgnoreCase("아시안")) {
            // 음식 사진
            Glide.with(getApplicationContext())
                    .load(Uri.parse(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).ATT_FILE_NO_MAIN))
                    .centerCrop()
                    .into(image);

            // 칼로리
            caloriesText.setText(String.valueOf(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).INFO_ENG) + "Kcal");

            // 단백질
            proteinText.setText(String.valueOf(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).INFO_PRO) + "g");

            // 지방
            fatText.setText(String.valueOf(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).INFO_FAT) + "g");

            // 나트륨
            sodiumText.setText(String.valueOf(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).INFO_NA) + "g");

            // 음식 이름
            foodNameText.setText(RecipeGeneralAsianFragment.itemTitle.get(recyclerItemPosition));

            // 재료
            ingredientText.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).RCP_PARTS_DTLS);

            // 메뉴얼 1 ~ 18
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL01.equalsIgnoreCase("nan")) {
                manualText1.setHeight(0);
                manualText1.setVisibility(View.GONE);
            } else {
                manualText1.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL01);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL02.equalsIgnoreCase("nan")) {
                manualText2.setHeight(0);
                manualText2.setVisibility(View.GONE);
            } else {
                manualText2.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL02);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL03.equalsIgnoreCase("nan")) {
                manualText3.setHeight(0);
                manualText3.setVisibility(View.GONE);
            } else {
                manualText3.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL03);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL04.equalsIgnoreCase("nan")) {
                manualText4.setHeight(0);
                manualText4.setVisibility(View.GONE);
            } else {
                manualText4.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL04);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL05.equalsIgnoreCase("nan")) {
                manualText5.setHeight(0);
                manualText5.setVisibility(View.GONE);
            } else {
                manualText5.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL05);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL06.equalsIgnoreCase("nan")) {
                manualText6.setHeight(0);
                manualText6.setVisibility(View.GONE);
            } else {
                manualText6.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL06);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL07.equalsIgnoreCase("nan")) {
                manualText7.setHeight(0);
                manualText7.setVisibility(View.GONE);
            } else {
                manualText7.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL07);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL08.equalsIgnoreCase("nan")) {
                manualText8.setHeight(0);
                manualText8.setVisibility(View.GONE);
            } else {
                manualText8.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL08);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL09.equalsIgnoreCase("nan")) {
                manualText9.setHeight(0);
                manualText9.setVisibility(View.GONE);
            } else {
                manualText9.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL09);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL10.equalsIgnoreCase("nan")) {
                manualText10.setHeight(0);
                manualText10.setVisibility(View.GONE);
            } else {
                manualText10.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL10);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL11.equalsIgnoreCase("nan")) {
                manualText11.setHeight(0);
                manualText11.setVisibility(View.GONE);
            } else {
                manualText11.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL11);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL12.equalsIgnoreCase("nan")) {
                manualText12.setHeight(0);
                manualText12.setVisibility(View.GONE);
            } else {
                manualText12.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL12);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL13.equalsIgnoreCase("nan")) {
                manualText13.setHeight(0);
                manualText13.setVisibility(View.GONE);
            } else {
                manualText13.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL13);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL14.equalsIgnoreCase("nan")) {
                manualText14.setHeight(0);
                manualText14.setVisibility(View.GONE);
            } else {
                manualText14.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL14);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL15.equalsIgnoreCase("nan")) {
                manualText15.setHeight(0);
                manualText15.setVisibility(View.GONE);
            } else {
                manualText15.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL15);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL16.equalsIgnoreCase("nan")) {
                manualText16.setHeight(0);
                manualText16.setVisibility(View.GONE);
            } else {
                manualText16.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL16);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL17.equalsIgnoreCase("nan")) {
                manualText17.setHeight(0);
                manualText17.setVisibility(View.GONE);
            } else {
                manualText17.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL17);
            }
            if (RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL18.equalsIgnoreCase("nan")) {
                manualText18.setHeight(0);
                manualText18.setVisibility(View.GONE);
            } else {
                manualText18.setText(RecipeGeneralAsianFragment.itemList.get(recyclerItemPosition).MANUAL18);
            }
        } else if (categoryGeneral.equalsIgnoreCase("샐러드")) {
            // 음식 사진
            Glide.with(getApplicationContext())
                    .load(Uri.parse(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).ATT_FILE_NO_MAIN))
                    .centerCrop()
                    .into(image);

            // 칼로리
            caloriesText.setText(String.valueOf(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).INFO_ENG) + "Kcal");

            // 단백질
            proteinText.setText(String.valueOf(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).INFO_PRO) + "g");

            // 지방
            fatText.setText(String.valueOf(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).INFO_FAT) + "g");

            // 나트륨
            sodiumText.setText(String.valueOf(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).INFO_NA) + "g");

            // 음식 이름
            foodNameText.setText(RecipeGeneralSaladFragment.itemTitle.get(recyclerItemPosition));

            // 재료
            ingredientText.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).RCP_PARTS_DTLS);

            // 메뉴얼 1 ~ 18
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL01.equalsIgnoreCase("nan")) {
                manualText1.setHeight(0);
                manualText1.setVisibility(View.GONE);
            } else {
                manualText1.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL01);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL02.equalsIgnoreCase("nan")) {
                manualText2.setHeight(0);
                manualText2.setVisibility(View.GONE);
            } else {
                manualText2.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL02);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL03.equalsIgnoreCase("nan")) {
                manualText3.setHeight(0);
                manualText3.setVisibility(View.GONE);
            } else {
                manualText3.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL03);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL04.equalsIgnoreCase("nan")) {
                manualText4.setHeight(0);
                manualText4.setVisibility(View.GONE);
            } else {
                manualText4.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL04);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL05.equalsIgnoreCase("nan")) {
                manualText5.setHeight(0);
                manualText5.setVisibility(View.GONE);
            } else {
                manualText5.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL05);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL06.equalsIgnoreCase("nan")) {
                manualText6.setHeight(0);
                manualText6.setVisibility(View.GONE);
            } else {
                manualText6.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL06);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL07.equalsIgnoreCase("nan")) {
                manualText7.setHeight(0);
                manualText7.setVisibility(View.GONE);
            } else {
                manualText7.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL07);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL08.equalsIgnoreCase("nan")) {
                manualText8.setHeight(0);
                manualText8.setVisibility(View.GONE);
            } else {
                manualText8.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL08);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL09.equalsIgnoreCase("nan")) {
                manualText9.setHeight(0);
                manualText9.setVisibility(View.GONE);
            } else {
                manualText9.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL09);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL10.equalsIgnoreCase("nan")) {
                manualText10.setHeight(0);
                manualText10.setVisibility(View.GONE);
            } else {
                manualText10.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL10);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL11.equalsIgnoreCase("nan")) {
                manualText11.setHeight(0);
                manualText11.setVisibility(View.GONE);
            } else {
                manualText11.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL11);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL12.equalsIgnoreCase("nan")) {
                manualText12.setHeight(0);
                manualText12.setVisibility(View.GONE);
            } else {
                manualText12.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL12);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL13.equalsIgnoreCase("nan")) {
                manualText13.setHeight(0);
                manualText13.setVisibility(View.GONE);
            } else {
                manualText13.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL13);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL14.equalsIgnoreCase("nan")) {
                manualText14.setHeight(0);
                manualText14.setVisibility(View.GONE);
            } else {
                manualText14.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL14);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL15.equalsIgnoreCase("nan")) {
                manualText15.setHeight(0);
                manualText15.setVisibility(View.GONE);
            } else {
                manualText15.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL15);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL16.equalsIgnoreCase("nan")) {
                manualText16.setHeight(0);
                manualText16.setVisibility(View.GONE);
            } else {
                manualText16.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL16);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL17.equalsIgnoreCase("nan")) {
                manualText17.setHeight(0);
                manualText17.setVisibility(View.GONE);
            } else {
                manualText17.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL17);
            }
            if (RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL18.equalsIgnoreCase("nan")) {
                manualText18.setHeight(0);
                manualText18.setVisibility(View.GONE);
            } else {
                manualText18.setText(RecipeGeneralSaladFragment.itemList.get(recyclerItemPosition).MANUAL18);
            }
        } else if (categoryGeneral.equalsIgnoreCase("디저트")) {
            // 음식 사진
            Glide.with(getApplicationContext())
                    .load(Uri.parse(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).ATT_FILE_NO_MAIN))
                    .centerCrop()
                    .into(image);

            // 칼로리
            caloriesText.setText(String.valueOf(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).INFO_ENG) + "Kcal");

            // 단백질
            proteinText.setText(String.valueOf(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).INFO_PRO) + "g");

            // 지방
            fatText.setText(String.valueOf(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).INFO_FAT) + "g");

            // 나트륨
            sodiumText.setText(String.valueOf(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).INFO_NA) + "g");

            // 음식 이름
            foodNameText.setText(RecipeGeneralDessertFragment.itemTitle.get(recyclerItemPosition));

            // 재료
            ingredientText.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).RCP_PARTS_DTLS);

            // 메뉴얼 1 ~ 18
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL01.equalsIgnoreCase("nan")) {
                manualText1.setHeight(0);
                manualText1.setVisibility(View.GONE);
            } else {
                manualText1.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL01);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL02.equalsIgnoreCase("nan")) {
                manualText2.setHeight(0);
                manualText2.setVisibility(View.GONE);
            } else {
                manualText2.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL02);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL03.equalsIgnoreCase("nan")) {
                manualText3.setHeight(0);
                manualText3.setVisibility(View.GONE);
            } else {
                manualText3.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL03);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL04.equalsIgnoreCase("nan")) {
                manualText4.setHeight(0);
                manualText4.setVisibility(View.GONE);
            } else {
                manualText4.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL04);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL05.equalsIgnoreCase("nan")) {
                manualText5.setHeight(0);
                manualText5.setVisibility(View.GONE);
            } else {
                manualText5.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL05);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL06.equalsIgnoreCase("nan")) {
                manualText6.setHeight(0);
                manualText6.setVisibility(View.GONE);
            } else {
                manualText6.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL06);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL07.equalsIgnoreCase("nan")) {
                manualText7.setHeight(0);
                manualText7.setVisibility(View.GONE);
            } else {
                manualText7.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL07);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL08.equalsIgnoreCase("nan")) {
                manualText8.setHeight(0);
                manualText8.setVisibility(View.GONE);
            } else {
                manualText8.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL08);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL09.equalsIgnoreCase("nan")) {
                manualText9.setHeight(0);
                manualText9.setVisibility(View.GONE);
            } else {
                manualText9.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL09);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL10.equalsIgnoreCase("nan")) {
                manualText10.setHeight(0);
                manualText10.setVisibility(View.GONE);
            } else {
                manualText10.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL10);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL11.equalsIgnoreCase("nan")) {
                manualText11.setHeight(0);
                manualText11.setVisibility(View.GONE);
            } else {
                manualText11.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL11);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL12.equalsIgnoreCase("nan")) {
                manualText12.setHeight(0);
                manualText12.setVisibility(View.GONE);
            } else {
                manualText12.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL12);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL13.equalsIgnoreCase("nan")) {
                manualText13.setHeight(0);
                manualText13.setVisibility(View.GONE);
            } else {
                manualText13.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL13);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL14.equalsIgnoreCase("nan")) {
                manualText14.setHeight(0);
                manualText14.setVisibility(View.GONE);
            } else {
                manualText14.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL14);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL15.equalsIgnoreCase("nan")) {
                manualText15.setHeight(0);
                manualText15.setVisibility(View.GONE);
            } else {
                manualText15.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL15);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL16.equalsIgnoreCase("nan")) {
                manualText16.setHeight(0);
                manualText16.setVisibility(View.GONE);
            } else {
                manualText16.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL16);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL17.equalsIgnoreCase("nan")) {
                manualText17.setHeight(0);
                manualText17.setVisibility(View.GONE);
            } else {
                manualText17.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL17);
            }
            if (RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL18.equalsIgnoreCase("nan")) {
                manualText18.setHeight(0);
                manualText18.setVisibility(View.GONE);
            } else {
                manualText18.setText(RecipeGeneralDessertFragment.itemList.get(recyclerItemPosition).MANUAL18);
            }
        } else {      // 비건의 경우
            Toast.makeText(getApplicationContext(), "else: 비건", Toast.LENGTH_SHORT).show();
            // 음식 사진
            Glide.with(getApplicationContext())
                    .load(Uri.parse(RecipeVeganFragment.itemList.get(recyclerItemPosition).ATT_FILE_NO_MAIN))
                    .centerCrop()
                    .into(image);

            // 칼로리
            caloriesText.setText(String.valueOf(RecipeVeganFragment.itemList.get(recyclerItemPosition).INFO_ENG) + "Kcal");

            // 단백질
            proteinText.setText(String.valueOf(RecipeVeganFragment.itemList.get(recyclerItemPosition).INFO_PRO) + "g");

            // 지방
            fatText.setText(String.valueOf(RecipeVeganFragment.itemList.get(recyclerItemPosition).INFO_FAT) + "g");

            // 나트륨
            sodiumText.setText(String.valueOf(RecipeVeganFragment.itemList.get(recyclerItemPosition).INFO_NA) + "g");

            // 음식 이름
            foodNameText.setText(RecipeVeganFragment.itemTitle.get(recyclerItemPosition));

            // 재료
            ingredientText.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).RCP_PARTS_DTLS);

            // 메뉴얼 1 ~ 18
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL01.equalsIgnoreCase("nan")) {
                manualText1.setHeight(0);
                manualText1.setVisibility(View.GONE);
            } else {
                manualText1.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL01);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL02.equalsIgnoreCase("nan")) {
                manualText2.setHeight(0);
                manualText2.setVisibility(View.GONE);
            } else {
                manualText2.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL02);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL03.equalsIgnoreCase("nan")) {
                manualText3.setHeight(0);
                manualText3.setVisibility(View.GONE);
            } else {
                manualText3.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL03);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL04.equalsIgnoreCase("nan")) {
                manualText4.setHeight(0);
                manualText4.setVisibility(View.GONE);
                manualText4.setVisibility(View.GONE);
            } else {
                manualText4.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL04);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL05.equalsIgnoreCase("nan")) {
                manualText5.setHeight(0);
                manualText5.setVisibility(View.GONE);
            } else {
                manualText5.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL05);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL06.equalsIgnoreCase("nan")) {
                manualText6.setHeight(0);
                manualText6.setVisibility(View.GONE);
            } else {
                manualText6.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL06);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL07.equalsIgnoreCase("nan")) {
                manualText7.setHeight(0);
                manualText7.setVisibility(View.GONE);
            } else {
                manualText7.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL07);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL08.equalsIgnoreCase("nan")) {
                manualText8.setHeight(0);
                manualText8.setVisibility(View.GONE);
            } else {
                manualText8.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL08);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL09.equalsIgnoreCase("nan")) {
                manualText9.setHeight(0);
                manualText9.setVisibility(View.GONE);
            } else {
                manualText9.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL09);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL10.equalsIgnoreCase("nan")) {
                manualText10.setHeight(0);
                manualText10.setVisibility(View.GONE);
            } else {
                manualText10.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL10);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL11.equalsIgnoreCase("nan")) {
                manualText11.setHeight(0);
                manualText11.setVisibility(View.GONE);
            } else {
                manualText11.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL11);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL12.equalsIgnoreCase("nan")) {
                manualText12.setHeight(0);
                manualText12.setVisibility(View.GONE);
            } else {
                manualText12.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL12);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL13.equalsIgnoreCase("nan")) {
                manualText13.setHeight(0);
                manualText13.setVisibility(View.GONE);
            } else {
                manualText13.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL13);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL14.equalsIgnoreCase("nan")) {
                manualText14.setHeight(0);
                manualText14.setVisibility(View.GONE);
            } else {
                manualText14.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL14);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL15.equalsIgnoreCase("nan")) {
                manualText15.setHeight(0);
                manualText15.setVisibility(View.GONE);
            } else {
                manualText15.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL15);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL16.equalsIgnoreCase("nan")) {
                manualText16.setHeight(0);
                manualText16.setVisibility(View.GONE);
            } else {
                manualText16.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL16);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL17.equalsIgnoreCase("nan")) {
                manualText17.setHeight(0);
                manualText17.setVisibility(View.GONE);
            } else {
                manualText17.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL17);
            }
            if (RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL18.equalsIgnoreCase("nan")) {
                manualText18.setHeight(0);
                manualText18.setVisibility(View.GONE);
            } else {
                manualText18.setText(RecipeVeganFragment.itemList.get(recyclerItemPosition).MANUAL18);
            }
        }



        // 레시피 더보기 버튼 이벤트
        readMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레시피 메뉴얼 constraint layout height 변경
//                Toast.makeText(getApplicationContext(), "manualLayout.height = " + String.valueOf(manualLayout.getHeight()), Toast.LENGTH_SHORT).show();
                if (manualLayout.getHeight() <= 600){
                    int height = 500;
                    if (manualText1.getHeight() != 0) {
                        height += manualText1.getHeight() + 8;
                    }
                    if (manualText2.getHeight() != 0) {
                        height += manualText2.getHeight() + 8;
                    }
                    if (manualText3.getHeight() != 0) {
                        height += manualText3.getHeight() + 8;
                    }
                    if (manualText4.getHeight() != 0) {
                        height += manualText4.getHeight() + 8;
                    }
                    if (manualText5.getHeight() != 0) {
                        height += manualText5.getHeight() + 8;
                    }
                    if (manualText6.getHeight() != 0) {
                        height += manualText6.getHeight() + 8;
                    }
                    if (manualText7.getHeight() != 0) {
                        height += manualText7.getHeight() + 8;
                    }
                    if (manualText8.getHeight() != 0) {
                        height += manualText8.getHeight() + 8;
                    }
                    if (manualText9.getHeight() != 0) {
                        height += manualText9.getHeight() + 8;
                    }
                    if (manualText10.getHeight() != 0) {
                        height += manualText10.getHeight() + 8;
                    }
                    if (manualText11.getHeight() != 0) {
                        height += manualText11.getHeight() + 8;
                    }
                    if (manualText12.getHeight() != 0) {
                        height += manualText12.getHeight() + 8;
                    }
                    if (manualText13.getHeight() != 0) {
                        height += manualText13.getHeight() + 8;
                    }
                    if (manualText14.getHeight() != 0) {
                        height += manualText14.getHeight() + 8;
                    }
                    if (manualText15.getHeight() != 0) {
                        height += manualText15.getHeight() + 8;
                    }
                    if (manualText16.getHeight() != 0) {
                        height += manualText16.getHeight() + 8;
                    }
                    if (manualText17.getHeight() != 0) {
                        height += manualText17.getHeight() + 8;
                    }
                    if (manualText18.getHeight() != 0) {
                        height += manualText18.getHeight() + 8;
                    }
                    
                    height += ingredientText.getHeight() + 35;

                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, height);
                    params.height = height;
                    params.topToBottom = R.id.recipe_detail_line1;
                    params.bottomToTop = R.id.activity_recipe_detail_manual_read_more_button;
                    manualLayout.setLayoutParams(params);
                    
                } else {
//                    manualLayout.setMinHeight(200);
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 600);
                    params.height = 600;
                    params.topToBottom = R.id.recipe_detail_line1;
                    params.bottomToTop = R.id.activity_recipe_detail_manual_read_more_button;
                    manualLayout.setLayoutParams(params);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:     // toolbar의 뒤로가기 버튼을 눌렀을 때
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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