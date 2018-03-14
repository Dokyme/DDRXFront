package com.ddrx.ddrxfront;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/14.
 */

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private List<CardWarehouseIntro> cardWarehouseIntros_list;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView front_page;
        public TextView warehouse_name;
        public TextView add_time;
        public TextView intro;

        public ViewHolder(View view){
            super(view);
            front_page = view.findViewById(R.id.warehouse_front_page);
            warehouse_name = view.findViewById(R.id.warehouse_name);
            add_time = view.findViewById(R.id.warehouse_add_time);
            intro = view.findViewById(R.id.warehouse_intro);
        }
    }

    public CardListAdapter(List<CardWarehouseIntro> list_val){
        cardWarehouseIntros_list = list_val;
    }

    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.warehouse_intro_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        CardWarehouseIntro cardWarehouseIntro = cardWarehouseIntros_list.get(position);
        holder.front_page.setImageResource(cardWarehouseIntro.getImage_id());
        holder.warehouse_name.setText(cardWarehouseIntro.getWarehouse_name());
        holder.add_time.setText(cardWarehouseIntro.getSave_date());
        holder.intro.setText(cardWarehouseIntro.getIntroduction());
    }

    @Override
    public int getItemCount(){
        return cardWarehouseIntros_list.size();
    }
}
