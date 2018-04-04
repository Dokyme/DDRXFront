package com.ddrx.ddrxfront;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import com.ddrx.ddrxfront.Model.CardModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dokym on 2018/4/3.
 */

public class Fragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecycleview;
    private RecyclerView.LayoutManager mLayoutManager;
    private ModelListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

//        mRecycleview = view.findViewById(R.id.recycleview);
//        mRecycleview.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        mRecycleview.setLayoutManager(mLayoutManager);
//
//        List<CardModel> temp = new ArrayList<>();
//        temp.add(new CardModel(0, "", "", 0, "", "", 0, 0, "", "", 0));
//        mAdapter = new ModelListAdapter(temp);
//        mRecycleview.setAdapter(mAdapter);

        return view;
    }
}
