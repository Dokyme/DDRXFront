package com.ddrx.ddrxfront

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.vipulasri.timelineview.TimelineView

/**
 * Created by dokym on 2018/3/23.
 */
class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
    val mDate: TextView
    val mMessage: TextView
    val mTimeLineView: TimelineView

    init {
        mTimeLineView = itemView.findViewById(R.id.time_marker)
        mTimeLineView.initLine(viewType)
        mDate = itemView.findViewById(R.id.text_timeline_date)
        mMessage = itemView.findViewById(R.id.text_timeline_message)
    }
}