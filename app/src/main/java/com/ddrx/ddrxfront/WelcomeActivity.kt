package com.ddrx.ddrxfront

import android.content.Context
import com.ddrx.ddrxfront.Controller.InitUpdateDatabase
import com.ddrx.ddrxfront.Utilities.MacAddressUtil
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper
import com.ddrx.ddrxfront.Utilities.Request
import com.ddrx.ddrxfront.Utilities.URLHelper
import okhttp3.FormBody
import org.json.JSONArray

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ddrx.ddrxfront.Model.UserInfo
import com.ddrx.ddrxfront.Utilities.*
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt

/**
 * Created by dokym on 2018/3/24.
 */
class WelcomeActivity : AppCompatActivity() {

    private lateinit var userInfo: UserInfo
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        userInfo = UserInfoPreference(this).userInfo
        handler = Handler({ msg: Message? ->
            kotlin.run {
                when (msg?.what) {
                    InitUpdateDatabase.NETWORK_ERROR -> {
                        prompt(this, "网络错误")
                        Log.d("dokyme", "WelcomeActivity InitUpdateDatabase.NETWORK_ERROR")
                        handler.removeMessages(InitUpdateDatabase.NETWORK_ERROR)
                        handler.removeMessages(InitUpdateDatabase.UPDATE_WAREHOUSE_SUCCESS)
                        handler.removeMessages(InitUpdateDatabase.UPDATE_MODEL_SUCCESS)
                        handler.removeMessages(InitUpdateDatabase.UPDATE_TRAINING_SUCCESS)
                        finish()
                    }
                    InitUpdateDatabase.UPDATE_TRAINING_SUCCESS -> {
                        InitUpdateDatabase.updateCardModelDatabase(this@WelcomeActivity, handler, OKHttpClientWrapper.getInstance(this@WelcomeActivity))
                    }
                    InitUpdateDatabase.UPDATE_MODEL_SUCCESS -> {
                        InitUpdateDatabase.updateCardWarehouseDatabase(this@WelcomeActivity, handler, OKHttpClientWrapper.getInstance(this@WelcomeActivity))
                    }
                    InitUpdateDatabase.UPDATE_WAREHOUSE_SUCCESS -> {
                        startActivity(Intent(this, AddNewWarehouseActivity::class.java))
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
                Request.Builder()
                        .url(URLHelper("/user/sign_in").build())
                        .post(body)
                        .build()
                        .enqueue(object : Request.DefaultCallback(this@WelcomeActivity) {
                            override fun onSuccess(context: Context, data: JSONArray, message: String) {
                                InitUpdateDatabase.updateTrainingRecordDatabase(this@WelcomeActivity, handler, OKHttpClientWrapper.getInstance(this@WelcomeActivity))
                            }

                            override fun onAnyFailure(message: String) {
                                startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                                finish()
                            }
                        })
                        .execute(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}