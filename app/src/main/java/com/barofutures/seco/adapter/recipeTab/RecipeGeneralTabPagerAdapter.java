package com.barofutures.seco.adapter.recipeTab;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.barofutures.seco.fragments.recipeTab.RecipeGeneralAsianFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralBunsikFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralChineseFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralDessertFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralFusionFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralJapaneseFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralKoreanFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralSaladFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeGeneralWesternFragment;

public class RecipeGeneralTabPagerAdapter extends FragmentStateAdapter {
    private static int tabElementNum;
    private Fragment fragment;

    public RecipeGeneralTabPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public RecipeGeneralTabPagerAdapter(@NonNull Fragment fragment, int tabElementNum) {
        super(fragment);
        this.tabElementNum = tabElementNum;         // L2의 탭 개수
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            fragment = new RecipeGeneralKoreanFragment();
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else if (position == 1) {
            fragment = new RecipeGeneralWesternFragment();
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else if (position == 2) {
            fragment = new RecipeGeneralJapaneseFragment();
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else if (position == 3) {
            fragment = new RecipeGeneralChineseFragment();
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else if (position == 4) {
            fragment = new RecipeGeneralBunsikFragment();
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else if (position == 5) {
            fragment = new RecipeGeneralFusionFragment();
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else if (position == 6) {
            fragment = new RecipeGeneralAsianFragment();
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else if (position == 7) {
            fragment = new RecipeGeneralSaladFragment();
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else if (position == 8) {
            fragment = new RecipeGeneralDessertFragment();
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else {
            Log.e("RecipeGeneralTabPagerAdapter", "[ERROR] RecipeGeneralTabPagerAdapter: createFragment() error");
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return tabElementNum;
    }
}
