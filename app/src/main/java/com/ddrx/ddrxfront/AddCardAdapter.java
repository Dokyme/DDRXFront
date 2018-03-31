package com.ddrx.ddrxfront;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import com.ddrx.ddrxfront.Model.Model;
import com.ddrx.ddrxfront.Model.ModelInput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vincentshaw on 2018/3/31.
 */

public class AddCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ModelInput> entryList;
    private HashMap<Integer, View> entryManager;
    private Context context;
    private int now_count = 0;
    private final int SINGLE_ONLY = 1;
    private final int SINGLE_MUL = 2;
    private final int TWO_ONLY = 3;
    private final int TWO_MUL = 4;

    class SingleLineOnlyViewHolder extends RecyclerView.ViewHolder{
        TextView entry_name;
        EditText entry_data;

        public SingleLineOnlyViewHolder(View view){
            super(view);
            entry_name = view.findViewById(R.id.AC_entry_name);
            entry_data = view.findViewById(R.id.AC_entry_data);
        }
    }

    class SingleLineMulViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView entry_name;
        EditText entry_data;
        ImageView new_entry;

        public SingleLineMulViewHolder(View view){
            super(view);
            entry_name = view.findViewById(R.id.AC_entry_name);
            entry_data = view.findViewById(R.id.AC_entry_data);
            new_entry = view.findViewById(R.id.AC_add_new_single_entry);
            this.view = view;
        }
    }

    class TwoLineOnlyViewHolder extends RecyclerView.ViewHolder{
        TextView entry_name;
        EditText entry_data;

        public TwoLineOnlyViewHolder(View view){
            super(view);
            entry_name = view.findViewById(R.id.AC_entry_name);
            entry_data = view.findViewById(R.id.AC_entry_data);
        }
    }

    class TwoLineMulViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView entry_name;
        EditText entry_data;
        ImageView new_entry;

        public TwoLineMulViewHolder(View view){
            super(view);
            this.view = view;
            entry_name = view.findViewById(R.id.AC_entry_name);
            entry_data = view.findViewById(R.id.AC_entry_data);
            new_entry = view.findViewById(R.id.AC_add_two_single_entry);
        }
    }

    public AddCardAdapter(List<ModelInput> cardItems, Context context){
        entryList = cardItems;
        entryManager = new HashMap<>();
        this.context = context;
    }

    public Map<Integer, View> getEntryManager(){
        return entryManager;
    }

    @Override
    public int getItemViewType(int position) {
        if(entryList.get(position).getType() != Model.PARAGRAPH){
            if(entryList.get(position).getNum() != Model.ONLY_ONE){
                return SINGLE_ONLY;
            }
            else{
                return SINGLE_MUL;
            }
        }
        else{
            if(entryList.get(position).getNum() != Model.ONLY_ONE){
                return TWO_ONLY;
            }
            else{
                return TWO_MUL;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case SINGLE_ONLY:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_line_entry_only_item, parent, false);
                entryManager.put(now_count++, view);
                holder = new SingleLineOnlyViewHolder(view);
                break;
            }
            case SINGLE_MUL:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_line_entry_mul_item, parent, false);
                entryManager.put(now_count++, view.findViewById(R.id.AC_first_layout));
                holder = new SingleLineMulViewHolder(view);
                break;
            }
            case TWO_ONLY:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.two_line_entry_only_item, parent, false);
                entryManager.put(now_count++, view);
                holder = new TwoLineOnlyViewHolder(view);
                break;
            }
            case TWO_MUL:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.two_line_entry_mul_item, parent, false);
                entryManager.put(now_count++, view.findViewById(R.id.AC_first_layout));
                holder = new TwoLineMulViewHolder(view);
                break;
            }
            default:
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position){
        ModelInput input = entryList.get(position);
        if(viewHolder instanceof SingleLineOnlyViewHolder){
            ((SingleLineOnlyViewHolder) viewHolder).entry_name.setText(input.getName());
        }
        else if(viewHolder instanceof SingleLineMulViewHolder){
            ((SingleLineMulViewHolder) viewHolder).entry_name.setText(input.getName());
            ((SingleLineMulViewHolder) viewHolder).new_entry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout n_entry = (LinearLayout) View.inflate(context, R.layout.single_line_entry, null);
                    ImageView delete_btn = n_entry.findViewById(R.id.AC_delete_entry);
                    delete_btn.setTag(now_count);
                    delete_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int item_id = (int) v.getTag();
                            LinearLayout delete_entry = (LinearLayout) entryManager.get(item_id);
                            ((LinearLayout) ((SingleLineMulViewHolder) viewHolder).view.findViewById(R.id.AC_content_layout)).removeView(delete_entry);
                            entryManager.remove(item_id);
                        }
                    });
                    entryManager.put(now_count++, ((SingleLineMulViewHolder) viewHolder).view);
                    ((LinearLayout)((SingleLineMulViewHolder) viewHolder).view.findViewById(R.id.AC_content_layout)).addView(n_entry);
                }
            });
        }
        else if(viewHolder instanceof TwoLineOnlyViewHolder){
            ((TwoLineOnlyViewHolder) viewHolder).entry_name.setText(input.getName());
        }
        else if(viewHolder instanceof TwoLineMulViewHolder){
            ((TwoLineMulViewHolder) viewHolder).entry_name.setText(input.getName());
            ((TwoLineMulViewHolder) viewHolder).new_entry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout n_entry = (LinearLayout) View.inflate(context, R.layout.two_line_entry, null);
                    ImageView delete_btn = n_entry.findViewById(R.id.AC_delete_entry);
                    delete_btn.setTag(now_count);
                    delete_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int item_id = (int) v.getTag();
                            LinearLayout delete_entry = (LinearLayout) entryManager.get(item_id);
                            ((LinearLayout) ((TwoLineMulViewHolder) viewHolder).view.findViewById(R.id.AC_content_layout)).removeView(delete_entry);
                            entryManager.remove(item_id);
                        }
                    });
                    entryManager.put(now_count++, ((TwoLineMulViewHolder) viewHolder).view);
                    ((LinearLayout)((TwoLineMulViewHolder) viewHolder).view.findViewById(R.id.AC_content_layout)).addView(n_entry);
                }
            });
        }
    }

    @Override
    public int getItemCount(){
        return entryList.size();
    }
}

