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

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.barofutures.seco.adapter.FreeModePagerAdapter;
import com.barofutures.seco.fragments.freemode.FreeModeActivityFragment;
import com.barofutures.seco.fragments.freemode.ChallengeFragment;
import com.barofutures.seco.fragments.freemode.FreeModeMealFragment;
import com.barofutures.seco.fragments.freemode.FreeModeQuestFragment;
import com.barofutures.seco.model.ContentsDetailData;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ContentsListActivity extends AppCompatActivity {

//    private ActivityFreeModeBinding binding;

    private Toolbar toolbar;

    // Tab & Fragment
    private TabLayout freeModeTabLayout;
    private ViewPager viewPager;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private FreeModeMealFragment mealFragment;
    private FreeModeActivityFragment activityFragment;
    private FreeModeQuestFragment questFragment;
    private ChallengeFragment favoritesFragment;

    public static ContentsDetailData contentsDetailDataMeal;
    public static ContentsDetailData contentsDetailDataActivity;
    public static ContentsDetailData contentsDetailDataQuest;
    public static ContentsDetailData contentsDetailDataFavorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_list);

        // ????????? ?????? ??????
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar ??????
        toolbar=findViewById(R.id.activity_free_mode_material_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Status Bar ???????????? Padding ??????
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.activity_free_mode_view_pager);

        // TabLayout??? viewPager ??????
        freeModeTabLayout = (TabLayout) findViewById(R.id.activity_free_mode_tabs);
        freeModeTabLayout.setupWithViewPager(viewPager);

        // user??? free mode ????????? ?????????
        contentsDetailDataMeal = getFreeModeContentsListDataMeal();
        contentsDetailDataActivity = getFreeModeContentsListDataActivity();
        contentsDetailDataQuest = getFreeModeContentsListDataQuest();
        contentsDetailDataFavorites =getFreeModeContentsListDataListFavorites();

        // create fragments
        mealFragment = new FreeModeMealFragment(contentsDetailDataMeal);
        activityFragment = new FreeModeActivityFragment(contentsDetailDataActivity);
        questFragment = new FreeModeQuestFragment(contentsDetailDataQuest);
        favoritesFragment = new ChallengeFragment();

        // connect Fragment using ViewPagerAdapter
        FreeModePagerAdapter viewPagerAdapter = new FreeModePagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(mealFragment, "??????");
        viewPagerAdapter.addFragment(activityFragment, "??????");
        viewPagerAdapter.addFragment(questFragment, "?????????");
        viewPagerAdapter.addFragment(favoritesFragment, "??????");
        viewPager.setAdapter(viewPagerAdapter);



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:     // toolbar??? ???????????? ????????? ????????? ???
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //status bar??? ?????? ??????
    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }

    // user??? free mode ????????? ????????? (meal)
    public ContentsDetailData getFreeModeContentsListDataMeal() {

        ContentsDetailData meal = new ContentsDetailData();
        meal.setMeal();

        return meal;
    }

    // user??? free mode ????????? ????????? (activity)
    public ContentsDetailData getFreeModeContentsListDataActivity() {

        ContentsDetailData activity = new ContentsDetailData();
        activity.setActivity();

        return activity;
    }

    // user??? free mode ????????? ????????? (quest)
    public ContentsDetailData getFreeModeContentsListDataQuest() {

        ContentsDetailData quest = new ContentsDetailData();
        quest.setQuest();

        return quest;
    }

    // user??? free mode ????????? ????????? (Favorites)
    public ContentsDetailData getFreeModeContentsListDataListFavorites() {


        ContentsDetailData favorites = new ContentsDetailData();
        favorites.setFavorites();

        return favorites;
    }
}