package com.ddrx.ddrxfront

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.ddrx.ddrxfront.Controller.TrainingController
import com.ddrx.ddrxfront.Model.Card
import com.ddrx.ddrxfront.Model.MemoryCard
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase
import kotlinx.android.synthetic.main.activity_training.*

/**
 * Created by dokym on 2018/3/30.
 */
class TrainingActivity : AppCompatActivity() {

    private var currentPage: Int = 0
    private var cwId: Long? = null
    private lateinit var trainingCardList: List<MemoryCard>
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                TrainingController.GET_MEMORY_CARDS -> {
                    trainingCardList = msg?.obj as List<MemoryCard>
                    viewpager_training.adapter = TrainingFragmentPagerAdapter()
                    viewpager_training.currentItem = 0
                }
            }
        }
    }
    private lateinit var controller: TrainingController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ddrx", "TrainingActivity")
        setContentView(R.layout.activity_training)

        cwId = intent.getLongExtra("CW_id", -1)
        controller = TrainingController(handler, this)
        controller.getNeedTrainingMemoryCard(cwId!!)

        initEvent()
    }

    fun initEvent() {
        btn_next.setOnClickListener({ nextPage() })
        btn_previous.setOnClickListener({ previousPage() })
    }

    fun nextPage() {
        currentPage++
        //切换viewpager
        viewpager_training.currentItem = currentPage
        if (currentPage == trainingCardList.size - 1) {
            btn_next.text = "开始测试"
            btn_next.setOnClickListener({
                val intent = Intent(this, TestingActivity::class.java)
                intent.putExtra("CW_id", cwId)
                startActivity(intent)
                finish()
            })
        } else if (currentPage == 1)
            btn_previous.text = "上一页"
    }

    fun previousPage() {
        currentPage--
        //切换viewpager
        viewpager_training.currentItem = currentPage
        if (currentPage == 0) {
            btn_previous.text = "退出训练"
            btn_previous.setOnClickListener({ startActivity(Intent(this, WarehouseActivity::class.java)) })
        } else if (currentPage == trainingCardList.size.minus(1))
            btn_next.text = "下一页"
    }

    inner class TrainingFragmentPagerAdapter : FragmentStatePagerAdapter(supportFragmentManager) {
        override fun getItem(position: Int): Fragment {
            val fragment = TrainingPageFragment()
            fragment.card = Card(trainingCardList[position].cC_content)
            return fragment
        }

        override fun getCount(): Int {
            return trainingCardList.size
        }

    }
}