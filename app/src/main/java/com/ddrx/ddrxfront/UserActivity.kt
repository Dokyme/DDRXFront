package com.ddrx.ddrxfront

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by dokym on 2018/3/23.
 */
class UserActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initEvent()
    }
}