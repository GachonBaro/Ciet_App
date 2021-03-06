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
package com.barofutures.seco.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.barofutures.seco.R;
import com.barofutures.seco.adapter.recipeTab.CarbonDietTabPagerAdapter;
import com.barofutures.seco.model.ContentsDetailData;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_CarbonDiet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_CarbonDiet extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TabLayout tabLayout;         // Carbon Diet 탭 TabLayout (식단, 활동, 퀘스트, 진행중)

    private CarbonDietTabPagerAdapter carbonDietTabPagerAdapter;        // Carbon Diet 탭 adapter (식단, 활동, 퀘스트, 진행중)
    private ViewPager2 viewPager2;                              // Carbon Diet viewPager (식단, 활동, 퀘스트, 진행중)

    public static ContentsDetailData contentsDetailDataMeal;
    public static ContentsDetailData contentsDetailDataActivity;
    public static ContentsDetailData contentsDetailDataQuest;

    public Fragment_CarbonDiet() {
        // Required empty public constructor
    }

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

        // TabLayout에 viewPager 연결
        tabLayout = (TabLayout) view.findViewById(R.id.fragment_carbon_diet_tabs);
        carbonDietTabPagerAdapter = new CarbonDietTabPagerAdapter(this, contentsDetailDataMeal, contentsDetailDataActivity, contentsDetailDataQuest);
        viewPager2 = view.findViewById(R.id.fragment_recipe_view_pager);
        viewPager2.setAdapter(carbonDietTabPagerAdapter);
        viewPager2.setUserInputEnabled(false);
        // 탭 전환 시 프래그먼트 객체 사라짐 방지
        viewPager2.setSaveEnabled(false);

        // tab list 생성
        final List<String> tabElement = Arrays.asList("식단", "활동", "퀘스트", "챌린지");
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
        ContentsDetailData meal = new ContentsDetailData();
        meal.setMeal();

        return meal;
    }

    // user의 free mode 데이터 받아옴 (activity)
    public ContentsDetailData getFreeModeContentsListDataActivity() {
        ContentsDetailData activity = new ContentsDetailData();
        activity.setActivity();

        return activity;
    }

    // user의 free mode 데이터 받아옴 (quest)
    public ContentsDetailData getFreeModeContentsListDataQuest() {
        ContentsDetailData quest = new ContentsDetailData();
        quest.setQuest();

        return quest;
    }
}