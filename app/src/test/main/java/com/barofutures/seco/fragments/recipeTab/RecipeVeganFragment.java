package com.barofutures.seco.fragments.recipeTab;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.adapter.recipeTab.RecipeVeganAdapter;
import com.barofutures.seco.firebase.RecipeVeganData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecipeVeganFragment extends Fragment {
    RecyclerView recipeVeganRecyclerView;
    RecipeVeganAdapter recipeVeganAdapter;

    // Firebase
    DatabaseReference mDatabase;
    public static ArrayList<String> itemTitle;        // firebase에서 불러온 데이터의 key value(레시피 이름)를 담을 ArrayLsit
    public static ArrayList<RecipeVeganData> itemList;     // firebase에서 불러온 데이터 담을 ArrayList (1 ~ 20n까지의 데이터 들어있음)

    String nextKey;         // firebase에서 다음으로 불러올 key를 저장

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_recipe_vegan, container, false);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // TODO

        mDatabase = FirebaseDatabase.getInstance().getReference();

        itemTitle = new ArrayList<>();
        itemList = new ArrayList<>();

        recipeVeganRecyclerView = view.findViewById(R.id.fragment_recipe_vegan_recycler_view);
//        recipeAdapter = new RecipeAdapter(itemTitle, itemList);

//        Bundle args = getArguments();
//        category = RecipeGeneralFragment.tabElement.get(args.getInt("page_position"));
//        Log.d("123123", "category: " + category);

        // firebase에서 데이터 20개 받아옴
        getRecipeDataFromFirebase();

//        recipeVeganRecyclerView.setAdapter(recipeAdapter);
//        Log.d("123123", "setAdpter");
//        recipeVeganRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        recipeVeganRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // 마지막 item인지 확인 && 이전에 불러온 데이터가 마지막 데이터인지 확인(다 불러와서 더 불러올 데이터 없음)
                if (!(recipeVeganRecyclerView.canScrollVertically(1)) && (itemTitle.size() % 20 == 0)&&dy>0) {
                    getMoreRecipeDataFromFirebase(nextKey, itemTitle.size());
                }
            }
        });
    }

    // firebase에서 레시피 데이터를 20개씩 받아오는 method
    private void getRecipeDataFromFirebase() {
//        // itemTitle, itemList의 마지막 index의 값 삭제 (중복 제거하기 위함)
//        if (itemTitle.size() != 0) {
//            itemTitle.remove(itemTitle.size() - 1);
//            itemList.remove(itemList.size() - 1);
//        }

//        mDatabase = FirebaseDatabase.getInstance().getReference();

        Log.d("12312355", "getRecipeDataFromFirebase");

        mDatabase.child("recipe_dict").child("비건").limitToFirst(21).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    itemTitle.add(dataSnapshot.getKey());
                    Log.d("12312355", String.valueOf(itemTitle.size()));
                    Log.d("123123", String.valueOf(itemTitle.get(itemTitle.size() - 1)));
//                    itemList.add(dataSnapshot.getValue(RecipeGeneralData.class));
                    RecipeVeganData recipeVeganData = dataSnapshot.getValue(RecipeVeganData.class);
                    itemList.add(recipeVeganData);
                    nextKey = dataSnapshot.getKey();
                    Log.d("123123", "nextKey = " + nextKey);
                }

                // itemTitle, itemList의 마지막 index의 값 삭제 (중복 제거하기 위함)
                if ((itemTitle.size() != 0) && (itemTitle.size() % 20 == 1)) {
                    itemTitle.remove(itemTitle.size() - 1);
                    itemList.remove(itemList.size() - 1);
                }

                ///////////////////////////////
                Log.d("123123", "itemList: " + itemList.get(itemList.size()-1).MANUAL01);
                recipeVeganAdapter = new RecipeVeganAdapter(itemTitle, itemList);
                recipeVeganRecyclerView.setAdapter(recipeVeganAdapter);
                recipeVeganRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recipeVeganAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "한식 데이터 20개 불러오기 성공", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("일반식 데이터 로드", "일반식 데이터 불러오기 실패");
            }
        });
    }

    // firebase에서 레시피 데이터를 추가로 20개씩 받아오는 method
    private void getMoreRecipeDataFromFirebase(String startKey, int lastNum) {
//        // itemTitle, itemList의 마지막 index의 값 삭제 (중복 제거하기 위함)
//        if (itemTitle.size() != 0) {
//            itemTitle.remove(itemTitle.size() - 1);
//            itemList.remove(itemList.size() - 1);
//        }

        Log.d("123123", "STARTKEY = " + startKey);
        mDatabase.child("recipe_dict").child("비건").orderByKey().startAt(startKey).limitToFirst(21).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    itemTitle.add(dataSnapshot.getKey());
                    Log.d("123123", String.valueOf(itemTitle.size()));
                    Log.d("123123", "더 불러오기: " + itemTitle.get(itemTitle.size() - 1));
//                    itemList.add(dataSnapshot.getValue(RecipeVeganData.class));
                    RecipeVeganData recipeVeganData = dataSnapshot.getValue(RecipeVeganData.class);
                    itemList.add(recipeVeganData);
                    nextKey = dataSnapshot.getKey();
                    Log.d("123123", "nextKey = " + nextKey);
                }



                // itemTitle, itemList의 마지막 index의 값 삭제 (중복 제거하기 위함)
                if ((itemTitle.size() != 0) && (itemTitle.size() - lastNum == 21)) {
                    itemTitle.remove(itemTitle.size() - 1);
                    itemList.remove(itemList.size() - 1);
                }

                // 데이터 갱신
                recipeVeganAdapter.notifyDataSetChanged();

                Toast.makeText(getActivity(), "한식 데이터 더 불러오기 성공", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("일반식 데이터 로드", "일반식 데이터 불러오기 실패");
            }
        });

    }
}
