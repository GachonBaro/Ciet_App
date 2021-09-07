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

        // get free mode meal recyclerView
        freeModeMealRecyclerView = viewGroup.findViewById(R.id.fragment_free_mode_meal_recyclerView);

        // init pager adapter
        freeModeContentsListAdapter = new FreeModeContentsListAdapter(0, mealData);

        // init recyclerView
        freeModeMealRecyclerView.setAdapter(freeModeContentsListAdapter);
        freeModeMealRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.LayoutManager layoutManager = freeModeMealRecyclerView.getLayoutManager();

        return viewGroup;
    }
}
