package com.ddrx.ddrxfront

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase
import kotlinx.android.synthetic.main.activity_training.*

/**
 * Created by dokym on 2018/3/30.
 */
class TrainingActivity : AppCompatActivity() {

    private var currentPage: Int = 0
    private var fragmentList: MutableList<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        val cwid = intent.getLongExtra("CWName")
        val cards = MemoryMasterDatabase.getInstance(this)
                .memoryCardDAO
                .queryMemoryCardByCW_id(cwid)
        fragmentList = ArrayList()
        for (card in cards) {
            val fragment = MemoryCard_Fragment()
            fragment.setCard(card)
            fragmentList.add(fragment)
        }
    }

    fun initEvent() {
        viewpager_training.adapter = MemoryCardFragmentPagerAdapter(null, supportFragmentManager)
    }
}