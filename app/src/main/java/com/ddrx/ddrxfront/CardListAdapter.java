package com.ddrx.ddrxfront;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddrx.ddrxfront.Model.CardWarehouse;
import com.ddrx.ddrxfront.Utilities.ReadByteArray;

import java.io.*;
import java.util.List;

/**
 * Created by vincentshaw on 2018/3/14.
 */

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private List<CardWarehouse> CardWarehouses_list;
    private Context context;

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

    public CardListAdapter(List<CardWarehouse> list_val, Context context){
        CardWarehouses_list = list_val;
        this.context = context;
    }

    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.warehouse_intro_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        CardWarehouse cardWarehouse = CardWarehouses_list.get(position);
        byte[] arr = null;
        try {
            arr = ReadByteArray.getContent(context.getFileStreamPath(cardWarehouse.getCW_cover_url()));
        }catch(IOException e){
            Log.e("vincent", "cant read image file for cover");
        }
        if(arr != null)
            holder.front_page.setImageBitmap(BitmapFactory.decodeByteArray(arr, 0, arr.length));
        holder.warehouse_name.setText(cardWarehouse.getCW_name());
        holder.add_time.setText(cardWarehouse.getUCW_time());
        holder.intro.setText(cardWarehouse.getCW_abstract());
    }

    @Override
    public int getItemCount(){
        return CardWarehouses_list.size();
    }
}
