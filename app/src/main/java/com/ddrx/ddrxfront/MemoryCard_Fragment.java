package com.ddrx.ddrxfront;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddrx.ddrxfront.Model.Card;
import com.ddrx.ddrxfront.Model.CardFieldDisplayItem;
import com.ddrx.ddrxfront.Model.Model;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryCard_Fragment extends Fragment {

    Card card;
    public MemoryCard_Fragment() {
        // Required empty public constructor
    }

    public void setCard(Card card){
        this.card = card;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memory_card, container, false);
        LinearLayout layout = view.findViewById(R.id.memory_card_layout);
        List<CardFieldDisplayItem> displayItems = card.getDisplayItems();

        for(CardFieldDisplayItem item:displayItems){
            LinearLayout new_record = new LinearLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if(item.isName_visible()){
                TextView item_name = new TextView(getContext());
                item_name.setTextSize(14);
                item_name.setGravity(Gravity.START);
                LinearLayout.LayoutParams margins = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                margins.setMargins(8 * item.getLevel(), 0, 0, 0);
                item_name.setLayoutParams(margins);
                new_record.addView(item_name);
            }

            TextView item_data = new TextView(getContext());
            item_data.setTextSize(item.getText_size());
            if(item.getAlign() == Model.LEFT_ALIGN){
                if(!item.isName_visible()){
                    LinearLayout.LayoutParams margins = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    margins.setMargins(8 * item.getLevel(), 0, 0, 0);
                    item_data.setLayoutParams(margins);
                }
                item_data.setGravity(Gravity.START);
            }
            else if(item.getAlign() == Model.MID_ALIGN){
                item_data.setGravity(Gravity.CENTER);
            }
            else if(item.getAlign() == Model.RIGHT_ALIGN){
                item_data.setGravity(Gravity.END);
            }
            new_record.addView(item_data);
            layout.addView(new_record);
        }

        return view;
    }

}
