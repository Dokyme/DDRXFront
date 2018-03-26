package com.ddrx.ddrxfront.Controller

import com.ddrx.ddrxfront.LoginActivity
import com.ddrx.ddrxfront.Model.UserInfo
import com.ddrx.ddrxfront.Preference
import com.ddrx.ddrxfront.Utilities.URLBuilder

/**
 * Created by dokym on 2018/3/23.
 */
class LoginActivityController(var context: LoginActivity) {
    var prefUserInfo: UserInfo by Preference(context, "userInfo", UserInfo())
    var LOGIN_URL = URLBuilder("/user/sign_in").build()

    fun loginFromRemote(username: String, password: String) {

    }

    fun checkLogined(): Boolean {
        return prefUserInfo.checkValidity()
    }
}