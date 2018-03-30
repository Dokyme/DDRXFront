package com.ddrx.ddrxfront;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.ddrx.ddrxfront.Model.CardModelIntro;
import com.ddrx.ddrxfront.Model.Model;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/30.
 */

public class ANMModelAdapter extends RecyclerView.Adapter<ANMModelAdapter.ViewHolder>{

    private List<CardModelIntro> modelList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView model_image;
        TextView model_name;

        public ViewHolder(View view){
            super(view);
            model_image = view.findViewById(R.id.ANW_model_image);
            model_name = view.findViewById(R.id.ANW_model_name);
        }
    }

    public ANMModelAdapter(List<CardModelIntro> introList){
        modelList = introList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_new_warehouse_model_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        CardModelIntro intro = modelList.get(position);
        holder.model_image.setImageResource(Model.TYPE[intro.getType()]);
        holder.model_name.setText(intro.getModel_name());
    }

    @Override
    public int getItemCount(){
        return modelList.size();
    }
}
