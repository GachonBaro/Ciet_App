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
package com.barofutures.seco.fragments.freemode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.adapter.FreeModeContentsListAdapter;
import com.barofutures.seco.model.ContentsDetailData;

import org.jetbrains.annotations.NotNull;

public class FreeModeMealFragment extends Fragment {
    private RecyclerView freeModeMealRecyclerView;
    private FreeModeContentsListAdapter freeModeContentsListAdapter;
    public ContentsDetailData mealData;

    public FreeModeMealFragment () {

    }
    public FreeModeMealFragment(ContentsDetailData data) {
        mealData = data;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_free_mode_meal, container, false);


        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get free mode meal recyclerView
        freeModeMealRecyclerView = view.findViewById(R.id.fragment_free_mode_meal_recyclerView);

        // init pager adapter
        freeModeContentsListAdapter = new FreeModeContentsListAdapter(0, mealData);

        // init recyclerView
        freeModeMealRecyclerView.setAdapter(freeModeContentsListAdapter);
        freeModeMealRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.LayoutManager layoutManager = freeModeMealRecyclerView.getLayoutManager();
    }
}
