package com.ddrx.ddrxfront

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ddrx.ddrxfront.Model.TimeLineModel
import kotlinx.android.synthetic.main.activity_user.*

/**
 * Created by dokym on 2018/3/23.
 */
class UserActivity : AppCompatActivity() {
    lateinit var mRecyclerView: RecyclerView
    lateinit var mTimeLineAdapter: TimeLineAdapter
    var mDataList: MutableList<TimeLineModel> = ArrayList()

    init {
        mDataList.add(TimeLineModel("复习了：二叉树", "2018-3-24"))
        mDataList.add(TimeLineModel("复习了：KMP算法", "2018-3-25"))
        mDataList.add(TimeLineModel("复习了：快速排序算法", "2018-3-26"))
        mDataList.add(TimeLineModel("复习了：《楚辞-离骚》", "2018-3-27"))
        mDataList.add(TimeLineModel("复习了：TCP/IP-Cubic算法", "2018-3-27"))
        mDataList.add(TimeLineModel("复习了：TCP/IP-Reno算法", "2018-3-27"))
        mDataList.sortByDescending { e -> e.date }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        mRecyclerView = view_recycle
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)
        user_image.setOnClickListener({ view: View? -> startActivity(Intent(this, UserDetailActivity::class.java)) })
        initView()
    }

    fun initView() {
        mTimeLineAdapter = TimeLineAdapter(mDataList)
        mRecyclerView.adapter = mTimeLineAdapter
    }
}