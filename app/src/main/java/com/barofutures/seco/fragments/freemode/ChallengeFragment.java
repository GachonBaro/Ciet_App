package com.barofutures.seco.fragments.freemode;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.adapter.ChallengeRecommendationListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ChallengeFragment extends Fragment {
    private ConstraintLayout offProgressLayout;

    private RecyclerView offRecommendationRecyclerView;
    private ChallengeRecommendationListAdapter recommendationListAdapter;

    public ChallengeFragment() {

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_challenge, container, false);

        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        offProgressLayout = view.findViewById(R.id.fragment_challenge_off_progress_layout);
        offRecommendationRecyclerView = view.findViewById(R.id.fragment_challenge_off_recommendation_recycler_view);

        offProgressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "챌린지 생성 화면으로 이동", Toast.LENGTH_SHORT).show();
                // TODO: 챌린지 생성으로 이동
            }
        });

        loadChallengeRecommendationData();
    }

    // Firebase(realtime)에서 난이도별 추천 챌린지 데이터 받아오기
    private void loadChallengeRecommendationData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
//        Map<String, Object> data = new HashMap<>();
//        data.put("초급", "자전거|플로깅");
        databaseReference.child("challenge").child("recommendations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> data = new HashMap<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    data.put(dataSnapshot.getKey(), dataSnapshot.getValue());
                }
                Log.d("ChallengeFragment", data.get("초급").toString());

                recommendationListAdapter = new ChallengeRecommendationListAdapter(data);
                offRecommendationRecyclerView.setAdapter(recommendationListAdapter);
                offRecommendationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recommendationListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChallengeFragment", "일반식 데이터 불러오기 실패");
            }
        });
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("ChallengeFragment", "Error getting data", task.getException());
//                } else {
//                    Log.d("ChallengeFragment", String.valueOf(task.getResult().getValue()));
//                    Map<String, Object> data = new HashMap<>();
//                    data = task.getResult().getValue();
//                }
//            }
//        });
    }
}