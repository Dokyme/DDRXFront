package com.ddrx.ddrxfront.Utilities

import android.content.Context
import com.ddrx.ddrxfront.Model.UserInfo
import com.ddrx.ddrxfront.Preference

/**
 * Created by dokym on 2018/3/23.
 */
class UserInfoPreference(var context: Context) {

    private var prefUserInfo: UserInfo by Preference(context, "userInfo", UserInfo())

    var userInfo: UserInfo
        get() {
            return prefUserInfo
        }
        set(value) {
            prefUserInfo = value
        }
}