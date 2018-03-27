package com.ddrx.ddrxfront.Utilities

import android.content.Context
import com.ddrx.ddrxfront.Model.UserInfo

/**
 * Created by dokym on 2018/3/23.
 */
class UserInfoPreference(var context: Context) {

    private var prefUserInfo = UserInfo(context)

    var userInfo: UserInfo
        get() {
            return prefUserInfo
        }
        set(value) {
            prefUserInfo = value
        }
}