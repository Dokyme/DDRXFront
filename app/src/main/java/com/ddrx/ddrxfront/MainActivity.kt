package com.ddrx.ddrxfront

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun gotoWarehouseActivity(view: View){
        var intent = Intent(this, WarehouseActivity::class.java)
        startActivity(intent)
    }
}
