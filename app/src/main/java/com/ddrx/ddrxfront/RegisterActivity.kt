package com.ddrx.ddrxfront

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ddrx.ddrxfront.Utilities.*
import com.ddrx.ddrxfront.Utilities.URLHelper.*
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.*
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


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
        btn_register_confirm.setOnClickListener(OnClickConfirmListener())
        btn_register_back.setOnClickListener(OnClickBackListener())
    }

    inner class OnClickBackListener : View.OnClickListener {
        override fun onClick(v: View?) {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }

    inner class OnClickConfirmListener : View.OnClickListener {
        override fun onClick(v: View?) {
            if (check()) {
                post(input_register_username.text.toString(), input_register_pwd.text.toString(), input_register_nickname.text.toString())
            }
        }

        fun check(): Boolean {
            return true
        }

        fun post(username: String, password: String, nickname: String) {
            val client = OKHttpClientWrapper.getInstance(this@RegisterActivity)
            val formBody = FormBody.Builder()
                    .add(MAC, MacAddressUtil(this@RegisterActivity).macAddress)
                    .add(USERNAME, username)
                    .add(PASSWORD, password)
                    .add(NICK_NAME, nickname)
                    .build()
            val request = Request.Builder()
                    .url(URLHelper("/user/sign_up").build())
                    .post(formBody)
                    .build()
            client.newCall(request)
                    .enqueue(object : Callback {
                        override fun onFailure(call: Call?, e: IOException?) {
                            prompt(this@RegisterActivity, "网络环境错误，请重试")
                        }

                        override fun onResponse(call: Call?, response: Response?) {
                            val obj = JSONObject(response?.body().toString())
                            when (obj.get("code") as Int) {
                                600 -> ToastUtil.prompt(this@RegisterActivity, "用户不存在！")
                                601 -> ToastUtil.prompt(this@RegisterActivity, "密码错误！")
                                1000 -> ToastUtil.prompt(this@RegisterActivity, "服务器错误。")
                                1003 -> ToastUtil.prompt(this@RegisterActivity, "无效的Cookies。")
                                1004 -> ToastUtil.prompt(this@RegisterActivity, "MAC地址错误。")
                                0 -> {
                                    prompt(this@RegisterActivity, "注册成功")
                                    try {
                                        JSONToEntity.getUserDetailInfo(this@RegisterActivity, obj)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                        prompt(this@RegisterActivity, "注册失败")
                                    }
                                }
                            }
                        }
                    })
        }
    }
}