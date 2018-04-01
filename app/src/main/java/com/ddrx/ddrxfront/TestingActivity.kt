package com.ddrx.ddrxfront

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ddrx.ddrxfront.Model.Card
import com.ddrx.ddrxfront.Model.CardFieldTrainingItem
import com.ddrx.ddrxfront.Model.MemoryCard
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase
import kotlinx.android.synthetic.main.activity_testing.*
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt

/**
 * Created by dokym on 2018/3/30.
 */
class TestingActivity : AppCompatActivity() {

    private var testingCardList: MutableList<MemoryCard> = mutableListOf()
    private val cardList: MutableList<Card> = mutableListOf()
    private val testingQuesList: MutableList<CardFieldTrainingItem> = mutableListOf()
    private var cwId: Long? = null
    private lateinit var memoryMasterDatabase: MemoryMasterDatabase
    private var currentQuesIndex: Int = 0
    private var currentCardIndex: Int = 0
    private var quesPerCard: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)
        cwId = intent.getLongExtra("CW_id", -1)
        memoryMasterDatabase = com.ddrx.ddrxfront.Model.MemoryMasterDatabase.getInstance(this)
        testingCardList = memoryMasterDatabase
                .cardTrainingRecordDAO
                .queryNeedTrainingMemoryCardByCWId(cwId)
        for (mCard in testingCardList) {
            val card = Card(mCard.cC_content)
            cardList.add(card)
            testingQuesList.addAll(card.cardTrainingItem)
        }
        quesPerCard = cardList[0].cardTrainingItem.size
        viewpager_testing.adapter = TestingFragmentPagerAdapter()

        initEvent()
    }

    fun initEvent() {
        btn_pass.setOnClickListener(OnPass())
        btn_notpass.setOnClickListener(OnNotPass())
        btn_show_answer.setOnClickListener(OnShowAnswer())
    }

    inner class OnPass : View.OnClickListener {
        override fun onClick(v: View?) {
            val training = memoryMasterDatabase
                    .cardTrainingRecordDAO
                    .queryTrainRecord(cwId, testingCardList[currentCardIndex].cC_id)
            training.training_count++
            memoryMasterDatabase
                    .cardTrainingRecordDAO
                    .updateCardsTrainingRecords(training)
            currentQuesIndex++
            viewpager_testing.currentItem = currentQuesIndex
            if (currentQuesIndex % quesPerCard!! == 0) {
                //说明进入了下一张卡片
                currentCardIndex++
                if (currentCardIndex == tes tingCardList.size) {
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
        }
    }

    inner class TestingFragmentPagerAdapter : FragmentPagerAdapter(supportFragmentManager) {
        override fun getItem(position: Int): Fragment {
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
        }
    }
}