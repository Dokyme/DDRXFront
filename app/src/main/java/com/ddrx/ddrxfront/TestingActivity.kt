package com.ddrx.ddrxfront

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.ddrx.ddrxfront.Controller.TrainingController
import com.ddrx.ddrxfront.Model.Card
import com.ddrx.ddrxfront.Model.CardFieldTrainingItem
import com.ddrx.ddrxfront.Model.MemoryCard
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper.Companion.context
import kotlinx.android.synthetic.main.activity_testing.*
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt
import kotlinx.android.synthetic.main.activity_user_detail.*

/**
 * Created by dokym on 2018/3/30.
 */
class TestingActivity : AppCompatActivity() {

    private lateinit var testingCardList: List<MemoryCard>
    private val cardList: MutableList<Card> = mutableListOf()
    private val testingQuesList: MutableList<CardFieldTrainingItem> = mutableListOf()
    private var cwId: Long? = null
    private lateinit var current: TestingPageFragment
    private var currentQuesIndex: Int = 0
    private var currentCardIndex: Int = 0
    private var quesPerCard: Int? = null
    private var handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                TrainingController.GET_MEMORY_CARDS -> {
                    testingCardList = msg?.obj as List<MemoryCard>
                    for (mCard in testingCardList) {
                        val card = Card(mCard.cC_content)
                        cardList.add(card)
                        testingQuesList.addAll(card.cardTrainingItem)
                    }
                    quesPerCard = cardList[0].cardTrainingItem.size
                    viewpager_testing.adapter = TestingFragmentPagerAdapter()
                    viewpager_testing.currentItem = 0
                }
            }
        }
    }
    private lateinit var controller: TrainingController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)
        cwId = intent.getLongExtra("CW_id", -1)
        controller = TrainingController(handler, context)
        controller.getNeedTrainingMemoryCard(cwId!!)
        initEvent()
    }

    fun initEvent() {
        btn_pass.setOnClickListener(OnPass())
        btn_notpass.setOnClickListener(OnNotPass())
        btn_show_answer.setOnClickListener(OnShowAnswer())
    }

    inner class OnPass : View.OnClickListener {
        override fun onClick(v: View?) {
            currentQuesIndex++
            val current = viewpager_testing.adapter?.instantiateItem(viewpager_testing, viewpager_testing.currentItem) as TestingPageFragment
            controller.incrementTrainingCount(cwId!!, (current.quesIndex?.div(quesPerCard!!)?.plus(1))?.toLong()!!)
            viewpager_testing.currentItem = currentQuesIndex
            if (currentQuesIndex % quesPerCard!! == 0) {
                //说明进入了下一张卡片
                currentCardIndex++
                if (currentCardIndex == testingCardList.size) {
                    //所有卡片的所有题目都已经答题完毕
                    prompt(this@TestingActivity, "本次训练结束")
                    startActivity(Intent(this@TestingActivity, WarehouseActivity::class.java))
                    return
                }
            }
        }
    }

    inner class OnNotPass : View.OnClickListener {
        override fun onClick(v: View?) {
            currentQuesIndex++
            viewpager_testing.currentItem = currentQuesIndex
            if (currentQuesIndex % quesPerCard!! == 0) {
                //说明进入了下一张卡片
                currentCardIndex++
                if (currentCardIndex == testingCardList.size) {
                    //所有卡片的所有题目都已经答题完毕
                    prompt(this@TestingActivity, "本次训练结束")
                    startActivity(Intent(this@TestingActivity, WarehouseActivity::class.java))
                    return
                }
            }
        }
    }

    inner class OnShowAnswer : View.OnClickListener {
        override fun onClick(v: View?) {
            btn_show_answer.isEnabled = false
            val current = viewpager_testing.adapter?.instantiateItem(viewpager_testing, viewpager_testing.currentItem) as TestingPageFragment
            current.showAnswer()
        }
    }

    inner class TestingFragmentPagerAdapter : FragmentPagerAdapter(supportFragmentManager) {
        override fun getItem(position: Int): Fragment {
            btn_show_answer.isEnabled = true
            val fragment = TestingPageFragment()
            fragment.item = testingQuesList[position]
            fragment.quesIndex = position
            return fragment
        }

        override fun getCount(): Int {
            return testingQuesList.size
        }

        override fun setPrimaryItem(container: View, position: Int, `object`: Any) {
            super.setPrimaryItem(container, position, `object`)
            btn_show_answer.isEnabled = true
            currentQuesIndex = (`object` as TestingPageFragment).quesIndex!!
            current = `object`
        }


    }
}