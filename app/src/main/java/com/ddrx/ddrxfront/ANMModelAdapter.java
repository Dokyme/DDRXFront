package com.ddrx.ddrxfront;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.ddrx.ddrxfront.Model.CardModel;
import com.ddrx.ddrxfront.Model.CardModelIntro;
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;
import com.ddrx.ddrxfront.Model.Model;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/30.
 */

public class ANMModelAdapter extends RecyclerView.Adapter<ANMModelAdapter.ViewHolder>{

    private List<CardModel> modelList;
    private AddNewWarehouseActivity activity;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View model_view;
        ImageView model_image;
        TextView model_name;

        public ViewHolder(View view){
            super(view);
            model_image = view.findViewById(R.id.ANW_model_image);
            model_name = view.findViewById(R.id.ANW_model_name);
            model_view = view;
        }
    }

    public ANMModelAdapter(List<CardModel> introList,AddNewWarehouseActivity activity){
        modelList = introList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_new_warehouse_model_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.model_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                final CardModel intro = modelList.get(position);
                activity.setChosen_model_id(intro.getCT_id());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        CardModel model = modelList.get(position);
        holder.model_image.setImageResource(Model.TYPE[model.getCT_type()]);
        holder.model_name.setText(model.getCT_name());
    }

    @Override
    public int getItemCount(){
        return modelList.size();
    }
}
