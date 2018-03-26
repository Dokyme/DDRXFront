package com.ddrx.ddrxfront.Model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by dokym on 2018/3/24.
 */
class UserDetailModel() : Parcelable {
    var label: String? = null
    var value: String? = null

    companion object {
        var CREATOR = object : Parcelable.Creator<UserDetailModel> {
            override fun createFromParcel(source: Parcel?): UserDetailModel {
                return UserDetailModel(source)
            }

            override fun newArray(size: Int): Array<UserDetailModel> {
                return emptyArray()
            }
        }
    }

    constructor(ip: Parcel?) : this() {
        this.label = ip?.readString()
        this.value = ip?.readString()
    }

    constructor(label: String, value: String) : this() {
        this.label = label
        this.value = value
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(label)
        dest?.writeString(value)
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}