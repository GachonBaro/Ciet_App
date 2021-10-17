package com.barofutures.seco.fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barofutures.seco.R;
import com.barofutures.seco.adapter.recipeTab.CarbonDietTabPagerAdapter;
import com.barofutures.seco.model.ContentsDetailData;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_CarbonDiet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_CarbonDiet extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TabLayout tabLayout;         // Carbon Diet 탭 TabLayout (식단, 활동, 퀘스트, 진행중)

    private CarbonDietTabPagerAdapter carbonDietTabPagerAdapter;        // Carbon Diet 탭 adapter (식단, 활동, 퀘스트, 진행중)
    private ViewPager2 viewPager2;                              // Carbon Diet viewPager (식단, 활동, 퀘스트, 진행중)

    public static ContentsDetailData contentsDetailDataMeal;
    public static ContentsDetailData contentsDetailDataActivity;
    public static ContentsDetailData contentsDetailDataQuest;
//    public static ContentsDetailData contentsDetailDataFavorites;

    public Fragment_CarbonDiet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_CarbonDiet.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_CarbonDiet newInstance(String param1, String param2) {
        Fragment_CarbonDiet fragment = new Fragment_CarbonDiet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_carbon_diet, container, false);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // user의 free mode 데이터 받아옴
        contentsDetailDataMeal = getFreeModeContentsListDataMeal();
        contentsDetailDataActivity = getFreeModeContentsListDataActivity();
        contentsDetailDataQuest = getFreeModeContentsListDataQuest();
//        contentsDetailDataFavorites =getFreeModeContentsListDataListFavorites();

        // TabLayout에 viewPager 연결
        tabLayout = (TabLayout) view.findViewById(R.id.fragment_carbon_diet_tabs);
        carbonDietTabPagerAdapter = new CarbonDietTabPagerAdapter(this, contentsDetailDataMeal, contentsDetailDataActivity, contentsDetailDataQuest);
        viewPager2 = view.findViewById(R.id.fragment_recipe_view_pager);
        viewPager2.setAdapter(carbonDietTabPagerAdapter);
        viewPager2.setUserInputEnabled(false);
        // 탭 전환 시 프래그먼트 객체 사라짐 방지
        viewPager2.setSaveEnabled(false);

        // tab list 생성
        final List<String> tabElement = Arrays.asList("식단", "활동", "퀘스트", "진행중");
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                Typeface typeface= ResourcesCompat.getFont(getActivity(), R.font.noto_sans_cjk_kr_bold);
                TextView textView = new TextView(getActivity());
                textView.setText(tabElement.get(position));
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setTypeface(typeface);
                tab.setCustomView(textView);
            }
        }).attach();
        tabLayout.setTabTextColors(getResources().getColor(R.color.seco_tooltipgray), getResources().getColor(R.color.seco_green));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.seco_green));
        tabLayout.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

    }

    // user의 free mode 데이터 받아옴 (meal)
    public ContentsDetailData getFreeModeContentsListDataMeal() {
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

        ContentsDetailData meal = new ContentsDetailData();
        meal.setMeal(isFavorites, isEnabled);

        return meal;
    }

    // user의 free mode 데이터 받아옴 (activity)
    public ContentsDetailData getFreeModeContentsListDataActivity() {
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

        ContentsDetailData activity = new ContentsDetailData();
        activity.setActivity(isFavorites, isEnabled);

        return activity;
    }

    // user의 free mode 데이터 받아옴 (quest)
    public ContentsDetailData getFreeModeContentsListDataQuest() {
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


        ContentsDetailData quest = new ContentsDetailData();
        quest.setQuest(isFavorites, isEnabled);

        return quest;
    }
}