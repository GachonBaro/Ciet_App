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

public class FreeModeActivityFragment extends Fragment {
    private RecyclerView freeModeActivityRecyclerView;
    private FreeModeContentsListAdapter freeModeContentsListAdapter;
    public ContentsDetailData activityData;

    public FreeModeActivityFragment () {

    }

    public FreeModeActivityFragment (ContentsDetailData data) {
        activityData = data;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_free_mode_activity, container, false);

        // get free mode activity recyclerView
        freeModeActivityRecyclerView = viewGroup.findViewById(R.id.fragment_free_mode_activity_recyclerView);

        // init recycler adapter
        freeModeContentsListAdapter = new FreeModeContentsListAdapter(1, activityData);

        // init recyclerView
        freeModeActivityRecyclerView.setAdapter(freeModeContentsListAdapter);
        freeModeActivityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        freeModeContentsListAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = freeModeActivityRecyclerView.getLayoutManager();

        return viewGroup;
    }
}
