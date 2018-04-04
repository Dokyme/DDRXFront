package com.ddrx.ddrxfront;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddrx.ddrxfront.Model.NeedTrainingItem;

import java.util.List;

/**
 * Created by dokym on 2018/3/28.
 */

public class NeedTrainingAdapter extends RecyclerView.Adapter<NeedTrainingAdapter.ViewHolder> {

    private List<NeedTrainingItem> needTrainingItemList;

    public interface StartTrainingClickListener {
        void onClick(View view, int position);
    }

    public void setmListener(StartTrainingClickListener mListener) {
        this.mListener = mListener;
    }

    private StartTrainingClickListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView image;
        public TextView name;
        public TextView lastDate;
        public Button startTraining;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            image = view.findViewById(R.id.image_need_training);
            name = view.findViewById(R.id.text_need_training_name);
            lastDate = view.findViewById(R.id.text_last_training_date);
            startTraining = view.findViewById(R.id.btn_start_training);
        }
    }

    public NeedTrainingAdapter(List<NeedTrainingItem> dataList) {
        needTrainingItemList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_need_training, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        NeedTrainingItem nt = needTrainingItemList.get(position);
        holder.image.setImageBitmap(BitmapFactory.decodeFile(nt.getImageUrl()));
        holder.name.setText(nt.getWarehouseName());
        holder.lastDate.setText(nt.getLastTrainingDate());
        holder.startTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return needTrainingItemList.size();
    }
}
