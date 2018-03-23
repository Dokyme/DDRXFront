package com.ddrx.ddrxfront

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Created by dokym on 2018/3/23.
 */
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initEvent()
    }

    fun initEvent() {
        btn_register_confirm.setOnClickListener(ConfirmOnClickListener())
        btn_register_back.setOnClickListener(BackOnClickListener())
    }

    inner class BackOnClickListener : View.OnClickListener {
        override fun onClick(v: View?) {

        }

        fun check():Boolean{

        }

        fun
    }

    inner class ConfirmOnClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}