package com.ddrx.ddrxfront

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddrx.ddrxfront.Model.UserDetailModel

/**
 * Created by dokym on 2018/3/24.
 */
class UserDetailAdapter(var feedList: List<UserDetailModel>?) : RecyclerView.Adapter<UserDetailViewHolder>() {

    lateinit var mContext: Context
    lateinit var mLayoutInflater: LayoutInflater

    companion object {
        interface OnItemClickListener {
            fun onItemClick(view: View?, position: Int?)
        }
    }

    var mOnItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailViewHolder {
        mContext = parent.context
        mLayoutInflater = LayoutInflater.from(mContext)
        val view: View = mLayoutInflater.inflate(R.layout.item_detail, parent, false)
        return UserDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        val fl = feedList
        if (fl != null)
            return fl.size
        return 0
    }

    override fun onBindViewHolder(holder: UserDetailViewHolder, position: Int) {
        val model = feedList?.get(position)
        holder.mLabel.text = model?.label
        holder.mValue.text = model?.value
        holder.itemView.setOnClickListener({ v -> mOnItemClickListener?.onItemClick(v, position) })
    }
}