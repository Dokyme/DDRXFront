package com.ddrx.ddrxfront

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ddrx.ddrxfront.Model.Card
import com.ddrx.ddrxfront.Model.MemoryCard
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase
import kotlinx.android.synthetic.main.activity_testing.*

/**
 * Created by dokym on 2018/3/30.
 */
class TestingActivity : AppCompatActivity() {

    private lateinit var testingCardList: MutableList<MemoryCard>
    private var cwId: Long? = null
    private lateinit var memoryMasterDatabase: MemoryMasterDatabase
    private var currentPage: Int = 0
    private lateinit var currentFragment: TestingPageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)
        cwId = intent.getLongExtra("CW_id", -1)
        memoryMasterDatabase = com.ddrx.ddrxfront.Model.MemoryMasterDatabase.getInstance(this)
        testingCardList = memoryMasterDatabase
                .memoryCardDAO
                .queryMemoryCardByCW_id(cwId!!)
        viewpager_testing.adapter = TestingFragmentPagerAdapter()

        initEvent()
    }

    fun initEvent() {
        btn_next.setOnClickListener(OnSubmit())
    }

    inner class OnSubmit : View.OnClickListener {
        override fun onClick(v: View?) {
            //对比答案
            if (currentFragment.getAnswer() == Card(testingCardList[currentPage].cC_content).cardTrainingItem.data) {
                
            } else {
                //回答错误
            }
            btn_next.text = "下一题"
            btn_next.setOnClickListener(OnNext())
        }
    }

    inner class OnNext : View.OnClickListener {
        override fun onClick(v: View?) {
            currentPage++
            //跳转到下一题
            btn_next.text = "确认"
            btn_next.setOnClickListener(OnSubmit())
        }
    }

    inner class TestingFragmentPagerAdapter : FragmentPagerAdapter(supportFragmentManager) {
        override fun getItem(position: Int): Fragment {
            val fragment = TestingPageFragment()
            fragment.card = Card(testingCardList[position].cC_content)
            currentFragment = fragment
            return fragment
        }

        override fun getCount(): Int {
            return testingCardList.size
        }
    }
}