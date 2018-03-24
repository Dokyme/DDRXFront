package com.ddrx.ddrxfront

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ddrx.ddrxfront.Model.UserInfo
import com.ddrx.ddrxfront.Utilities.JSONToEntity
import com.ddrx.ddrxfront.Utilities.MacAddressUtil
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import com.ddrx.ddrxfront.Utilities.ToastUtil.prompt;
import com.ddrx.ddrxfront.Utilities.UserInfoPreference

/**
 * Created by dokym on 2018/3/15.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var userInfo: UserInfo

    companion object {
        var LOGIN_URL = "http://localhost:8080/user/sign_in"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userInfo = UserInfoPreference(this).userInfo
        initEvent()
    }

    private fun initEvent() {
        btn_login?.setOnClickListener(LoginOnClickListener())
        btn_register?.setOnClickListener(RegisterOnClickListener())
        //测试专用快捷按钮
        btn_test_main_activity?.setOnClickListener({ v: View? -> startActivity(Intent(this@LoginActivity, MainActivity::class.java)) })
        btn_test_user_activity?.setOnClickListener({ v: View? -> startActivity(Intent(this@LoginActivity, UserActivity::class.java)) })
        btn_test_page?.setOnClickListener({ v: View? -> startActivity(Intent(this@LoginActivity, TestActivity::class.java)) })
    }

    private fun loginFromRemote(username: String, password: String): Boolean {
        val body = FormBody.Builder()
                .add("_MAC", MacAddressUtil(this).macAddress)
                .add("_USER_NAME", username)
                .add("_PASSWORD", password)
                .build()
        val request = Request.Builder()
                .post(body)
                .url(LOGIN_URL)
                .build()
        OKHttpClientWrapper.getInstance(this)
                .newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        Toast.makeText(this@LoginActivity, "网络环境错误，请重试", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val obj = JSONObject(response?.body().toString())
                        when (obj.get("code") as Int) {
                            600 -> prompt(this@LoginActivity, "用户不存在！")
                            601 -> prompt(this@LoginActivity, "密码错误！")
                            1000 -> prompt(this@LoginActivity, "服务器错误。")
                            1003 -> prompt(this@LoginActivity, "无效的Cookies。")
                            1004 -> prompt(this@LoginActivity, "MAC地址错误。")
                            0 -> {
                                prompt(this@LoginActivity, "登陆成功。")
                                try {
                                    JSONToEntity.getUserInfo(this@LoginActivity, obj)
                                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    prompt(this@LoginActivity, "失败")
                                }
                            }
                        }
                    }
                })
        return username.equals("zdk") and password.equals("123")
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