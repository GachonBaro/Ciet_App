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

public class FreeModeQuestFragment extends Fragment {
    private RecyclerView freeModeQuestRecyclerView;
    private FreeModeContentsListAdapter freeModeContentsListAdapter;
    public ContentsDetailData questData;

    public FreeModeQuestFragment () {

    }

    public FreeModeQuestFragment(ContentsDetailData data) {
        questData = data;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_free_mode_quest, container, false);

        // get free mode quest recyclerView
        freeModeQuestRecyclerView = viewGroup.findViewById(R.id.fragment_free_mode_quest_recyclerView);

        // init pager adapter
        freeModeContentsListAdapter = new FreeModeContentsListAdapter(3, questData);

        // init recyclerView
        freeModeQuestRecyclerView.setAdapter(freeModeContentsListAdapter);
        freeModeQuestRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.LayoutManager layoutManager = freeModeQuestRecyclerView.getLayoutManager();

        return viewGroup;
    }
}
