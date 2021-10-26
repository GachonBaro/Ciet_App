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
import com.barofutures.seco.adapter.recipeTab.RecipeTabPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class Fragment_Recipe extends Fragment {
//    private EditText searchBar;                     // 검색창

    private TabLayout tabLayout;                    // 레시피 L1 탭 TabLayout (일반식, 비건)

    private RecipeTabPagerAdapter recipeTabPagerAdapter;        // 레시피 L1 탭 adapter (일반식, 비건)
    private ViewPager2 viewPager2;                              // 레시피 L1 탭 viewPager (일번식, 비건)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_recipe, container, false);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        searchBar = view.findViewById(R.id.fragment_recipe_searchbar_edit_text);
        tabLayout = view.findViewById(R.id.fragment_recipe_tabs);
        recipeTabPagerAdapter = new RecipeTabPagerAdapter(this);
        viewPager2 = view.findViewById(R.id.fragment_recipe_view_pager);
        viewPager2.setAdapter(recipeTabPagerAdapter);
        viewPager2.setUserInputEnabled(false);
        // 탭 전환 시 프래그먼트 객체 사라짐 방지
        viewPager2.setSaveEnabled(false);

        // tab list 생성
        final List<String> tabElement = Arrays.asList("일반식", "비건");
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
}
