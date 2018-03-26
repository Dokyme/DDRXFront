package com.ddrx.ddrxfront;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddrx.ddrxfront.Model.CardWarehouseIntro;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/14.
 */

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private List<CardWarehouseIntro> cardWarehouseIntros_list;
    private Handler handler;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public View intro_view;
        public ImageView front_page;
        public TextView warehouse_name;
        public TextView add_time;
        public TextView intro;

        public ViewHolder(View view){
            super(view);
            intro_view = view;
            front_page = view.findViewById(R.id.warehouse_front_page);
            warehouse_name = view.findViewById(R.id.warehouse_name);
            add_time = view.findViewById(R.id.warehouse_add_time);
            intro = view.findViewById(R.id.warehouse_intro);
        }
    }

    public CardListAdapter(List<CardWarehouseIntro> list_val, Handler handler){
        cardWarehouseIntros_list = list_val;
        this.handler = handler;
    }

    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.warehouse_intro_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.intro_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = WarehouseActivity.UPDATE_FRAGMENT;
                message.arg1 = cardWarehouseIntros_list.get(holder.getAdapterPosition()).getWarehouse_id();
                handler.sendMessage(message);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        CardWarehouseIntro cardWarehouseIntro = cardWarehouseIntros_list.get(position);
        holder.front_page.setImageBitmap(BitmapFactory.decodeFile(cardWarehouseIntro.getImage_url()));
        holder.warehouse_name.setText(cardWarehouseIntro.getWarehouse_name());
        holder.add_time.setText(cardWarehouseIntro.getSave_date());
        holder.intro.setText(cardWarehouseIntro.getIntroduction());
    }

    @Override
    public int getItemCount(){
        return cardWarehouseIntros_list.size();
    }
}
