package com.ddrx.ddrxfront.Model

import java.util.*

/**
 * Created by dokym on 2018/3/23.
 */
class UserInfo(var id: Long?,
               var account: String?,
               var lastLoginTime: Date?,
               var nickname: String?,
               var img: String?,
               var sex: String?,
               var birthday: Date?,
               var city: String?,
               var brief: String?,
               var experience: Int?,
               var balance: Float?,
               var cardLimit: Int?,
               var cookies: String?,
               var mac: String?) {

    constructor() : this(null, null, null, null, null, null, null, null, null, null, null, null, null)

    fun checkValidity(): Boolean {
        return true
    }

}