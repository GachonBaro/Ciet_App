package com.barofutures.seco.adapter.recipeTab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.barofutures.seco.fragments.freemode.FreeModeActivityFragment;
import com.barofutures.seco.fragments.freemode.FreeModeLikeFragment;
import com.barofutures.seco.fragments.freemode.FreeModeMealFragment;
import com.barofutures.seco.fragments.freemode.FreeModeQuestFragment;
import com.barofutures.seco.model.ContentsDetailData;

public class CarbonDietTabPagerAdapter extends FragmentStateAdapter {
    public static ContentsDetailData contentsDetailDataMeal;
    public static ContentsDetailData contentsDetailDataActivity;
    public static ContentsDetailData contentsDetailDataQuest;
    public static ContentsDetailData contentsDetailDataFavorites;

    public CarbonDietTabPagerAdapter(@NonNull Fragment fragment, ContentsDetailData contentsDetailDataMeal, ContentsDetailData contentsDetailDataActivity, ContentsDetailData contentsDetailDataQuest, ContentsDetailData contentsDetailDataFavorites) {
        super(fragment);
        this.contentsDetailDataMeal = contentsDetailDataMeal;
        this.contentsDetailDataActivity = contentsDetailDataActivity;
        this.contentsDetailDataQuest = contentsDetailDataQuest;
        this.contentsDetailDataFavorites = contentsDetailDataFavorites;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            Fragment fragment = new FreeModeMealFragment(contentsDetailDataMeal);
//            Bundle args = new Bundle();
            // Our object is just an integer :-P
//            args.putInt(fragment.ARG_OBJECT, position + 1);
//            fragment.setArguments(args);
            return fragment;
        } else if (position == 1) {
            Fragment fragment = new FreeModeActivityFragment(contentsDetailDataActivity);
//            Bundle args = new Bundle();
            // Our object is just an integer :-P
//            args.putInt(fragment.ARG_OBJECT, position + 1);
//            fragment.setArguments(args);
            return fragment;
        } else if (position == 2) {
            Fragment fragment = new FreeModeQuestFragment(contentsDetailDataQuest);
//            Bundle args = new Bundle();
            // Our object is just an integer :-P
//            args.putInt(fragment.ARG_OBJECT, position + 1);
//            fragment.setArguments(args);
            return fragment;
        } else if (position == 3) {
            Fragment fragment = new FreeModeLikeFragment(contentsDetailDataFavorites);
//            Bundle args = new Bundle();
            // Our object is just an integer :-P
//            args.putInt(fragment.ARG_OBJECT, position + 1);
//            fragment.setArguments(args);
            return fragment;
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
