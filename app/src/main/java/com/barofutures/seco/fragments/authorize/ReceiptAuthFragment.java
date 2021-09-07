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