package com.ddrx.ddrxfront

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddrx.ddrxfront.Model.TimeLineModel
import com.github.vipulasri.timelineview.TimelineView

/**
 * Created by dokym on 2018/3/23.
 */
class TimeLineAdapter(var feedList: List<TimeLineModel>?) : RecyclerView.Adapter<TimeLineViewHolder>() {

    lateinit var mContext: Context
    lateinit var mLayoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {
        mContext = parent.context
        mLayoutInflater = LayoutInflater.from(mContext)
        val view: View = mLayoutInflater.inflate(R.layout.item_timeline, parent, false)
        return TimeLineViewHolder(view, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun getItemCount(): Int {
        val fl = feedList
        if (fl != null)
            return fl.size
        return 0
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        val model: TimeLineModel? = feedList?.get(position)
        holder.mDate.text = model?.date
        holder.mMessage.text = model?.message
    }
}