package com.ddrx.ddrxfront.Controller

import com.ddrx.ddrxfront.LoginActivity
import com.ddrx.ddrxfront.Model.UserInfo
import com.ddrx.ddrxfront.Preference

/**
 * Created by dokym on 2018/3/23.
 */
class LoginActivityController(var context: LoginActivity) {
    var prefUserInfo: UserInfo by Preference(context, "userInfo", UserInfo())

    fun loginFromRemote(username: String, password: String) {
        
    }

    fun checkLogined(): Boolean {
        return prefUserInfo.checkValidity()
    }
}