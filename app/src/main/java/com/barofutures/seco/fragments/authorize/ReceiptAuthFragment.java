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
package com.barofutures.seco.fragments.authorize;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barofutures.seco.R;
import com.barofutures.seco.adapter.ReceiptListAdapter;

public class ReceiptAuthFragment extends Fragment {
    private RecyclerView recyclerView;
    private ReceiptListAdapter receiptAuthAdapter;

    public ReceiptAuthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View 초기화 및 연결
        ViewGroup viewGroup=(ViewGroup) inflater.inflate(R.layout.fragment_receipt_auth, container, false);

        // Adapter 초기화 및 연결
        recyclerView=viewGroup.findViewById(R.id.fragment_authorize_receipt_recyclerview);
        receiptAuthAdapter=new ReceiptListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(receiptAuthAdapter);

        return viewGroup;
    }
}