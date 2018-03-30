package com.ddrx.ddrxfront

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
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
    private lateinit var trainingCardList: MutableList<MemoryCard>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        cwId = intent.getLongExtra("CW_id", -1)
        trainingCardList = MemoryMasterDatabase.getInstance(this)
                .memoryCardDAO
                .queryMemoryCardByCW_id(cwId!!)
        viewpager_training.adapter = TrainingFragmentPagerAdapter()

        initEvent()
    }

    fun initEvent() {
        btn_next.setOnClickListener({ nextPage() })
        btn_previous.setOnClickListener({ previousPage() })
    }

    fun nextPage() {
        currentPage++
        //切换viewpager
        if (currentPage == trainingCardList.size - 1) {
            btn_next.text = "开始测试"
            btn_next.setOnClickListener({
                val intent = Intent(this, TestingActivity::class.java)
                intent.putExtra("CW_id", cwId)
                startActivity(intent)
            })
        } else if (currentPage == 1)
            btn_previous.text = "上一页"
    }

    fun previousPage() {
        currentPage--
        //切换viewpager
        if (currentPage == 0) {
            btn_previous.text = "退出训练"
            btn_previous.setOnClickListener({ startActivity(Intent(this, WarehouseActivity::class.java)) })
        } else if (currentPage == trainingCardList.size.minus(1))
            btn_next.text = "下一页"
    }

    inner class TrainingFragmentPagerAdapter : FragmentStatePagerAdapter(supportFragmentManager) {
        override fun getItem(position: Int): Fragment {
            val fragment = MemoryCard_Fragment()
            fragment.setCard(Card(trainingCardList[position].cC_content))
        }

        override fun getCount(): Int {
            return trainingCardList.size
        }
    }
}