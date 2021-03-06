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
import com.barofutures.seco.fragments.freemode.FreeModeLikeFragment;
import com.barofutures.seco.fragments.freemode.FreeModeMealFragment;
import com.barofutures.seco.fragments.freemode.FreeModeQuestFragment;
import com.barofutures.seco.model.FreeModeContentsListData;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FreeModeActivity extends AppCompatActivity {

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
    private FreeModeLikeFragment favoritesFragment;

    public static FreeModeContentsListData freeModeContentsListDataMeal;
    public static FreeModeContentsListData freeModeContentsListDataActivity;
    public static FreeModeContentsListData freeModeContentsListDataQuest;
    public static FreeModeContentsListData freeModeContentsListDataFavorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_list);

        // 상단바 완전 투명
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // actionBar 설정
        toolbar=findViewById(R.id.activity_free_mode_material_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Status Bar 높이만큼 Padding 부여
        toolbar.setPadding(0,getStatusBarHeight(), 0, 0);

        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.activity_free_mode_view_pager);

        // TabLayout에 viewPager 연결
        freeModeTabLayout = (TabLayout) findViewById(R.id.activity_free_mode_tabs);
        freeModeTabLayout.setupWithViewPager(viewPager);

        // user의 free mode 데이터 받아옴
        freeModeContentsListDataMeal = getFreeModeContentsListDataMeal();
        freeModeContentsListDataActivity = getFreeModeContentsListDataActivity();
        freeModeContentsListDataQuest = getFreeModeContentsListDataQuest();
        freeModeContentsListDataFavorites=getFreeModeContentsListDataListFavorites();

        // create fragments
        mealFragment = new FreeModeMealFragment(freeModeContentsListDataMeal);
        activityFragment = new FreeModeActivityFragment(freeModeContentsListDataActivity);
        questFragment = new FreeModeQuestFragment(freeModeContentsListDataQuest);
        favoritesFragment = new FreeModeLikeFragment(freeModeContentsListDataFavorites);

        // connect Fragment using ViewPagerAdapter
        FreeModePagerAdapter viewPagerAdapter = new FreeModePagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(mealFragment, "식단");
        viewPagerAdapter.addFragment(activityFragment, "활동");
        viewPagerAdapter.addFragment(questFragment, "퀘스트");
        viewPagerAdapter.addFragment(favoritesFragment, "관심");
        viewPager.setAdapter(viewPagerAdapter);



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:     // toolbar의 뒤로가기 버튼을 눌렀을 때
//                Intent intent = new Intent(FreeModeActivity.this, MainActivity.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        // 현재(FreeModeActivity) 액티비티와 이 위에 있는 모든 애티비티를 종료시키고 이동
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                startActivity(intent);
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

    // user의 free mode 데이터 받아옴 (meal)
    public FreeModeContentsListData getFreeModeContentsListDataMeal() {
        //TODO: 사용자의 좋아요, 활성화 유무 데이터 받아오기
        ArrayList<Boolean> isFavorites = new ArrayList<>();
        isFavorites.add(true);
        isFavorites.add(true);
        isFavorites.add(false);
        isFavorites.add(false);

        ArrayList<Boolean> isEnabled = new ArrayList<>();
        isEnabled.add(false);
        isEnabled.add(true);
        isEnabled.add(true);
        isEnabled.add(true);

        FreeModeContentsListData meal = new FreeModeContentsListData();
        meal.setMeal(isFavorites, isEnabled);

        return meal;
    }

    // user의 free mode 데이터 받아옴 (activity)
    public FreeModeContentsListData getFreeModeContentsListDataActivity() {
        //TODO: 사용자의 좋아요, 활성화 유무 데이터 받아오기
        ArrayList<Boolean> isFavorites = new ArrayList<>();
        isFavorites.add(true);
        isFavorites.add(true);
        isFavorites.add(false);
        isFavorites.add(false);

        ArrayList<Boolean> isEnabled = new ArrayList<>();
        isEnabled.add(false);
        isEnabled.add(true);
        isEnabled.add(true);
        isEnabled.add(true);

        FreeModeContentsListData activity = new FreeModeContentsListData();
        activity.setActivity(isFavorites, isEnabled);

        return activity;
    }

    // user의 free mode 데이터 받아옴 (quest)
    public FreeModeContentsListData getFreeModeContentsListDataQuest() {
        //TODO: 사용자의 좋아요, 활성화 유무 데이터 받아오기
        ArrayList<Boolean> isFavorites = new ArrayList<>();
        isFavorites.add(true);
        isFavorites.add(true);
        isFavorites.add(false);
        isFavorites.add(false);
        isFavorites.add(true);
        isFavorites.add(true);
        isFavorites.add(false);
        isFavorites.add(false);


        ArrayList<Boolean> isEnabled = new ArrayList<>();
        isEnabled.add(false);
        isEnabled.add(true);
        isEnabled.add(true);
        isEnabled.add(true);
        isEnabled.add(false);
        isEnabled.add(true);
        isEnabled.add(true);
        isEnabled.add(true);


        FreeModeContentsListData quest = new FreeModeContentsListData();
        quest.setQuest(isFavorites, isEnabled);

        return quest;
    }

    // user의 free mode 데이터 받아옴 (Favorites)
    public FreeModeContentsListData getFreeModeContentsListDataListFavorites() {
        //TODO: 사용자의 좋아요, 활성화 유무 데이터 받아오기
        ArrayList<Boolean> isFavorites = new ArrayList<>();
        isFavorites.add(true);
        isFavorites.add(true);
        isFavorites.add(true);

        ArrayList<Boolean> isEnabled = new ArrayList<>();
        isEnabled.add(false);
        isEnabled.add(true);
        isEnabled.add(true);

        FreeModeContentsListData favorites = new FreeModeContentsListData();
        favorites.setFavorites(isFavorites, isEnabled);

        return favorites;
    }
}