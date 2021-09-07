package com.barofutures.seco.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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

    // Instance 반환 메소드
    public static Fragment_Commerce newInstance() {
        return new Fragment_Commerce();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View 초기화 및 연결
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_commerce, container, false);
        indicator = viewGroup.findViewById(R.id.fragment_commerce_tabs_indicator);
        viewPager = viewGroup.findViewById(R.id.fragment_commerce_viewpager);
        tabLayout = viewGroup.findViewById(R.id.fragment_commerce_tabs);
        spinner = viewGroup.findViewById(R.id.fragment_commerce_item_list_spinner);
        recyclerView=viewGroup.findViewById(R.id.fragment_commerce_list_recycler_view);
        goToMap=viewGroup.findViewById(R.id.fragment_commerce_button_map);

        // Tablayout 설정
        tabLayout.setupWithViewPager(viewPager);

        // Fragment 생성
        commerce_health = new Commerce_Health();
        commerce_dining = new Commerce_Dining();
        commerce_ugly = new Commerce_Ugly();
        commerce_vegan = new Commerce_Vegan();

        // ViewPager Adapter 연결
        commerceFragAdapter = new CommerceFragAdapter(getActivity().getSupportFragmentManager(), 0);
        commerceFragAdapter.addFragment(commerce_dining, "외식");
        commerceFragAdapter.addFragment(commerce_ugly, "못난이");
        commerceFragAdapter.addFragment(commerce_vegan, "비건");
        commerceFragAdapter.addFragment(commerce_health, "헬스");
        viewPager.setAdapter(commerceFragAdapter);
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // Indicator를 위한 Viewpager의 callback 사용
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

        // Spinner 설정
        spinnerAdapter = new ArrayAdapter(getContext(), R.layout.commerce_spinner, getResources().getStringArray(R.array.commerce_item_list_spinner));
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(getContext(), "추천순", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getContext(), "판매순", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(), "낮은 가격순", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getContext(), "높은 가격순", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getContext(), "최신순", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 아이템 리스트 설정
        commerceListAdapter=new CommerceListAdapter();
        recyclerView.setAdapter(commerceListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 지도에서 보기 이벤트 리스너
        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CommerceMapActivity.class);
                startActivity(intent);
            }
        });

        return viewGroup;
    }

    // Indicator 세팅
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
}