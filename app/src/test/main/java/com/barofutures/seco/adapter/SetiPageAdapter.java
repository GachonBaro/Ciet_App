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
