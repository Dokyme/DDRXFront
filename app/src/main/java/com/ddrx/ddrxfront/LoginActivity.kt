package com.ddrx.ddrxfront

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

/**
 * Created by dokym on 2018/3/15.
 */
class LoginActivity : AppCompatActivity() {

    var usernamePref: String by Preference(this@LoginActivity, "username", "")

    var lastLoginTime: Date by Preference(this@LoginActivity, "lastLoginTime", Date(0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!usernamePref.isEmpty()) {
            //直接跳转到home界面
        }
        initEvent()
    }

    private fun initEvent() {
        btn_login.setOnClickListener(LoginOnClickListener())
        btn_register.setOnClickListener(RegisterOnClickListener())
    }

    private fun loginFromRemote(username: String, password: String): Boolean {
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
                if (loginFromRemote(username, password)) {
                    usernamePref = username
                    lastLoginTime = Date()
                    Toast.makeText(this@LoginActivity, "登陆成功", Toast.LENGTH_SHORT).show()
                    //跳转到home界面
                } else {
                    Toast.makeText(this@LoginActivity, "用户名或密码错误", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    inner class RegisterOnClickListener : View.OnClickListener {
        override fun onClick(p0: View?) {
            Toast.makeText(this@LoginActivity, "注册", Toast.LENGTH_SHORT).show()
        }
    }
}