package com.ddrx.ddrxfront;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ddrx.ddrxfront.Model.CardBriefInfo;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/30.
 */

public class ANMCardAdapter extends RecyclerView.Adapter<ANMCardAdapter.ViewHolder> {

    private List<CardBriefInfo> cardList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView card_id;
        TextView card_intro;

        public ViewHolder(View view){

        }
    }
}
