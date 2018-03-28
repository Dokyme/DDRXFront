package com.ddrx.ddrxfront

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ddrx.ddrxfront.Utilities.*
import kotlinx.android.synthetic.main.activity_revise_password.*
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt
import com.ddrx.ddrxfront.Utilities.URLHelper.*
import okhttp3.*
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

/**
 * Created by dokym on 2018/3/28.
 */
class RevisePasswordActivity : AppCompatActivity() {
    val userInfoPreference = UserInfoPreference(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revise_password)
        actionBar?.setHomeButtonEnabled(true)
        initEvent()
    }

    fun initEvent() {
        btn_confirm_revising.setOnClickListener(OnConfirmRevisingClickedListener())
    }

    inner class OnConfirmRevisingClickedListener : View.OnClickListener {
        override fun onClick(v: View?) {
            if (check()) {
                post(input_old_pwd.text.toString(), input_new_pwd.text.toString())
            }
        }

        private fun check(): Boolean {
            val old = input_old_pwd.text.toString()
            val confirm = input_confirm_pwd.text.toString().trim()
            val new = input_new_pwd.text.toString().trim()
            if (old.isEmpty() || new.isEmpty() || confirm.isEmpty()) {
                prompt(this@RevisePasswordActivity, "密码不能为空")
                return false
            }
            if (!confirm.equals(new)) {
                prompt(this@RevisePasswordActivity, "两次输入的密码不一致")
                return false
            }
            return true
        }

        private fun post(oldPwd: String, newPwd: String) {
            val client = OKHttpClientWrapper.getInstance(this@RevisePasswordActivity)
            val formBody = FormBody.Builder()
                    .add(MAC, MacAddressUtil(this@RevisePasswordActivity).macAddress)
                    .add(USER_ID, "${userInfoPreference.userInfo.id}")
                    .add(PASSWORD_OLD, oldPwd)
                    .add(PASSWORD_NEW, newPwd)
                    .build()
            val request = Request.Builder()
                    .url(URLHelper("/user/sign_alter").build())
                    .post(formBody)
                    .build()
            client.newCall(request)
                    .enqueue(object : Callback {
                        override fun onFailure(call: Call?, e: IOException?) {
                            prompt(this@RevisePasswordActivity, "修改密码失败")
                        }

                        override fun onResponse(call: Call?, response: Response?) {
                            val obj = JSONObject(response?.body().toString())
                            when (obj.get("code") as Int) {
                                600 -> prompt(this@RevisePasswordActivity, "用户不存在！")
                                601 -> prompt(this@RevisePasswordActivity, "密码错误！")
                                1000 -> prompt(this@RevisePasswordActivity, "服务器错误。")
                                1003 -> prompt(this@RevisePasswordActivity, "无效的Cookies。")
                                1004 -> prompt(this@RevisePasswordActivity, "MAC地址错误。")
                                0 -> {
                                    prompt(this@RevisePasswordActivity, "修改密码成功。")
                                    try {
                                        JSONToEntity.getUserDetailInfo(this@RevisePasswordActivity, obj)
                                        finish()
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                        prompt(this@RevisePasswordActivity, "修改密码失败")
                                    }
                                }
                            }
                        }
                    })
        }
    }
}