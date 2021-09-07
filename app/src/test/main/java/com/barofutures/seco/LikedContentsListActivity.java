package com.barofutures.seco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;

import com.barofutures.seco.adapter.LikedContentsAdapter;

public class LikedContentsListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LikedContentsAdapter likedContentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_contents_list);

        // View 연결 및 초기화
        recyclerView=findViewById(R.id.activity_liked_contents_list_recyclerview);
        likedContentsAdapter=new LikedContentsAdapter();
        recyclerView.setAdapter(likedContentsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}