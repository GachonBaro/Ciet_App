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
package com.barofutures.seco.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.barofutures.seco.fragments.setisurvey.Fragment_SetiQ_0;
import com.barofutures.seco.fragments.setisurvey.Fragment_SetiQ_1;
import com.barofutures.seco.fragments.setisurvey.Fragment_SetiQ_2;
import com.barofutures.seco.fragments.setisurvey.Fragment_SetiQ_3;
import com.barofutures.seco.fragments.setisurvey.Fragment_SetiQ_4;

public class SetiPageAdapter extends FragmentStateAdapter {
    public SetiPageAdapter(FragmentActivity activity){ super(activity); }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch (position){
            case 0:
                return new Fragment_SetiQ_0();
            case 1:
                return new Fragment_SetiQ_1();
            case 2:
                return new Fragment_SetiQ_2();
            case 3:
                return new Fragment_SetiQ_3();
            case 4:
                return new Fragment_SetiQ_4();
            default:
                return new Fragment_SetiQ_0();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
