package com.ddrx.ddrxfront;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddrx.ddrxfront.Model.CardModel;
import com.ddrx.ddrxfront.Model.Model;

import java.util.List;

/**
 * Created by vincentshaw on 2018/4/1.
 */

public class ModelListAdapter extends RecyclerView.Adapter<ModelListAdapter.ViewHolder>{

    private List<CardModel> model_list;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public View intro_view;
        public ImageView front_page;
        public TextView warehouse_name;
        public TextView add_time;
        public TextView intro;

        public ViewHolder(View view){
            super(view);
            intro_view = view;
            front_page = view.findViewById(R.id.cardmodel_front_page);
            warehouse_name = view.findViewById(R.id.cardmodel_name);
            add_time = view.findViewById(R.id.cardmodel_add_time);
            intro = view.findViewById(R.id.cardmodel_intro);
        }
    }

    public ModelListAdapter(List<CardModel> list_val){
        model_list = list_val;
    }

    @Override
    public ModelListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardmodel_intro_item, parent, false);
        ModelListAdapter.ViewHolder holder = new ModelListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ModelListAdapter.ViewHolder holder, int position){
        CardModel cardModelIntro = model_list.get(position);
        holder.front_page.setImageResource(Model.TYPE[cardModelIntro.getCT_type()]);
        holder.warehouse_name.setText(cardModelIntro.getCT_name());
        holder.add_time.setText(cardModelIntro.getUCT_time());
        holder.intro.setText(cardModelIntro.getCT_brief());
    }

    @Override
    public int getItemCount(){
        return model_list.size();
    }
}
