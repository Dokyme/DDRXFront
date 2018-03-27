package com.ddrx.ddrxfront.Model

import android.content.Context
import com.ddrx.ddrxfront.Preference
import java.io.Serializable
import java.util.*

/**
 * Created by dokym on 2018/3/23.
 */
class UserInfo(val context: Context) : Serializable {

    var id: Long? by Preference(context, "userId", -1)
    var account: String? by Preference(context, "userAccount", "ACCOUNT")
    var lastLoginTime: Date? by Preference(context, "userLastLoginTime", Date())
    var nickname: String? by Preference(context, "userNickName", "NICKNAME")
    var img: String? by Preference(context, "userImg", "IMG")
    var sex: String? by Preference(context, "userSex", "男")
    var birthday: Date? by Preference(context, "userBirthday", Date())
    var city: String? by Preference(context, "userCity", "CITY")
    var brief: String? by Preference(context, "userBrief", "BRIEF")
    var experience: Long? by Preference(context, "userExperience", -1)
    var balance: Float? by Preference(context, "userBalance", -1f)
    var cardLimit: Int? by Preference(context, "userCardLimit", -1)

    constructor(context: Context,
                id: Long?,
                account: String?,
                lastLoginTime: Date?,
                nickname: String?,
                img: String?,
                sex: String?,
                birthday: Date?,
                city: String?,
                brief: String?,
                experience: Long?,
                balance: Float?,
                cardLimit: Int?) : this(context) {
        this.id = id
        this.account = account
        this.lastLoginTime = lastLoginTime
        this.nickname = nickname
        this.img = img
        this.sex = sex
        this.birthday = birthday
        this.city = city
        this.brief = brief
        this.experience = experience
        this.balance = balance
        this.cardLimit = cardLimit
    }
}