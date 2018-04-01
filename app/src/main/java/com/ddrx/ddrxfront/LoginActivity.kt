package com.ddrx.ddrxfront

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ddrx.ddrxfront.Controller.InitUpdateDatabase
import com.ddrx.ddrxfront.Model.UserInfo
import com.ddrx.ddrxfront.Utilities.*
import kotlinx.android.synthetic.main.activity_login.*
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt
import okhttp3.FormBody
import org.json.JSONArray

/**
 * Created by dokym on 2018/3/15.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var userInfo: UserInfo
    lateinit var handler: Handler
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userInfo = UserInfoPreference(this).userInfo
        progressDialog = ProgressDialog.show(this, "正在更新数据", "请稍后...")
        handler = Handler({ msg: Message? ->
            kotlin.run {
                when (msg?.what) {
                    InitUpdateDatabase.NETWORK_ERROR -> {
                        prompt(this, "网络错误")
                        progressDialog.dismiss()
                    }
                    InitUpdateDatabase.UPDATE_SUCCESS -> progressDialog.dismiss()
                }
                true
            }
        })
        initEvent()
    }

    private fun initEvent() {
        btn_login?.setOnClickListener(LoginOnClickListener())
        btn_register?.setOnClickListener(RegisterOnClickListener())
        //测试专用快捷按钮
        btn_test_main_activity?.setOnClickListener({ v: View? -> startActivity(Intent(this@LoginActivity, AddNewModelActivity::class.java)) })
        btn_test_user_activity?.setOnClickListener({ v: View? -> startActivity(Intent(this@LoginActivity, UserActivity::class.java)) })
        btn_test_page?.setOnClickListener({ v: View? -> startActivity(Intent(this@LoginActivity, TestActivity::class.java)) })
    }

    private fun loginFromRemote(username: String, password: String) {
        val body = FormBody.Builder()
                .add("_MAC", MacAddressUtil(this).macAddress)
                .add("_USER_NAME", username)
                .add("_PASSWORD", password)
                .build()
        Request.Builder()
                .url(URLHelper("/user/sign_in").build())
                .post(body)
                .build()
                .enqueue(object : Request.DefaultCallback(this@LoginActivity) {
                    override fun onSuccess(context: Context, data: JSONArray, message: String) {
                        prompt(this@LoginActivity, "登陆成功。", true)
                        try {
                            progressDialog.show()
                            InitUpdateDatabase.updateCardWarehouseDatabase(this@LoginActivity, handler, OKHttpClientWrapper.getInstance(this@LoginActivity))
                            InitUpdateDatabase.updateCardModelDatabase(this@LoginActivity, handler, OKHttpClientWrapper.getInstance(this@LoginActivity))
                            InitUpdateDatabase.updateMemoryCardDatabase(this@LoginActivity, handler, OKHttpClientWrapper.getInstance(this@LoginActivity))
                            InitUpdateDatabase.updateTrainingRecordDatabase(this@LoginActivity, handler, OKHttpClientWrapper.getInstance(this@LoginActivity))
                            JSONToEntity.getUserInfo(this@LoginActivity, data.getJSONObject(0))
                            startActivity(Intent(this@LoginActivity, AddNewModelActivity::class.java))
                        } catch (e: Exception) {
                            e.printStackTrace()
                            prompt(this@LoginActivity, "登陆失败", true)
                        }
                    }
                })
                .execute(this)
    }

    inner class LoginOnClickListener : View.OnClickListener {
        override fun onClick(p0: View?) {
            val username = input_username.text.toString()
            val password = input_password.text.toString()
            if (username.isEmpty())
                Toast.makeText(this@LoginActivity, "用户名不能为空", Toast.LENGTH_SHORT).show()
            else if (password.isEmpty())
                Toast.makeText(this@LoginActivity, "密码不能为空", Toast.LENGTH_SHORT).show()
            else if (username.length > 32)
                Toast.makeText(this@LoginActivity, "用户名过长", Toast.LENGTH_SHORT).show()
            else if (password.length < 8)
                prompt(this@LoginActivity, "密码过短", false)
            else if (password.length > 32)
                Toast.makeText(this@LoginActivity, "密码过长", Toast.LENGTH_SHORT).show()
            else {
                loginFromRemote(username, password)
            }
        }
    }

    inner class RegisterOnClickListener : View.OnClickListener {
        override fun onClick(p0: View?) {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }
}