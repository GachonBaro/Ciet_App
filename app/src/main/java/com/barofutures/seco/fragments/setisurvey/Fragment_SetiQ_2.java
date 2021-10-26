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
package com.barofutures.seco.fragments.setisurvey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.adapter.SetiSurveyAdapter;

public class Fragment_SetiQ_2 extends Fragment {

    private RecyclerView setiSurveyRecyclerView;
    private SetiSurveyAdapter setiSurveyAdapter;

    public Fragment_SetiQ_2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // init ViewGroup
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_setiq_2, container, false);

        // get SETI survey recyclerView
        setiSurveyRecyclerView = viewGroup.findViewById(R.id.seti_survey_page_2_recyclerView);

        // init adapter
        setiSurveyAdapter = new SetiSurveyAdapter(2);

        // init recyclerView
        setiSurveyRecyclerView.setAdapter(setiSurveyAdapter);
        setiSurveyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.LayoutManager layoutManager = setiSurveyRecyclerView.getLayoutManager();

        return viewGroup;
    }
}