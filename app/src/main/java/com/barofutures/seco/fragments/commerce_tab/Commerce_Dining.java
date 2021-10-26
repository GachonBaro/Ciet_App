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
package com.barofutures.seco.fragments.commerce_tab;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.barofutures.seco.R;
import com.barofutures.seco.model.CommerceItem;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class Commerce_Dining extends Fragment {
    private ViewGroup viewGroup;
    private CircleImageView image1, image2, image3, image4;
    private TextView name1, name2, name3, name4;

    public Commerce_Dining() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View 초기화 및 연결
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_commerce_dining, container, false);
        image1 = viewGroup.findViewById(R.id.fragment_commerce_tab_dining_image_1);
        image2 = viewGroup.findViewById(R.id.fragment_commerce_tab_dining_image_2);
        image3 = viewGroup.findViewById(R.id.fragment_commerce_tab_dining_image_3);
        image4 = viewGroup.findViewById(R.id.fragment_commerce_tab_dining_image_4);
        name1 = viewGroup.findViewById(R.id.fragment_commerce_tab_dining_text_1);
        name2 = viewGroup.findViewById(R.id.fragment_commerce_tab_dining_text_2);
        name3 = viewGroup.findViewById(R.id.fragment_commerce_tab_dining_text_3);
        name4 = viewGroup.findViewById(R.id.fragment_commerce_tab_dining_text_4);

        // 이미지 불러오기
        Glide.with(getContext())
                .load(CommerceItem.commerceDiningTabItemList[0][0])
                .into(image1);
        Glide.with(getContext())
                .load(CommerceItem.commerceDiningTabItemList[1][0])
                .into(image2);
        Glide.with(getContext())
                .load(CommerceItem.commerceDiningTabItemList[2][0])
                .into(image3);
        Glide.with(getContext())
                .load(CommerceItem.commerceDiningTabItemList[3][0])
                .into(image4);

        // 브랜드 이름 설정
        name1.setText(CommerceItem.commerceDiningTabItemList[0][1]);
        name2.setText(CommerceItem.commerceDiningTabItemList[1][1]);
        name3.setText(CommerceItem.commerceDiningTabItemList[2][1]);
        name4.setText(CommerceItem.commerceDiningTabItemList[3][1]);

        return viewGroup;
    }
}