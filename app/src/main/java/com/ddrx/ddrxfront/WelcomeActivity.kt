package com.ddrx.ddrxfront

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import com.ddrx.ddrxfront.Controller.InitUpdateDatabase
import com.ddrx.ddrxfront.Model.UserInfo
import com.ddrx.ddrxfront.Utilities.*
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt
import okhttp3.*
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.util.*

/**
 * Created by dokym on 2018/3/24.
 */
class WelcomeActivity : AppCompatActivity() {

    private lateinit var userInfo: UserInfo
    private lateinit var handler: Handler
    private var partSuccess = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        userInfo = UserInfoPreference(this).userInfo
        handler = Handler({ msg: Message? ->
            kotlin.run {
                when (msg?.what) {
                    InitUpdateDatabase.UPDATE_CARD_WAREHOUSE -> {
                        partSuccess++
                        if (partSuccess == 4) {
                            partSuccess = 0
                            startActivity(Intent(this, WarehouseActivity::class.java))
                        }
                    }
                    InitUpdateDatabase.UPDATE_CARD_MODEL -> {
                        partSuccess++
                        if (partSuccess == 4) {
                            partSuccess = 0
                            startActivity(Intent(this, WarehouseActivity::class.java))
                        }
                    }
                    InitUpdateDatabase.UPDATE_TRAINING_RECORD -> {
                        partSuccess++
                        if (partSuccess == 4) {
                            partSuccess = 0
                            startActivity(Intent(this, WarehouseActivity::class.java))
                        }
                    }
                    InitUpdateDatabase.UPDATE_MEMORY_CARD -> {
                        partSuccess++
                        if (partSuccess == 4) {
                            partSuccess = 0
                            startActivity(Intent(this, WarehouseActivity::class.java))
                        }
                    }
                    InitUpdateDatabase.NETWORK_ERROR -> {
                        prompt(this, "网络错误")
                        handler.removeMessages(InitUpdateDatabase.NETWORK_ERROR)
                        handler.removeMessages(InitUpdateDatabase.UPDATE_CARD_WAREHOUSE)
                        handler.removeMessages(InitUpdateDatabase.UPDATE_CARD_MODEL)
                        handler.removeMessages(InitUpdateDatabase.UPDATE_TRAINING_RECORD)
                        handler.removeMessages(InitUpdateDatabase.UPDATE_MEMORY_CARD)
                        finish()
                    }
                }
                true
            }
        })
        check()
    }

    private fun check() {
        try {
            if (userInfo != null) {
                val body = FormBody.Builder()
                        .add("_MAC", MacAddressUtil(this).macAddress)
                        .build()
                val request = Request.Builder()
                        .post(body)
                        .url(URLHelper("/user/sign_in").build())
                        .build()
                OKHttpClientWrapper.getInstance(this)
                        .newCall(request)
                        .enqueue(object : Callback {
                            override fun onFailure(call: Call?, e: IOException?) {
                                startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                                finish()
                            }

                            override fun onResponse(call: Call?, response: Response?) {
                                val content = response?.body()?.string()
                                val obj = JSONObject(content)
                                when (obj.get("code") as Int) {
                                    0 -> {
                                        InitUpdateDatabase.updateCardWarehouseDatabase(this@WelcomeActivity, handler, OKHttpClientWrapper.getInstance(this@WelcomeActivity))
                                        InitUpdateDatabase.updateCardModelDatabase(this@WelcomeActivity, handler, OKHttpClientWrapper.getInstance(this@WelcomeActivity))
                                        InitUpdateDatabase.updateMemoryCardDatabase(this@WelcomeActivity, handler, OKHttpClientWrapper.getInstance(this@WelcomeActivity))
                                        InitUpdateDatabase.updateTrainingRecordDatabase(this@WelcomeActivity, handler, OKHttpClientWrapper.getInstance(this@WelcomeActivity))
                                    }
                                    else -> {
//                                        prompt(this@WelcomeActivity, obj.get("msg") as String, true)
                                        startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                                        finish()
                                    }
                                }
                            }
                        })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}