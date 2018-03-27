package com.ddrx.ddrxfront

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ddrx.ddrxfront.Model.UserInfo
import com.ddrx.ddrxfront.Utilities.CookiesPreference
import com.ddrx.ddrxfront.Utilities.MacAddressUtil
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper
import com.ddrx.ddrxfront.Utilities.UserInfoPreference
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.*

/**
 * Created by dokym on 2018/3/24.
 */
class WelcomeActivity : AppCompatActivity() {

    private lateinit var userInfo: UserInfo
    private var success: Boolean? = null
    private var timeUp = false
    private lateinit var task: TimerTask
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        userInfo = UserInfoPreference(this).userInfo
        task = object : TimerTask() {
            override fun run() {
                timeUp = true
                val temp = success
                if (temp != null) {
                    if (temp)
                        startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                    else
                        startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }
        check()
        timer = Timer()
        timer.schedule(task, 1000)
    }

    private fun check() {
        try {
            if (userInfo != null) {
                val body = FormBody.Builder()
                        .add("_MAC", MacAddressUtil(this).macAddress)
                        .build()
                val request = Request.Builder()
                        .post(body)
                        .url(LoginActivity.LOGIN_URL)
                        .build()
                OKHttpClientWrapper.getInstance(this)
                        .newCall(request)
                        .enqueue(object : Callback {
                            override fun onFailure(call: Call?, e: IOException?) {
                                success = false
                                if (timeUp) {
                                    startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                                    finish()
                                }
                            }

                            override fun onResponse(call: Call?, response: Response?) {
                                val obj = JSONObject(response?.body().toString())
                                when (obj.get("code") as Int) {
                                    0 -> {
                                        success = true
                                        if (timeUp) {
                                            startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                                            finish()
                                        }
                                    }
                                    else -> {
                                        CookiesPreference(this@WelcomeActivity).cookieList = emptyList()
                                        success = false
                                        if (timeUp) {
                                            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                                            finish()
                                        }
                                    }
                                }
                            }
                        })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            success = false
            timer.cancel()
            task.run()
        }
    }
}