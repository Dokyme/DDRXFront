package com.ddrx.ddrxfront.Model

import java.io.Serializable
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
               var experience: Long?,
               var balance: Float?,
               var cardLimit: Int?) : Serializable {

    constructor() : this(null, null, null, null, null, null, null, null, null, null, null, null)

    fun checkValidity(): Boolean {
        return true
    }

}