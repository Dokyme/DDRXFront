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
            if (input_register_username.text.toString().trim().isEmpty()) {
                prompt(this@RegisterActivity, "用户名不能为空")
                return false
            }
            if (input_register_pwd.text.isEmpty() || input_register_confirmpwd.text.isEmpty()) {
                prompt(this@RegisterActivity, "密码不能为空")
                return false
            }
            if (!input_register_pwd.text.equals(input_register_confirmpwd.text)) {
                prompt(this@RegisterActivity, "两次输入的密码不一致")
                return false
            }
            if (input_register_pwd.text.length < 8) {
                prompt(this@RegisterActivity, "密码过短")
                return false
            } else if (input_register_pwd.text.length > 32) {
                prompt(this@RegisterActivity, "密码过长")
                return false
            }
            if (input_register_nickname.text.toString().trim().isEmpty()) {
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
                    .url("/user/sign_up")
                    .post(formBody)
                    .build()
                    .enqueue(object : com.ddrx.ddrxfront.Utilities.Request.DefaultCallback(this@RegisterActivity) {
                        override fun onSuccess(context: Context, data: JSONObject, message: String) {
                            prompt(this@RegisterActivity, "注册成功")
                            try {
                                JSONToEntity.getUserDetailInfo(this@RegisterActivity, data)
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