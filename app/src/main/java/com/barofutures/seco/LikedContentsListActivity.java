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