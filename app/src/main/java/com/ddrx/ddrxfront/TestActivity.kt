package com.ddrx.ddrxfront

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ddrx.ddrxfront.Utilities.MacAddressUtil
import kotlinx.android.synthetic.main.activity_test.*

/**
 * Created by dokym on 2018/3/24.
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var mu = MacAddressUtil(this)
        btn_test_mac.setOnClickListener({ view: View? -> text_test_content.text = mu.macAddress })
        btn_test_visible.setOnClickListener({ v: View? ->
            kotlin.run {
                if (text_test_visible.visibility == View.VISIBLE)
                    text_test_visible.visibility = View.INVISIBLE
                else
                    text_test_visible.visibility = View.VISIBLE
            }
        })
    }
}