package com.ddrx.ddrxfront;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ddrx.ddrxfront.Controller.UpdateCardFragmentController;
import com.ddrx.ddrxfront.Model.CardWarehouseIntro;
import com.ddrx.ddrxfront.Controller.InitUpdateDatabase;
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {

    private RecyclerView card_list_recycler_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CardWarehouseIntro> card_warehouses;
    private UpdateCardFragmentController controller;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;
    private MyHandler handler;

    public static final int EMPTY_LIST = 2;
    public static final int UPDATE_UI = 3;

    private static class MyHandler extends Handler{

        private final WeakReference<CardFragment> mFragment;

        public MyHandler(CardFragment fragment){
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case InitUpdateDatabase.NETWORK_ERROR:{
                    mFragment.get().progressDialog.dismiss();
                    Toast.makeText(mFragment.get().getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
                    break;
                }
                case InitUpdateDatabase.UPDATE_WAREHOUSE_SUCCESS:{
                    mFragment.get().controller.getDataListFromDB();
                    break;
                }
                case EMPTY_LIST:{
                    mFragment.get().progressDialog.dismiss();
                    Snackbar.make(mFragment.get().card_list_recycler_view, "空空如也的仓库", Snackbar.LENGTH_SHORT).setAction("创建新仓库", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mFragment.get().getActivity(), AddNewWarehouseActivity.class);
                            mFragment.get().getActivity().startActivity(intent);
                        }
                    });
                    break;
                }
                case UPDATE_UI:{
                    mFragment.get().card_warehouses = (List<CardWarehouseIntro>)msg.obj;
                    RecyclerView.Adapter mAdapter = new CardListAdapter(mFragment.get().card_warehouses);
                    mFragment.get().card_list_recycler_view.setAdapter(mAdapter);
                    mFragment.get().progressDialog.dismiss();
                }
            }
        }
    }


    public CardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        card_warehouses = new ArrayList<>();
        handler = new MyHandler(this);
        controller = new UpdateCardFragmentController(handler, getContext());
        controller.getDataListFromDB();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("获取仓库信息中");
        progressDialog.setMessage("等待中...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    public void onStart(){
        super.onStart();
        controller.getDataListFromDB();
        progressDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        card_list_recycler_view = view.findViewById(R.id.card_list);
        swipeRefreshLayout = view.findViewById(R.id.card_fragment_swipe_refresh);
        card_list_recycler_view.setHasFixedSize(true);
        swipeRefreshLayout = view.findViewById(R.id.card_fragment_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                InitUpdateDatabase.updateCardWarehouseDatabase(getContext(), handler, OKHttpClientWrapper.Companion.getInstance(getActivity()));
            }
        });

        mLayoutManager = new LinearLayoutManager(getContext());
        card_list_recycler_view.setLayoutManager(mLayoutManager);
        return view;
    }

}
