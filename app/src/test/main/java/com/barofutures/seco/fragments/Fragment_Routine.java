package com.barofutures.seco.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.RecipeDetailActivity;
import com.barofutures.seco.adapter.RoutineListAdapter;

public class Fragment_Routine extends Fragment {
    private ViewGroup viewGroup;
//    private RoutineListAdapter routineListAdapter;
//    private RecyclerView recyclerView;
//    private EditText search;

    Button testButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //뷰 초기화 및 연결
        viewGroup=(ViewGroup) inflater.inflate(R.layout.fragment_routine, container, false);
//        recyclerView=viewGroup.findViewById(R.id.routine_list_recycler_view);
//
//        // Routinelistadapter 초기화 및 연결
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        routineListAdapter=new RoutineListAdapter();
//        recyclerView.setAdapter(routineListAdapter);

//       // TODO: 스크롤하면 플로팅 버튼 보이기 / 숨기기
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                if(dy>0){
//                    floatingActionButton.hide();
//                }else if(dy<0){
//                    floatingActionButton.show();
//                }
//            }
//        });

//        // 오버스크롤 비활성화
//        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // TODO: 테스트용 버튼 - 나중에 지우기
        testButton = viewGroup.findViewById(R.id.to_recipe_detail_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
//                intent.putExtra("recycler_position", position);
                getActivity().startActivity(intent);
            }
        });

        return viewGroup;
    }

}