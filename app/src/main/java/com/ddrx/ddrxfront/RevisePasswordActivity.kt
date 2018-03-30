package com.ddrx.ddrxfront

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ddrx.ddrxfront.Utilities.*
import kotlinx.android.synthetic.main.activity_revise_password.*
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt
import com.ddrx.ddrxfront.Utilities.URLHelper.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

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
        btn_cancel_revising.setOnClickListener({ v ->
            kotlin.run {
                startActivity(Intent(this, UserDetailActivity::class.java))
                finish()
            }
        })
    }

    inner class OnConfirmRevisingClickedListener : View.OnClickListener {
        override fun onClick(v: View?) {
            if (check()) {
                post(input_old_pwd.text.toString().trim(), input_new_pwd.text.toString().trim())
            }
        }

        private fun check(): Boolean {
            val old = input_old_pwd.text.toString().trim()
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
            if (new.length < 8) {
                prompt(this@RevisePasswordActivity, "密码果断")
                return false
            } else if (new.length > 32) {
                prompt(this@RevisePasswordActivity, "密码过长")
                return false
            }
            return true
        }

        private fun post(oldPwd: String, newPwd: String) {
            val formBody = FormBody.Builder()
                    .add(MAC, MacAddressUtil(this@RevisePasswordActivity).macAddress)
                    .add(USER_ID, "${userInfoPreference.userInfo.id}")
                    .add(PASSWORD_OLD, oldPwd)
                    .add(PASSWORD_NEW, newPwd)
                    .build()
            com.ddrx.ddrxfront.Utilities.Request.Builder()
                    .url("/user/sign_alter")
                    .post(formBody)
                    .build()
                    .enqueue(object : com.ddrx.ddrxfront.Utilities.Request.DefaultCallback(this@RevisePasswordActivity) {
                        override fun onSuccess(context: Context, data: JSONArray, message: String) {
                            prompt(this@RevisePasswordActivity, "修改密码成功。", true)
                            try {
                                JSONToEntity.getUserDetailInfo(this@RevisePasswordActivity, data.getJSONObject(0))
                                finish()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                prompt(this@RevisePasswordActivity, "修改密码失败", true)
                            }
                        }
                    })
                    .execute(this@RevisePasswordActivity)
        }
    }
}