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

public class Fragment_SetiQ_1 extends Fragment {

    private RecyclerView setiSurveyRecyclerView;
    private SetiSurveyAdapter setiSurveyAdapter;

    public Fragment_SetiQ_1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // init ViewGroup
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_setiq_1, container, false);

        // get SETI survey recyclerView
        setiSurveyRecyclerView = viewGroup.findViewById(R.id.seti_survey_page_1_recyclerView);

        // init adapter
        setiSurveyAdapter = new SetiSurveyAdapter(1);

        // init recyclerView
        setiSurveyRecyclerView.setAdapter(setiSurveyAdapter);
        setiSurveyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.LayoutManager layoutManager = setiSurveyRecyclerView.getLayoutManager();

        return viewGroup;
    }
}