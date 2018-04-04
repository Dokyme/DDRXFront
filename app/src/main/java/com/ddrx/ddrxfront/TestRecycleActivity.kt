package com.ddrx.ddrxfront

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_test_recycler.*

class TestRecycleActivity : FragmentActivity() {

    lateinit var list: MutableList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_recycler)

        list = mutableListOf(com.ddrx.ddrxfront.Fragment())
        viewpager.adapter = ViewPagerAdapter()
        viewpager.currentItem = 0
    }

    inner class ViewPagerAdapter : FragmentStatePagerAdapter(supportFragmentManager) {
        override fun getItem(position: Int): Fragment {
            return list[position]
        }

        override fun getCount(): Int {
            return list.size
        }
    }
}
