package com.ddrx.ddrxfront;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.util.Log;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {

    private RecyclerView card_list_recycler_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private List<CardWarehouseIntro> card_warehouses;


    public CardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        card_list_recycler_view = view.findViewById(R.id.card_list);
        card_list_recycler_view.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        card_list_recycler_view.setLayoutManager(mLayoutManager);

        LoadCardWarehouseDBTask dbTask = new LoadCardWarehouseDBTask(card_warehouses);
        dbTask.execute(getContext());

        return view;
    }

    class LoadCardWarehouseDBTask extends AsyncTask<Context, Integer, Long> {
        private List<CardWarehouseIntro> list;
        private Context context;
        public LoadCardWarehouseDBTask(List<CardWarehouseIntro> list){
            super();
            this.list = list;
        }
        @Override
        protected Long doInBackground(Context... contexts){
            context = contexts[0];
            CardWarehouseDatabase db = CardWarehouseDatabase.getInstance(contexts[0]);
            updateDatabase(db);
            CardWarehouse new_record = new CardWarehouse(1, "唐诗三百首", "肖君彦", R.drawable.r01, "2018-3-15", 0, 300, "", 0, "不学诗，无以言。", "这是著名的唐诗三百首", 0);
            db.getCardWarehouseDAO().insertSingleCardWarehouse(new_record);
            List<CardWarehouse> t_list = db.getCardWarehouseDAO().queryAllCardWarehouse();
            Log.d("w_name", String.valueOf(t_list.get(0).getCover_url()));
            List<CardWarehouseIntro> temp_list = db.getCardWarehouseDAO().queryAllCardWarehouseIntro();
            db.close();

            if (!temp_list.isEmpty())
                list.addAll(temp_list);
            return Long.valueOf(2);
        }

        @Override
        protected void onPostExecute(Long result){
            mAdapter = new CardListAdapter(card_warehouses);
            card_list_recycler_view.setAdapter(mAdapter);
        }

        private void updateDatabase(CardWarehouseDatabase db){

        }
    }

}
