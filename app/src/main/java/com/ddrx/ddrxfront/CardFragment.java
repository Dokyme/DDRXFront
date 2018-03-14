package com.ddrx.ddrxfront;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link FragmentActivity} subclass.
 */
public class CardFragment extends FragmentActivity {

    private RecyclerView card_list_recycler_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public CardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_card);
        card_list_recycler_view = findViewById(R.id.card_list);

        card_list_recycler_view.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        card_list_recycler_view.setLayoutManager(mLayoutManager);

        mAdapter = new CardListAdapter();
        card_list_recycler_view.setAdapter(mAdapter);
    }

}
