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

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.barofutures.seco.CommerceMapActivity;
import com.barofutures.seco.R;
import com.barofutures.seco.adapter.CommerceFragAdapter;
import com.barofutures.seco.adapter.CommerceListAdapter;
import com.barofutures.seco.fragments.commerce_tab.Commerce_Dining;
import com.barofutures.seco.fragments.commerce_tab.Commerce_Health;
import com.barofutures.seco.fragments.commerce_tab.Commerce_Ugly;
import com.barofutures.seco.fragments.commerce_tab.Commerce_Vegan;
import com.google.android.material.tabs.TabLayout;

public class Fragment_Commerce extends Fragment {
    private ViewGroup viewGroup;
    private TabLayout tabLayout;
    private LinearLayout indicator;
    private ViewPager viewPager;
    private Commerce_Health commerce_health;
    private Commerce_Ugly commerce_ugly;
    private Commerce_Vegan commerce_vegan;
    private Commerce_Dining commerce_dining;
    private CommerceFragAdapter commerceFragAdapter;
    private CommerceListAdapter commerceListAdapter;
    private ArrayAdapter spinnerAdapter;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private Button goToMap;

    LinearLayout ll;

    // Instance ?????? ?????????
    public static Fragment_Commerce newInstance() {
        return new Fragment_Commerce();
    }

    @Override
    public void onStart() {
        super.onStart();
        setDialog();
    }

    @Override
    public void onPause() {
        super.onPause();
        removeDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View ????????? ??? ??????
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_commerce, container, false);
        indicator = viewGroup.findViewById(R.id.fragment_commerce_tabs_indicator);
        viewPager = viewGroup.findViewById(R.id.fragment_commerce_viewpager);
        tabLayout = viewGroup.findViewById(R.id.fragment_commerce_tabs);
        spinner = viewGroup.findViewById(R.id.fragment_commerce_item_list_spinner);
        recyclerView=viewGroup.findViewById(R.id.fragment_commerce_list_recycler_view);
        goToMap=viewGroup.findViewById(R.id.fragment_commerce_button_map);

        // Tablayout ??????
        tabLayout.setupWithViewPager(viewPager);

        // Fragment ??????
        commerce_health = new Commerce_Health();
        commerce_dining = new Commerce_Dining();
        commerce_ugly = new Commerce_Ugly();
        commerce_vegan = new Commerce_Vegan();

        // ViewPager Adapter ??????
        commerceFragAdapter = new CommerceFragAdapter(getActivity().getSupportFragmentManager(), 0);
        commerceFragAdapter.addFragment(commerce_dining, "Meal Saving");
        commerceFragAdapter.addFragment(commerce_ugly, "?????????");
        commerceFragAdapter.addFragment(commerce_vegan, "??????");
        commerceFragAdapter.addFragment(commerce_health, "??????");
        viewPager.setAdapter(commerceFragAdapter);
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // Indicator??? ?????? Viewpager??? callback ??????
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUpIndicator(4);

        // Spinner ??????
        spinnerAdapter = new ArrayAdapter(getContext(), R.layout.commerce_spinner, getResources().getStringArray(R.array.commerce_item_list_spinner));
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        Toast.makeText(getContext(), "?????????", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
//                        Toast.makeText(getContext(), "?????????", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
//                        Toast.makeText(getContext(), "?????? ?????????", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
//                        Toast.makeText(getContext(), "?????? ?????????", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
//                        Toast.makeText(getContext(), "?????????", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // ????????? ????????? ??????
        commerceListAdapter=new CommerceListAdapter();
        recyclerView.setAdapter(commerceListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // ???????????? ?????? ????????? ?????????
        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CommerceMapActivity.class);
                startActivity(intent);
            }
        });

        return viewGroup;
    }

    // Indicator ??????
    private void setUpIndicator(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getActivity());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            indicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = indicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) indicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg_indicator_inactive));
            }
        }
    }

    // dialog
    private void setDialog() {

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ll = (LinearLayout)inflater.inflate(R.layout.dialog_transparent, null);

        ll.setBackgroundColor(Color.parseColor("#99000000"));

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
        param.setMargins(0, getStatusBarHeight() + getActionBarHeight(), 0, getNavigationBarHeight() + 220);
        getActivity().addContentView(ll, param);

    }

    private void removeDialog() {
//        @SuppressLint("ResourceType") LinearLayout ll = (LinearLayout) getActivity().findViewById(R.layout.dialog_transparent);
        ((ViewManager) ll.getParent()).removeView(ll);
    }

    //status bar??? ?????? ??????
    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }

    // navigation bar ?????? ??????
    private int getNavigationBarHeight() {
        int navigationBarHeight = 0;
        int resId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resId > 0) {
            navigationBarHeight = getResources().getDimensionPixelSize(resId);
        }
        return navigationBarHeight;
    }

    // actionBar ?????? ??????
    private int getActionBarHeight() {
        int actionBarHeight = 0;
        final TypedArray styledAttributes = getActivity().getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize }
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionBarHeight;
    }
}