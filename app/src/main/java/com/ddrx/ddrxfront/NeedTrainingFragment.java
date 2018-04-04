package com.ddrx.ddrxfront;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ddrx.ddrxfront.Controller.InitUpdateDatabase;
import com.ddrx.ddrxfront.Controller.NeedTrainingController;
import com.ddrx.ddrxfront.Model.NeedTrainingItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NeedTrainingFragment extends Fragment {

    private class MyHandler extends Handler {

        public MyHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            swipeRefreshLayout.setRefreshing(false);
            switch (msg.what) {
                case NeedTrainingController.EMPTY_LIST:
                    Snackbar.make(recyclerView, "空空如也的训练场", Snackbar.LENGTH_SHORT).show();
                    break;
                case NeedTrainingController.UPDATE_UI:
                    needTrainingItemList = (List<NeedTrainingItem>) msg.obj;
                    NeedTrainingAdapter adapter = new NeedTrainingAdapter((List<NeedTrainingItem>) msg.obj);
                    recyclerView.setAdapter(adapter);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    break;
            }
        }
    }

    private List<NeedTrainingItem> needTrainingItemList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NeedTrainingController controller;

    public NeedTrainingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("ddrx", "onHiddenChangedNeedTrainingFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.updateNeedTrainingFromDB();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new NeedTrainingController(new MyHandler(), getContext());
        needTrainingItemList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_need_training, container, false);
        recyclerView = view.findViewById(R.id.need_training_list);
        swipeRefreshLayout = view.findViewById(R.id.need_trainning_fragment_swipe_refresh);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                controller.updateNeedTrainingFromDB();
            }
        });

        return view;
    }

}
