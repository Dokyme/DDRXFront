package com.ddrx.ddrxfront

import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.TextView

/**
 * Created by dokym on 2018/3/24.
 */
class UserDetailViewHolder(itemView: View, var clickListener: UserDetailAdapter.Companion.OnItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val mLabel: TextView
    val mValue: TextView

    init {
        mLabel = itemView.findViewById(R.id.text_detail_label)
        mValue = itemView.findViewById(R.id.text_detail_value)
    }

    override fun onClick(v: View?) {
        clickListener.onItemClick(v, adapterPosition)
    }
}