package com.barofutures.seco.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.barofutures.seco.fragments.initsurvey.Fragment_initQ_0;
import com.barofutures.seco.fragments.initsurvey.Fragment_initQ_1;
import com.barofutures.seco.fragments.initsurvey.Fragment_initQ_2;
import com.barofutures.seco.fragments.initsurvey.Fragment_initQ_3;
import com.barofutures.seco.fragments.initsurvey.Fragment_initQ_4;
import com.barofutures.seco.fragments.initsurvey.Fragment_initQ_5;
import com.barofutures.seco.fragments.initsurvey.Fragment_initQ_6;
import com.barofutures.seco.fragments.initsurvey.Fragment_initQ_7;
import com.barofutures.seco.fragments.initsurvey.Fragment_initQ_8;

public class InitQnaAdapter extends FragmentStateAdapter {

    public InitQnaAdapter(FragmentActivity activity){
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Fragment_initQ_0();
            case 1:
                return new Fragment_initQ_1();
            case 2:
                return new Fragment_initQ_2();
            case 3:
                return new Fragment_initQ_3();
            case 4:
                return new Fragment_initQ_4();
            case 5:
                return new Fragment_initQ_5();
            case 6:
                return new Fragment_initQ_6();
            case 7:
                return new Fragment_initQ_7();
            case 8:
                return new Fragment_initQ_8();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }
}
