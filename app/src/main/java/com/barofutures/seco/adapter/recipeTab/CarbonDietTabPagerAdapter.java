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
package com.barofutures.seco.adapter.recipeTab;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.barofutures.seco.fragments.freemode.ChallengeFragment;
import com.barofutures.seco.fragments.freemode.FreeModeActivityFragment;
import com.barofutures.seco.fragments.freemode.FreeModeMealFragment;
import com.barofutures.seco.fragments.freemode.FreeModeQuestFragment;
import com.barofutures.seco.model.ContentsDetailData;

public class CarbonDietTabPagerAdapter extends FragmentStateAdapter {
    private Fragment fragment;

    public static ContentsDetailData contentsDetailDataMeal;
    public static ContentsDetailData contentsDetailDataActivity;
    public static ContentsDetailData contentsDetailDataQuest;

    public CarbonDietTabPagerAdapter(@NonNull Fragment fragment, ContentsDetailData contentsDetailDataMeal, ContentsDetailData contentsDetailDataActivity, ContentsDetailData contentsDetailDataQuest) {
        super(fragment);
        this.contentsDetailDataMeal = contentsDetailDataMeal;
        this.contentsDetailDataActivity = contentsDetailDataActivity;
        this.contentsDetailDataQuest = contentsDetailDataQuest;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            fragment = new FreeModeMealFragment(contentsDetailDataMeal);
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else if (position == 1) {
            fragment = new FreeModeActivityFragment(contentsDetailDataActivity);
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else if (position == 2) {
            fragment = new FreeModeQuestFragment(contentsDetailDataQuest);
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else if (position == 3) {
            fragment = new ChallengeFragment();
            Bundle args = new Bundle();
            args.putInt("page_position", position);
            fragment.setArguments(args);
            return fragment;
        } else {
            Log.e("CarbonDietTabPagerAdapter", "[ERROR] CarbonDietTabPagerAdapter: createFragment() error");
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
