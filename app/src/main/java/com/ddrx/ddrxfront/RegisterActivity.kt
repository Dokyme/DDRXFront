package com.ddrx.ddrxfront

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ddrx.ddrxfront.Utilities.*
import com.ddrx.ddrxfront.Utilities.URLHelper.*
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject


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
            finish()
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    inner class OnClickConfirmListener : View.OnClickListener {
        override fun onClick(v: View?) {
            if (check()) {
                post(input_register_username.text.toString().trim(), input_register_pwd.text.toString(), input_register_nickname.text.toString())
            }
        }

        fun check(): Boolean {
            val pwd = input_register_pwd.text.toString().trim()
            val username = input_register_username.text.toString().trim()
            val confirm = input_register_confirmpwd.text.toString().trim()
            val nickname = input_register_nickname.text.toString().trim()
            if (username.isEmpty()) {
                prompt(this@RegisterActivity, "用户名不能为空")
                return false
            }
            if (pwd.isEmpty() || confirm.isEmpty()) {
                prompt(this@RegisterActivity, "密码不能为空")
                return false
            }
            if (pwd != confirm) {
                prompt(this@RegisterActivity, "两次输入的密码不一致")
                return false
            }
            if (pwd.length < 8) {
                prompt(this@RegisterActivity, "密码过短")
                return false
            } else if (pwd.length > 32) {
                prompt(this@RegisterActivity, "密码过长")
                return false
            }
            if (nickname.isEmpty()) {
                prompt(this@RegisterActivity, "昵称不能为空")
                return false
            }
            return true
        }

        fun post(username: String, password: String, nickname: String) {
            val formBody = FormBody.Builder()
                    .add(MAC, MacAddressUtil(this@RegisterActivity).macAddress)
                    .add(USERNAME, username)
                    .add(PASSWORD, password)
                    .add(NICK_NAME, nickname)
                    .build()
            com.ddrx.ddrxfront.Utilities.Request.Builder()
                    .url(URLHelper("/user/sign_up").build())
                    .post(formBody)
                    .build()
                    .enqueue(object : com.ddrx.ddrxfront.Utilities.Request.SuccessfulCallback(this@RegisterActivity) {
                        override fun onSuccess(context: Context, data: JSONArray, message: String) {
                            prompt(this@RegisterActivity, "注册成功")
                            try {
                                JSONToEntity.getUserDetailInfo(this@RegisterActivity, data.getJSONObject(0))
                                startActivity(Intent(this@RegisterActivity, UserDetailActivity::class.java))
                                finish()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                prompt(this@RegisterActivity, "注册失败")
                            }
                        }
                    })
                    .execute(this@RegisterActivity)
        }
    }
}