package com.barofutures.seco.fragments.recipeTab;

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
import com.barofutures.seco.adapter.recipeTab.RecipeGeneralTabPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class RecipeGeneralFragment extends Fragment {
    // tab list
    public static final List<String> tabElement = Arrays.asList("한식", "양식", "일식", "중식", "분식", "퓨전", "아시안", "샐러드", "디저트");

    private TabLayout tabLayout;                // 레시피 L1(일반식)-> L2(카테고리: 한식, 양식 등)

    private RecipeGeneralTabPagerAdapter recipeGeneralTabPagerAdapter;      // 레시피 L2 탭 adapter (읿반식 -> 카테고리)
    private ViewPager2 viewPager2;                                          // 레시피 L2 탭 viewPager (읿반식 -> 카테고리)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_recipe_general, container, false);



        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tabLayout = view.findViewById(R.id.fragment_recipe_general_tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        recipeGeneralTabPagerAdapter = new RecipeGeneralTabPagerAdapter(this, tabElement.size());
        viewPager2 = view.findViewById(R.id.fragment_recipe_general_view_pager);
        viewPager2.setAdapter(recipeGeneralTabPagerAdapter);
        viewPager2.setUserInputEnabled(true);
        // 탭 전환 시 프래그먼트 객체 사라짐 방지
        viewPager2.setSaveEnabled(false);

        // tab list 생성
        final List<String> tabElement = Arrays.asList("한식", "양식", "일식", "중식", "분식", "퓨전", "아시안", "샐러드", "디저트");
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(getActivity());             //////// getContext()...?? getActivity()...?
                Typeface typeface= ResourcesCompat.getFont(getActivity(), R.font.noto_sans_cjk_kr_bold);
                textView.setText(tabElement.get(position));
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setTypeface(typeface);
                tab.setCustomView(textView);
            }
        }).attach();

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.seco_green));
        tabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.seco_green),ContextCompat.getColor(getActivity(), R.color.seco_tooltipgray));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

    }

}
