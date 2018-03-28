package com.ddrx.ddrxfront;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ddrx.ddrxfront.Controller.UpdateCardFragmentController;
import com.ddrx.ddrxfront.Controller.UpdateModelFragmentController;
import com.ddrx.ddrxfront.Model.CardWarehouseIntro;
import com.ddrx.ddrxfront.Model.ModelIntro;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ModelFragment extends Fragment {


    private RecyclerView model_list_recycler_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ModelIntro> models;
    private ProgressBar progressBar;
    private UpdateCardFragmentController controller;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView hint;

    public static final int NETWORK_ERROR = 1;
    public static final int EMPTY_LIST = 2;
    public static final int UPDATE_UI = 3;

    private static class MyHandler extends Handler {

        private final WeakReference<ModelFragment> mFragment;

        public MyHandler(ModelFragment fragment){
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case NETWORK_ERROR:{
                    mFragment.get().progressBar.setVisibility(View.GONE);
                    mFragment.get().hint.setText("阿哦！网络错误！");
                    mFragment.get().model_list_recycler_view.setVisibility(View.GONE);
                    mFragment.get().hint.setVisibility(View.VISIBLE);
                    break;
                }
                case EMPTY_LIST:{
                    mFragment.get().hint.setText("空空如也的卡片仓库！");
                    Log.d("Show", "Empty_list");
                    mFragment.get().model_list_recycler_view.clearAnimation();
                    mFragment.get().model_list_recycler_view.setVisibility(View.GONE);
                    mFragment.get().hint.setVisibility(View.VISIBLE);
                    break;
                }
                case UPDATE_UI:{
                    mFragment.get().models = (List<ModelIntro>)msg.obj;
                    //RecyclerView.Adapter mAdapter = new CardListAdapter(mFragment.get().card_warehouses, (WarehouseActivity)(mFragment.get().getActivity()).getHandler());
                    //mFragment.get().card_list_recycler_view.setAdapter(mAdapter);
                    mFragment.get().model_list_recycler_view.setVisibility(View.VISIBLE);
                    mFragment.get().progressBar.setVisibility(View.GONE);
                    mFragment.get().hint.setVisibility(View.GONE);
                }
            }
        }
    }


    public ModelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        models = new ArrayList<>();
        ModelFragment.MyHandler handler = new ModelFragment.MyHandler(this);
        controller = new UpdateModelFragmentController(handler, getContext());
    }

    @Override
    public void onStart(){
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isAvailable()){
            //controller.getDataListFromNetwork();
            controller.getDataListFromDB();
        }
        else{
            Log.e("Here","aa");
            controller.getDataListFromDB();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_model, container, false);
        model_list_recycler_view = view.findViewById(R.id.card_list);
        progressBar = view.findViewById(R.id.model_fragment_processBar);
        hint = view.findViewById(R.id.model_fragment_hint);
        swipeRefreshLayout = view.findViewById(R.id.model_fragment_swipe_refresh);
        hint.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        model_list_recycler_view.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        model_list_recycler_view.setLayoutManager(mLayoutManager);
        return view;
    }

}
