package com.ddrx.ddrxfront.Model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by dokym on 2018/3/23.
 */
class TimeLineModel() : Parcelable {

    var message: String? = null
    var date: String? = null

    companion object {
        var CREATOR = object : Parcelable.Creator<TimeLineModel> {
            override fun createFromParcel(source: Parcel?): TimeLineModel {
                return TimeLineModel(source)
            }

            override fun newArray(size: Int): Array<TimeLineModel> {
                return Array(size, { _ -> TimeLineModel() })
            }
        }
    }

    constructor(ip: Parcel?) : this() {
        this.message = ip?.readString()
        this.date = ip?.readString()
    }

    constructor(message: String, date: String) : this() {
        this.message = message
        this.date = date
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(message)
        dest?.writeString(date)
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}