package com.barofutures.seco.adapter.recipeTab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.barofutures.seco.fragments.recipeTab.RecipeGeneralFragment;
import com.barofutures.seco.fragments.recipeTab.RecipeVeganFragment;

public class RecipeTabPagerAdapter extends FragmentStateAdapter {
    public RecipeTabPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            Fragment fragment = new RecipeGeneralFragment();
            return fragment;
        } else if (position == 1) {
            Fragment fragment = new RecipeVeganFragment();
            return fragment;
        } else {
            return null;
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
