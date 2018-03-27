package com.ddrx.ddrxfront.Utilities

import android.content.Context
import okhttp3.Cookie

/**
 * Created by dokym on 2018/3/24.
 */
class CookiesPreference(context: Context) {
    private var prefCookies: List<Cookie> by Preference(context, "cookies", emptyList())

    var cookieList: List<Cookie>
        get() {
            return prefCookies
        }
        set(value) {
            prefCookies = value
        }
}